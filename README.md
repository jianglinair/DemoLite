# DemoLite
我的一些demo集合。

---

ConstraintLayout：

1. 约束类型；
2. 偏移；
3. Guideline, Barrier 辅助布局，Guideline 本身位置不变，元素相对其变化；Barrier 根据元素位置变化而变化；
4. Chain，链式比例布局

---

ToolBar:

现在不推荐使用 ActionBar，推荐使用 ToolBar；使用 ToolBar 之前需要禁用 ActionBar，
禁用 ActionBar 方法：

* 方法一：AndroidManifest.xml 文件中将 Activity 配置为 NoActionBar；
* 方法二：使用 Java 代码隐藏 ActionBar，代码如下：

  ```java
  ActionBar actionBar = getSupportActionBar();
  if (actionBar != null) {
    actionBar.hide();
  }
  ```

ToolBar 中的 action 只会显示图标，menu 中的 action 只会显示文字。

由于一般会将 Activity 设置为 AppTheme.NoActionBar，实际使用的是浅色主题，而头部 ToolBar 一般需要深色主题，所以需要重新单独设置 ToolBar 的 theme，即 **android:theme="@style/AppTheme.AppBarOverlay"**；
ToolBar theme 设置为 AppTheme.AppBarOverlay 后，上面弹出的菜单等变成使用了深色主题，此时想要将弹出的菜单设置回浅色主题需要使用 AppTheme.PopupOverlay，即 **app:popupTheme="@style/AppTheme.PopupOverlay"**。

