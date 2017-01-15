# WePeiYangRD

WePeiYang Redesign
# 微北洋3.0 全新设计  
-   架构：MVVM + RxJava

### 依赖：

 > fragmentation —— 处理fragment的各种坑
 >
 > mvvmkit — — 处理databinding的支持框架，并且进行功能的添加和修改
 >
 > Rxlifecycle —— 处理rx绑定时候的生命周期

### 推荐阅读

> Library：[MvvmLight框架地址](https://github.com/Kelin-Hong/MVVMLight)
>
> 中文文档：**[**MVVM Light Toolkit使用指南**](http://www.jianshu.com/p/43ea7a531700)
>
> **引申阅读：**[**如何构建Android MVVM应用程序**](http://www.jianshu.com/p/2fc41a310f79)

### 架构设计--使用场景：

1. adapter模式：

   > layout是绑定的layout，具体寻找学习资料
   >
   > 父目录ViewModel —— layout
   >
   > 适配器itemViewModel —— layout

   ```java
   public class ToolItemViewModel implements ViewModel {

       private Context mContext;

       private Class<? extends BaseActivity> targetAct;

       public final ObservableInt iconRes = new ObservableInt();

       public final ObservableField<String> title = new ObservableField<>();

       public final ReplyCommand clickCommand = new ReplyCommand(this::jump);

       public ToolItemViewModel(Context context, int iconres, String title, Class<? extends BaseActivity> activityClass) {
           mContext = context;
           this.iconRes.set(iconres);
           this.title.set(title);
           this.targetAct = activityClass;
       }

       private void jump(){
           Intent intent = new Intent(mContext,targetAct);
           mContext.startActivity(intent);
       }

   }
   ```

   ​

   ```Xml
   <layout xmlns:android="http://schemas.android.com/apk/res/android"
           xmlns:bind="http://schemas.android.com/apk/res-auto">
       <data>
           <variable
               name="viewModel"
               type="com.twtstudio.retrox.wepeiyangrd.home.tools.ToolItemViewModel"/>
       </data>
       <LinearLayout
           android:layout_margin="5dp"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:orientation="vertical"
           bind:clickCommand="@{viewModel.clickCommand}">

           <ImageView
               android:layout_width="match_parent"
               android:layout_height="70dp"
               bind:placeholderImageRes="@{viewModel.iconRes}"
               android:layout_gravity="center_horizontal"
               />
           <TextView
               android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:layout_gravity="center_horizontal"
               android:layout_marginTop="3dp"
               android:text="@{viewModel.title}"/>

       </LinearLayout>
   </layout>

   ```

   > 绑定：运用开源库  **[BindingCollectionAdapter](https://github.com/evant/binding-collection-adapter)** 可以查阅这里的文档

   > **根布局的viewmodel：**

   ```java
   public class ToolsFragViewModel implements ViewModel {
       private Context mContext;

       public final ObservableArrayList<ToolItemViewModel> itemList = new ObservableArrayList<>();

       public final ItemView itemView = ItemView.of(BR.viewModel, R.layout.item_tool);

       public ToolsFragViewModel(Context context) {
           mContext = context;
           init();
       }

       private void init(){
           itemList.add(new ToolItemViewModel(mContext,R.drawable.ic_main_schedule,"课程表", MainActivity.class));
           itemList.add(new ToolItemViewModel(mContext,R.drawable.ic_main_gpa,"GPA", MainActivity.class));
           itemList.add(new ToolItemViewModel(mContext,R.drawable.ic_main_bike,"哲学车", MainActivity.class));
           itemList.add(new ToolItemViewModel(mContext,R.drawable.ic_main_party,"党建", MainActivity.class));
           itemList.add(new ToolItemViewModel(mContext,R.drawable.ic_main_read,"图书馆", MainActivity.class));
           // TODO: 2017/1/15 修改跳转的activity
       }
   }

   ```

   > RecyclerView的绑定

   ```xml
   <android.support.v7.widget.RecyclerView
               android:id="@+id/recyclerView"
               android:layout_width="match_parent"
               android:layout_height="match_parent"
               bind:layoutManager="@{LayoutManagers.linear()}"
               bind:itemView="@{viewmodel.itemView}"
               bind:items="@{viewmodel.list}"/>
   ```



2. 普通视图模式：

   > 布局viewmodel — — layout 即可

3. 事件绑定

   > xml的控件里面绑定ReplyCommand ——— 事件绑定
   >
   > 推荐java的viewmodel中使用lambda表达式写

   ```java
   public final ReplyCommand<Integer> onLoadMoreCommand =  new ReplyCommand<>((itemCount) -> { 
    int page=itemCount/LIMIT+1; 
    loadData(page.LIMIT)
   });
   ```

   > Sample: RecyclerView 绑定加载更多的命令（已封装）

   ```xml
   <android.support.v7.widget.RecyclerView 
   android:layout_width="match_parent"  
   android:layout_height="match_parent"  
   bind:onLoadMoreCommand="@{viewModel.loadMoreCommand}"/>
   ```

   > SwipeRefreshLayout的下拉刷新触发的指令

   ```xml
   <android.support.v4.widget.SwipeRefreshLayout
           android:id="@+id/swipe_refresh_layout"
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           android:layout_gravity="fill_vertical"
           app:layout_behavior="@string/appbar_scrolling_view_behavior"
           app:onRefreshCommand="@{viewModel.onRefreshCommand}"
           app:setRefreshing="@{viewModel.viewStyle.isRefreshing}">
   ```

   > 点击的触发指令

   ```xml
   <RelativeLayout
           xmlns:android="http://schemas.android.com/apk/res/android"
           android:layout_width="match_parent"
           android:layout_height="60dp"
           android:padding="12dp"
           bind:clickCommand="@{viewModel.clickCommand}">
   ```

   ​