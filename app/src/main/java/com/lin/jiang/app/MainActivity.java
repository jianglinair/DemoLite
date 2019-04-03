package com.lin.jiang.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lin.jiang.app.aidl.AidlActivity;
import com.lin.jiang.app.aidl.Book;
import com.lin.jiang.app.aidl.BookIntentService;
import com.lin.jiang.app.autocreated.BasicActivity;
import com.lin.jiang.app.autocreated.BottomNavigationActivity;
import com.lin.jiang.app.autocreated.FragmentWithViewModelActivity;
import com.lin.jiang.app.autocreated.FullscreenActivity;
import com.lin.jiang.app.autocreated.LoginActivity;
import com.lin.jiang.app.autocreated.NavigationDrawerActivity;
import com.lin.jiang.app.autocreated.ScrollingActivity;
import com.lin.jiang.app.autocreated.SettingsActivity;
import com.lin.jiang.app.autocreated.TabbedActivity;
import com.lin.jiang.app.constraint.ConstraintAdvancedActivity;
import com.lin.jiang.app.constraint.ConstraintLayoutActivity;
import com.lin.jiang.app.coordinator.CoordinatorLayoutActivity;
import com.lin.jiang.app.coordinator.CoordinatorWithAppBarActivity;
import com.lin.jiang.app.danmaku.DanmakuActivity;
import com.lin.jiang.app.expandable.ExpandableTextViewActivity;
import com.lin.jiang.app.seek.SeekImageActivity;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Jiang Lin
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_aidl_test)
    Button mBtnAidlTest;
    @BindView(R.id.btn_is_test)
    Button mBtnIsTest;
    @BindView(R.id.btn_dir_test)
    Button mBtnDirTest;
    @BindView(R.id.btn_constraint_test)
    Button mBtnConstraintTest;
    @BindView(R.id.btn_danmaku_test)
    Button mBtnDanmakuTest;
    @BindView(R.id.btn_expandable_tv_test)
    Button mBtnExtvTest;
    @BindView(R.id.btn_seek_img_test)
    Button mBtnSeekImgTest;
    @BindView(R.id.btn_constraint_advanced_test)
    Button mBtnConstraintAdvancedTest;
    @BindView(R.id.btn_coordinator_test)
    Button mBtnCoordinatorTest;

    @BindView(R.id.btn_basic_activity)
    Button mBtnBasicActivity;
    @BindView(R.id.btn_bottom_navigation_activity)
    Button mBtnBottomNavigationActivity;
    @BindView(R.id.btn_fragment_view_model_activity)
    Button mBtnFragmentViewModelActivity;
    @BindView(R.id.btn_fullscreen_activity)
    Button mBtnFullscreenActivity;
    @BindView(R.id.btn_login_activity)
    Button mBtnLoginActivity;
    @BindView(R.id.btn_navigation_drawer_activity)
    Button mBtnNavigationDrawerActivity;
    @BindView(R.id.btn_scrolling_activity)
    Button mBtnScrollingActivity;
    @BindView(R.id.btn_settings_activity)
    Button mBtnSettingsActivity;
    @BindView(R.id.btn_tabbed_activity)
    Button mBtnTabbedActivity;
    @BindView(R.id.btn_coordinator_with_app_bar_test)
    Button mBtnCoordinatorWithAppBarTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        for (int i = 2; i < 10; i++) {
            BookIntentService.startActionAdd(this, new Book(i, "Book#" + i));
        }
    }

    private void printDir() {
        String cacheDir = getCacheDir().getPath();
        String filesDir = getFilesDir().getPath();
        String externalCacheDir = Objects.requireNonNull(getExternalCacheDir()).getPath();
        String externalFilesDir = Objects.requireNonNull(getExternalFilesDir(null)).getPath();
        String externalStorageDirectory = Environment.getExternalStorageDirectory().getPath();
        String externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
        Log.d("[jianglin]", "MainActivity.printDir: \n" +
                "getCacheDir=" + cacheDir + "\n" +
                "getFilesDir=" + filesDir + "\n" +
                "getExternalCacheDir=" + externalCacheDir + "\n" +
                "getExternalFilesDir=" + externalFilesDir + "\n" +
                "Environment.getExternalStorageDirectory=" + externalStorageDirectory + "\n" +
                "Environment.getExternalStoragePublicDirectory=" + externalStoragePublicDirectory);
        Toast.makeText(this, "getCacheDir=" + cacheDir + "\n" +
                "getFilesDir=" + filesDir + "\n" +
                "getExternalCacheDir=" + externalCacheDir + "\n" +
                "getExternalFilesDir=" + externalFilesDir + "\n" +
                "Environment.getExternalStorageDirectory=" + externalStorageDirectory + "\n" +
                "Environment.getExternalStoragePublicDirectory=" + externalStoragePublicDirectory, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_aidl_test)
    public void onMBtnAidlTestClicked() {
        // aidl test
        Intent intent = new Intent(this, AidlActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_is_test)
    public void onMBtnIsTestClicked() {
        // IntentService test
        BookIntentService.startActionQuery(this, new Book(5, "Book#5"));
    }

    @OnClick(R.id.btn_dir_test)
    public void onMBtnDirTestClicked() {
        // 内部存储测试
        printDir();
    }

    @OnClick(R.id.btn_danmaku_test)
    public void onMBtnDanmakuTestClicked() {
        // bilibili danmaku test
        Intent intent = new Intent(this, DanmakuActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_expandable_tv_test)
    public void onMBtnExpandableTvTestClicked() {
        // ExpandableTextView test
        Intent intent = new Intent(this, ExpandableTextViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_seek_img_test)
    public void onMBtnSeekImgTestClicked() {
        // Seek image test
        Intent intent = new Intent(this, SeekImageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_constraint_test)
    public void onMBtnConstraintTestClicked() {
        // ConstraintLayoutActivity test
        Intent intent = new Intent(this, ConstraintLayoutActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_constraint_advanced_test)
    public void onMBtnConstraintAdvancedTestClicked() {
        Intent intent = new Intent(this, ConstraintAdvancedActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_coordinator_test)
    public void onMBtnCoordinatorTestClicked() {
        Intent intent = new Intent(this, CoordinatorLayoutActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_coordinator_with_app_bar_test)
    public void onMBtnCoordinatorWithAppBarTestClicked() {
        Intent intent = new Intent(this, CoordinatorWithAppBarActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.btn_basic_activity, R.id.btn_bottom_navigation_activity, R.id.btn_fragment_view_model_activity, R.id.btn_fullscreen_activity,
            R.id.btn_login_activity, R.id.btn_navigation_drawer_activity, R.id.btn_scrolling_activity, R.id.btn_settings_activity, R.id.btn_tabbed_activity})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_basic_activity:
                intent = new Intent(this, BasicActivity.class);
                break;
            case R.id.btn_bottom_navigation_activity:
                intent = new Intent(this, BottomNavigationActivity.class);
                break;
            case R.id.btn_fragment_view_model_activity:
                intent = new Intent(this, FragmentWithViewModelActivity.class);
                break;
            case R.id.btn_fullscreen_activity:
                intent = new Intent(this, FullscreenActivity.class);
                break;
            case R.id.btn_login_activity:
                intent = new Intent(this, LoginActivity.class);
                break;
            case R.id.btn_navigation_drawer_activity:
                intent = new Intent(this, NavigationDrawerActivity.class);
                break;
            case R.id.btn_scrolling_activity:
                intent = new Intent(this, ScrollingActivity.class);
                break;
            case R.id.btn_settings_activity:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.btn_tabbed_activity:
                intent = new Intent(this, TabbedActivity.class);
                break;
            default:
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
