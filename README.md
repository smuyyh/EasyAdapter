# EasyAdapter
#### Android万能适配器，通吃所有的AbsListView、RecyclerView。

### 添加依赖
```
buildscript {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
    dependencies {
        ...
    }
}
```

```
dependencies {
    implementation 'com.github.smuyyh:EasyAdapter:1.3.0'
}
```

### 用法

#### step1

```java

// ListView
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

// RecyclerView
public class RecyclerViewAdapter extends EasyRVAdapter<Bean> {
    public RecyclerViewAdapter(Context context, List<Bean> list, int... layoutIds) {
        super(context, list, layoutIds);
    }

    @Override
    protected void onBindData(EasyRVHolder viewHolder, final int position, Bean item) {
        viewHolder.setText(R.id.tv, item.name);
        // 自定义点击可覆盖父类的setOnItemClickListener
        /*viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, position+"---", Toast.LENGTH_SHORT).show();
            }
        });*/
        viewHolder.setImageUrl(R.id.iv, "https://img.25pp.com//ppnews/zixun_img/6fc/096/1444271700253231.jpg");
    }

    @Override
    public int getLayoutIndex(int position, Bean item) {
        if (position % 2 == 0)
            return 0;
        else return 1;
    }
}
```

#### step2

```java
// 1、若需要调用adapter.setImageUrl，可以在Application配置全局统一回调
AdapterImageLoader.init(new AdapterImageLoader.ImageLoader() {
    @Override
    public void loadImage(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .into(view);
    }
});

// 2、若adapter需要单独定制ImageLoader，比如placeHolder，则重写该方法，以替换全局初始化的ImageLoader
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

#### step3

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
