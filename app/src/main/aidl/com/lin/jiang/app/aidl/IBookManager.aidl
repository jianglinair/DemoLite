// IBookManager.aidl
package com.lin.jiang.app.aidl;

// Declare any non-default types here with import statements

import com.lin.jiang.app.aidl.Book;
import com.lin.jiang.app.aidl.MyBook;
import com.lin.jiang.app.aidl.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void addMyBook(in MyBook book);
    void removeBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
