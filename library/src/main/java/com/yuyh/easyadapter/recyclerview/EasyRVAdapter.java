package com.yuyh.easyadapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuyh.easyadapter.helper.DataHelper;
import com.yuyh.library.easyadapter.R;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/7/21.
 */
public abstract class EasyRVAdapter<T> extends RecyclerView.Adapter<EasyRVHolder> implements DataHelper<T> {

    /****
     * 头部相关
     */
    public static final int TYPE_HEADER = -1, TYPE_FOOTER = -2;
    private View mHeaderView, mFooterView;
    private int headerViewId = -1, footerViewId = -2;

    protected Context mContext;
    protected List<T> mList;
    protected int[] layoutIds;
    protected LayoutInflater mLInflater;

    private SparseArray<View> mConvertViews = new SparseArray<>();

    private OnItemClickListener<T> itemClickListener;
    private OnItemLongClickListener<T> itemLongClickListener;

    public EasyRVAdapter(Context context, List<T> list, int... layoutIds) {
        this.mContext = context;
        this.mList = list;
        this.layoutIds = layoutIds;
        this.mLInflater = LayoutInflater.from(mContext);
    }

    @Override
    public EasyRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new EasyRVHolder(mContext, headerViewId, mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new EasyRVHolder(mContext, footerViewId, mFooterView);
        }
        if (viewType < 0 || viewType > layoutIds.length) {
            throw new ArrayIndexOutOfBoundsException("layoutIndex");
        }
        if (layoutIds.length == 0) {
            throw new IllegalArgumentException("not layoutId");
        }
        int layoutId = layoutIds[viewType];
        View view = mConvertViews.get(layoutId);
        if (view == null) {
            view = mLInflater.inflate(layoutId, parent, false);
        }
        EasyRVHolder viewHolder = (EasyRVHolder) view.getTag();
        if (viewHolder == null || viewHolder.getLayoutId() != layoutId) {
            viewHolder = new EasyRVHolder(mContext, layoutId, view);
            return viewHolder;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EasyRVHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER)
            return;
        if (getItemViewType(position) == TYPE_FOOTER)
            return;

        position = getPosition(position);
        final T item = mList.get(position);

        holder.getItemView().setTag(R.id.tag_position, position);
        holder.getItemView().setTag(R.id.tag_item, item);

        holder.getItemView().setOnClickListener(clickListener);
        holder.getItemView().setOnLongClickListener(longClickListener);

        onBindData(holder, position, item);
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return mList == null ? 0 : mList.size();
        } else if (mHeaderView != null && mFooterView != null) {
            return mList == null ? 2 : mList.size() + 2;
        } else {
            return mList == null ? 1 : mList.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mHeaderView != null) {
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1 && mFooterView != null) {
            return TYPE_FOOTER;
        }
        position = getPosition(position);
        return getLayoutIndex(position, mList.get(position));
    }

    /*****
     * 处理 GridLayoutManager
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER ? 1:gridManager.getSpanCount() ;
                }
            });
        }
    }

    /*****
     * 处理   StaggeredGridLayoutManager
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(EasyRVHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == 0);
        }
    }

    private int getPosition(int position) {
        if (mHeaderView != null) {
            position = position - 1;
        }
        return position;
    }

    /**
     * 指定item布局样式在layoutIds的索引。默认为第一个
     *
     * @param position
     * @param item
     * @return
     */
    public int getLayoutIndex(int position, T item) {
        return 0;
    }

    /****
     * 设置头部
     * @param headerViewId
     */
    public View setHeaderView(int headerViewId) {
        return setHeaderView(headerViewId, null);
    }

    public View setHeaderView(int headerViewId, ViewGroup parent) {
        mHeaderView = mLInflater.inflate(headerViewId, parent, false);
        this.headerViewId = headerViewId;
        notifyItemInserted(0);
        return mHeaderView;
    }

    public void removeHeaderView() {
        if (mHeaderView != null) {
            mHeaderView = null;
            this.headerViewId = -1;
            notifyItemRemoved(0);
        }
    }

    public View setFooterView(int footerViewId) {
        return setFooterView(footerViewId, null);
    }

    public View setFooterView(int footerViewId, ViewGroup parent) {
        mFooterView = mLInflater.inflate(footerViewId, parent, false);
        this.footerViewId = footerViewId;
        notifyItemInserted(mList.size());
        return mFooterView;
    }

    public void removeFooterView() {
        if (mFooterView != null) {
            mFooterView = null;
            this.footerViewId = -2;
            notifyItemRemoved(mList.size() - 1);
        }
    }

    /****
     * 获取头部
     * @return
     */
    public View getHeaderView() {
        return mHeaderView;
    }

    public View getFooterView() {
        return mFooterView;
    }

    protected abstract void onBindData(EasyRVHolder viewHolder, int position, T item);

    @Override
    public boolean addAll(List<T> list) {
        boolean result = mList.addAll(list);
        notifyDataSetChanged();
        return result;
    }

    @Override
    public boolean addAll(int position, List list) {
        boolean result = mList.addAll(position, list);
        notifyDataSetChanged();
        return result;
    }

    @Override
    public void add(T data) {
        mList.add(data);
        notifyDataSetChanged();
    }

    @Override
    public void add(int position, T data) {
        mList.add(position, data);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public boolean contains(T data) {
        return mList.contains(data);
    }

    @Override
    public T getData(int index) {
        return mList.get(index);
    }

    @Override
    public void modify(T oldData, T newData) {
        modify(mList.indexOf(oldData), newData);
    }

    @Override
    public void modify(int index, T newData) {
        mList.set(index, newData);
        notifyDataSetChanged();
    }

    @Override
    public boolean remove(T data) {
        boolean result = mList.remove(data);
        notifyDataSetChanged();
        return result;
    }

    @Override
    public void remove(int index) {
        mList.remove(index);
        notifyDataSetChanged();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.id.tag_position);
            T item = (T) v.getTag(R.id.tag_item);

            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, position, item);
            }
        }
    };

    private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

            int position = (int) v.getTag(R.id.tag_position);
            T item = (T) v.getTag(R.id.tag_item);

            if (itemLongClickListener != null) {
                itemLongClickListener.onItemLongClick(v, position, item);
            }

            return true;
        }
    };

    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    /****
     * RecyclerView Item 的点击事件
     * @param <T>
     */
    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T item);
    }

    /****
     * RecyclerView Item 的长按事件
     * @param <T>
     */
    public interface OnItemLongClickListener<T> {
        void onItemLongClick(View view, int position, T item);
    }
}
