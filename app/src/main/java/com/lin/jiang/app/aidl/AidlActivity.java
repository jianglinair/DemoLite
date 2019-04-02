package com.lin.jiang.app.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lin.jiang.app.R;

import java.util.List;

public class AidlActivity extends AppCompatActivity {
    private static final String TAG = "AidlActivity";

    private static final int MSG_NEW_BOOK_ARRIVED = 1;

    private IBookManager mBookManager;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_NEW_BOOK_ARRIVED:
                    Log.i(TAG, "handleMessage: receive new book: " + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBookManager = IBookManager.Stub.asInterface(service);
            try {
                service.linkToDeath(mDeathRecipient, 0);
                List<Book> books = mBookManager.getBookList();
                Log.i(TAG, "onServiceConnected: query book list, list type: " + books.getClass().getCanonicalName());
                Log.i(TAG, "onServiceConnected: query book list: " + books.toString());

                Book book1 = new Book(3, "Android 进阶");
                mBookManager.addBook(book1);
                Log.i(TAG, "onServiceConnected: add book1: " + book1);
                List<Book> books1 = mBookManager.getBookList();
                Log.i(TAG, "onServiceConnected: query book list: " + books1.toString());

                MyBook book2 = new MyBook(4, "Android 自我修养", 70);
                mBookManager.addMyBook(book2);
                Log.i(TAG, "onServiceConnected: add book2: " + book2);
                List<Book> books2 = mBookManager.getBookList();
                Log.i(TAG, "onServiceConnected: query book list: " + books2.toString());

                //                mBookManager.removeBook(book);

                mBookManager.registerListener(mOnNewBookArrivedListener);
                Log.i(TAG, "onServiceConnected: register listener: " + mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBookManager = null;
            Log.e(TAG, "onServiceDisconnected: binder died.");
            // 也可以在这里重连服务端
            // 两者的区别是onServiceDisconnected运行在UI线程，而DeathRecipient运行在Binder线程池中，不能访问UI
        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MSG_NEW_BOOK_ARRIVED, newBook).sendToTarget();
        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mBookManager == null) {
                return;
            }
            mBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBookManager = null;
            Log.e(TAG, "binderDied: DeathRecipient");
            // 重连服务端
            Intent intent = new Intent(AidlActivity.this, BookManagerService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        /*Button btn = findViewById(R.id.btn_main);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mBookManager != null && mBookManager.asBinder().isBinderAlive()) {
                        Toast.makeText(MainActivity.this, "books now: " + mBookManager.getBookList(), Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onClick: books now: " + mBookManager.getBookList());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }

    @Override
    protected void onDestroy() {
        if (mBookManager != null && mBookManager.asBinder().isBinderAlive()) {
            try {
                mBookManager.unregisterListener(mOnNewBookArrivedListener);
                Log.i(TAG, "onDestroy: unregister listener: " + mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
}
