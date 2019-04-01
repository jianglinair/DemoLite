package com.lin.jiang.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lin.jiang.app.aidl.AidlActivity;
import com.lin.jiang.app.aidl.Book;
import com.lin.jiang.app.aidl.BookIntentService;

/**
 * @author Jiang Lin
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 2; i < 10; i ++) {
            BookIntentService.startActionAdd(this, new Book(i, "Book#" + i));
        }

        Button btn = findViewById(R.id.btn_main);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                route();
            }
        });
    }

    private void route() {
        // aidl test
        /*Intent intent = new Intent(this, AidlActivity.class);
        startActivity(intent);*/

        // IntentService test
        BookIntentService.startActionQuery(this, new Book(5, "Book#5"));
    }

    private void print() {
        System.out.println(getCacheDir());
        System.out.println(getFilesDir());
        System.out.println(getExternalCacheDir());
        System.out.println(getExternalFilesDir(null));
        System.out.println(Environment.getExternalStorageDirectory());
        System.out.println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
    }
}
