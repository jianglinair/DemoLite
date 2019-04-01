package com.lin.jiang.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lin.jiang.app.aidl.AidlActivity;
import com.lin.jiang.app.aidl.Book;
import com.lin.jiang.app.aidl.BookIntentService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Jiang Lin
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.btn_aidl_test)
    Button mBtnAidlTest;
    @BindView(R.id.btn_is_test)
    Button mBtnIsTest;
    @BindView(R.id.btn_dir_test)
    Button mBtnDirTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        for (int i = 2; i < 10; i++) {
            BookIntentService.startActionAdd(this, new Book(i, "Book#" + i));
        }
    }


    @OnClick({R.id.btn_aidl_test, R.id.btn_is_test, R.id.btn_dir_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_aidl_test:
                // aidl test
                Intent intent = new Intent(this, AidlActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_is_test:
                // IntentService test
                BookIntentService.startActionQuery(this, new Book(5, "Book#5"));
                break;
            case R.id.btn_dir_test:
                printDir();
                break;
            default:
                break;
        }
    }

    private void printDir() {
        String cacheDir = getCacheDir().getPath();
        String filesDir = getFilesDir().getPath();
        String externalCacheDir = getExternalCacheDir().getPath();
        String externalFilesDir = getExternalFilesDir(null).getPath();
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
}
