# ASwipeLayout
Android侧滑菜单库

## Android中侧滑的场景有很多，大部分是基于RecyclerView，但是有些时候你可以动态地addView到一个布局当中，也希望他实现侧滑，所以就产生了ASwipeLayout，他不仅支持在RecyclerView中实现侧滑
## 实际上只要你包裹了这层布局，都能实现侧滑
## 1.效果图
![single](https://github.com/WelliJohn/ASwipeLayout/blob/master/images/single_row.gif?raw=true)

![single](https://github.com/WelliJohn/ASwipeLayout/blob/master/images/two_rows.gif?raw=true)
## 2.使用方式其实挺简单的，在设计的时候，就是想着怎么简单怎么来。
引入库：
```
Step 1. Add it in your root build.gradle at the end of repositories:
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.WelliJohn:ASwipeLayout:0.0.2'
	}
```
在需要侧滑的布局的根布局中添加下面这段代码，注意注释的地方才是可以定制的：
```
<?xml version="1.0" encoding="utf-8"?>
<wellijohn.org.swipevg.ASwipeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">


    <LinearLayout
        android:id="@+id/ll_content"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#FFFFFF"
        android:orientation="horizontal">

        //在这里是实现你的主item的东西，根据你们的项目随便添加
    </LinearLayout>

    <LinearLayout
        android:id="@+id/right_menu_content"
        android:layout_width="wrap_content"
        android:layout_height="match_parent">
 
        //在这里是实现右侧的菜单，根据你们的项目随便添加
    </LinearLayout>


</wellijohn.org.swipevg.SwipeLayout>

```
注意在这里ll_content，right_menu_content是一定要的，这个id对应的布局不要自己去改变，以后有需要会放开，一般的情况只需要定制主item的内容和右侧菜单栏了，在这里我也省去了定义一些额外的自定义view了，单纯就是用id，来区分主item和右侧的菜单。
## 3.我们知道在RecyclerView中ViewHolder是有复用的情况，这样会使得当我们滑出某个menu的时候，再进行RecyclerView的上下滑动时，会使得其他的Item也滑出了menu，这就是item复用导致了数据错乱，所以针对这类型的问题的话，我在这里已经提供了OnSwipeStateChangeListener接口，在这里你们可以记录下滑动的状态，在onBindViewHolder方法里面，根据状态来设定Item是打开menu还是关闭menu：
```
 @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Person person = mDatas.get(position);
        holder.scrollDelLl.setOpen(person.isOpen());

        holder.scrollDelLl.setOnSwipeStateChangeListener(new OnSwipeStateChangeListener() {
            @Override
            public void onSwipeStateChange(boolean open) {
                person.setOpen(open);
            }
        });

    }
```
如上代码就可以解决Item复用导致布局错乱的问题了（粑粑再也不用担心RecyclerView复用的问题了）。
## 4.目前的项目当中，只是支持了右侧菜单的实现，因为左侧的需求我现在见到不是很多，当然如果有需求的话，可以加下面的群，提你们的需求或者遇到的问题，我在有时间的情况会进行添加或者修改：
![](https://user-gold-cdn.xitu.io/2018/4/19/162dd9e2b739e06e?w=226&h=290&f=png&s=16623)
## 5.代码已上传github，[ASwipeLayout](https://github.com/WelliJohn/ASwipeLayout)
