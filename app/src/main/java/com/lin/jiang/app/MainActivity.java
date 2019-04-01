package com.lin.jiang.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.lin.jiang.app.aidl.AidlActivity;
import com.lin.jiang.app.aidl.Book;
import com.lin.jiang.app.aidl.BookIntentService;
import com.lin.jiang.app.constraint.ConstraintAdvancedActivity;
import com.lin.jiang.app.constraint.ConstraintLayoutActivity;
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
}
