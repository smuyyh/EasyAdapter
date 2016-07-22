# EasyAdapter
#####Android万能适配器，通吃所有的AbsListView、RecyclerView。

### 添加依赖
```
compile 'com.yuyh.easyadapter:library:1.0.0'
```

### 用法
```java
package com.yuyh.easyadapter;

import android.content.Context;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;
import java.util.List;

public class ListViewAdapter extends EasyLVAdapter<Bean> {

    public ListViewAdapter(Context context, List<Bean> list, int... layoutIds) {
        super(context, list, layoutIds);
    }

    @Override
    public void convert(EasyLVHolder holder, int position, final Bean bean) {
        holder.setText(R.id.tv, bean.name)
              .setText(R.id.tv2, bean.name2)
              .setImageResource(R.id.img, context.getResource().getDrawable(bean.imgRes));
    }

    @Override
    public int getLayoutIndex(int position, Bean item) {
        // 若需多样式布局，则重写该方法
        if (position % 2 == 0)
            return 1;
        else return 0;
    }
}
```
AbsListView的Adapter，重写EasyLVAdapter，在convert中完成数据与事件的绑定，并且可以指定多种样式的item布局，通过重写getLayoutIndex方法，来指定position位置的item引用的布局。

EasyLVHolder中封装了很多通用的方法，比如setText/setImageDrawable/setOnClickListener等等。也可直接通过getConvertView取出item布局，或者通过getView(int id)取出某个控件，进行相应操作。

RecyclerView的Adapter，需重写EasyRVAdapter，用法与EasyLVAdapter类似。

### 调用
```java
ListView lv = (ListView) findViewById(R.id.lv);
lv.setAdapter(new ListViewAdapter(this, list, R.layout.item_list_view_1, R.layout.item_list_view_2));
```

### 数据操作
EasyXXAdapter提供以下方法，便于对List数据的增删改。
```java
    boolean addAll(List<T> list);

    boolean addAll(int position, List<T> list);

    void add(T data);

    void add(int position, T data);

    void clear();

    boolean contains(T data);

    T getData(int index);

    void modify(T oldData, T newData);

    void modify(int index, T newData);

    boolean remove(T data);

    void remove(int index);
```