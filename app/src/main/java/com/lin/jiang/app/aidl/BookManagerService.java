package com.lin.jiang.app.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";

    private AtomicBoolean isServiceDestroyed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBooks = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListeners = new RemoteCallbackList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBooks;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.i(TAG, "addBook: " + book);
            mBooks.add(book);
        }

        @Override
        public void addMyBook(MyBook book) throws RemoteException {
            Log.i(TAG, "addBook: " + book);
            mBooks.add(book);
        }

        @Override
        public void removeBook(Book book) throws RemoteException {
            Log.i(TAG, "removeBook: " + book);
            for (Book b : mBooks) {
                if (b.bookId == book.bookId) {
                    mBooks.remove(b);
                    Log.i(TAG, "removeBook: done");
                    return;
                }
            }
            Log.e(TAG, "removeBook: not found: " + book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListeners.register(listener);
            Log.i(TAG, "registerListener: " + listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListeners.unregister(listener);
            Log.i(TAG, "unregisterListener: " + listener);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBooks.add(new Book(1, "Android"));
        mBooks.add(new Book(2, "iOS"));
        new Thread(new ServiceWorker()).start();
    }

    public BookManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBooks.add(book);
        final int N = mListeners.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener listener = mListeners.getBroadcastItem(i);
            if (listener != null) {
                listener.onNewBookArrived(book);
            }
        }
        mListeners.finishBroadcast();
    }

    private class ServiceWorker implements Runnable {

        @Override
        public void run() {
            while (!isServiceDestroyed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBooks.size() + 1;
                Book newBook = new Book(bookId, "new book#" + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
