package com.yuyh.easyadapter.recyclerview;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Created by zymapp on 2016/10/17.
 */

public class BaseRVAdapter<T> extends EasyRVAdapter<T> {

    private ItemClick ic;
    private OnItemClick oic;
    private OnItemLongClick oil;

    public BaseRVAdapter(Context context, List<T> list, int... layoutIds) {
        super(context, list, layoutIds);
    }

    @Override
    protected void onBindData(EasyRVHolder viewHolder, final int position, final T item) {
        viewHolder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ic!=null){
                    ic.onItemClick(v, v.getId(),position,item);
                }
                if(oic!=null){
                    oic.onItemClick(v, v.getId(),position,item);
                }
            }
        });
        viewHolder.setOnItemViewLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(ic!=null){
                    ic.onItemLongClick(v, v.getId(),position,item);
                }
                if(oil!=null){
                    oil.onItemLongClick(v, v.getId(),position,item);
                }
                return false;
            }
        });
    }

    /****
     * 设置 RecyclerView Item 点击事件 和 长按事件 的回调
     * @param ic
     */
    public void setClick(ItemClick<T> ic){
        this.ic = ic;
    }
    /****
     * 设置 RecyclerView Item 点击事件 的回调
     * @param oic
     */
    public void setClick(OnItemClick<T> oic){
        this.oic = oic;
    }
    /****
     * 设置 RecyclerView Item 长按事件 的回调
     * @param oil
     */
    public void setClick(OnItemLongClick<T> oil){
        this.oil = oil;
    }

    /****
     * RecyclerView Item 的点击事件 和 长按事件
     * @param <T>
     */
    public interface ItemClick<T>{
        void onItemClick(View v, int vId, int position, T item);
        void onItemLongClick(View v, int vId, int position, T item);
    }

    /****
     * RecyclerView Item 的点击事件
     * @param <T>
     */
    public interface OnItemClick<T>{
        void onItemClick(View v, int vId, int position, T item);
    }

    /****
     * RecyclerView Item 的长按事件
     * @param <T>
     */
    public interface OnItemLongClick<T>{
        void onItemLongClick(View v, int vId, int position, T item);
    }
}
