package com.lin.jiang.app.aidl;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class BookIntentService extends AliveIntentService {
    private static final String TAG = "BookIntentService";

    private static final String ACTION_ADD = "com.lin.jiang.app.aidl.action.ADD";
    private static final String ACTION_REMOVE = "com.lin.jiang.app.aidl.action.REMOVE";
    private static final String ACTION_QUERY = "com.lin.jiang.app.aidl.action.QUERY";

    private static final String EXTRA_BOOK = "com.lin.jiang.app.aidl.extra.BOOK";

    private List<Book> mBooks;

    public BookIntentService() {
        super("BookIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: IN");
        mBooks = new ArrayList<>(2);
        mBooks.add(new Book(0, "Android"));
        mBooks.add(new Book(1, "iOS"));
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: IN");
//        mBooks.clear();
//        mBooks = null;
        super.onDestroy();
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionAdd(Context context, Book book) {
        Intent intent = new Intent(context, BookIntentService.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_BOOK, book);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionRemove(Context context, Book book) {
        Intent intent = new Intent(context, BookIntentService.class);
        intent.setAction(ACTION_REMOVE);
        intent.putExtra(EXTRA_BOOK, book);
        context.startService(intent);
    }

    public static void startActionQuery(Context context, Book book) {
        Intent intent = new Intent(context, BookIntentService.class);
        intent.setAction(ACTION_QUERY);
        intent.putExtra(EXTRA_BOOK, book);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ADD.equals(action)) {
                final Book book = intent.getParcelableExtra(EXTRA_BOOK);
                handleActionAdd(book);
            } else if (ACTION_REMOVE.equals(action)) {
                final Book book = intent.getParcelableExtra(EXTRA_BOOK);
                handleActionRemove(book);
            } else if (ACTION_QUERY.equals(action)) {
                final Book book = intent.getParcelableExtra(EXTRA_BOOK);
                handleActionQuery(book);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionAdd(Book book) {
        if (mBooks != null && !mBooks.contains(book)) {
            Log.d(TAG, "handleActionAdd: book=" + book);
            mBooks.add(book);
            Log.d(TAG, "handleActionAdd: books size: " + mBooks.size());
        } else {
            Log.d(TAG, "handleActionAdd: error! books=" + mBooks + ", book=" + book);
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionRemove(Book book) {
        if (mBooks != null && mBooks.contains(book)) {
            Log.d(TAG, "handleActionRemove: book=" + book);
            mBooks.remove(book);
            Log.d(TAG, "handleActionRemove: books size: " + mBooks.size());
        } else {
            Log.d(TAG, "handleActionRemove: error! books=" + mBooks + ", book=" + book);
        }
    }

    private void handleActionQuery(Book book) {
        Log.d(TAG, "handleActionQuery: book=" + book);
        Log.d(TAG, "handleActionQuery: books=" + mBooks);
    }
}
