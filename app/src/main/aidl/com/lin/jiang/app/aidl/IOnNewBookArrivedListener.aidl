// IOnNewBookArrivedListener.aidl
package com.lin.jiang.app.aidl;

// Declare any non-default types here with import statements

import com.lin.jiang.app.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
