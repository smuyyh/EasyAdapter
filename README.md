# EasyAdapter
#### Android万能适配器，通吃所有的AbsListView、RecyclerView。

### 添加依赖
```
// 不包含v7和recyclerview的依赖，需自行在项目中引入
compile 'com.yuyh.easyadapter:library:1.2.0'
```

### 用法

#### step1

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

#### step2

```java
// 若需要调用adapter.setImageUrl，可以在Application配置全局统一回调，也可Adapter单独重写getImageLoader方法
AdapterImageLoader.init(new AdapterImageLoader.ImageLoader() {
    @Override
    public void loadImage(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .into(view);
    }
});
```

#### step3
```java

// 若adapter需要单独定制ImageLoader，比如placeHolder，则重写该方法，以替换全局初始化的ImageLoader
@Override
public AdapterImageLoader.ImageLoader getImageLoader() {
return new AdapterImageLoader.ImageLoader() {
    @Override
    public void loadImage(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .override(10, 10)
                .into(view);
    }
};
}

```

AbsListView的Adapter，继承EasyLVAdapter，重写convert方法，完成数据与事件的绑定，并且可以指定多种样式的item布局，通过重写getLayoutIndex方法，来指定position位置的item引用的布局。

EasyLVHolder中封装了很多通用的方法，比如setText/setImageDrawable/setOnClickListener等等。也可直接通过getConvertView取出item布局，或者通过getView(int id)取出某个控件，进行相应操作。

RecyclerView的Adapter，需继承EasyRVAdapter或者BaseRVAdapter(包含点击事件与长按事件：setClick方法)，用法与EasyLVAdapter类似。

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

    // RV 独有 点击事件
    void setOnItemClickListener(OnItemClickListener<T> itemClickListener);

    // RV 独有 长按事件
    void setOnItemLongClickListener(OnItemLongClickListener<T> itemLongClickListener);
```
