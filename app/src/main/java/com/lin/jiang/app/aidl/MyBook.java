package com.lin.jiang.app.aidl;

import android.os.Parcel;

/**
 * Created by JiangLin.<br>
 * Date: 2019/02/02 10:55<br>
 * Description: MyBook <br>
 * <a href="https://www.google.com">文档地址</a>
 *
 * @author JiangLin
 */
public class MyBook extends Book {

    public int bookPrice;

    public MyBook(int bookId, String bookName, int price) {
        super(bookId, bookName);
        this.bookPrice = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.bookPrice);
    }

    protected MyBook(Parcel in) {
        super(in);
        this.bookPrice = in.readInt();
    }

    public static final Creator<MyBook> CREATOR = new Creator<MyBook>() {
        @Override
        public MyBook createFromParcel(Parcel source) {
            return new MyBook(source);
        }

        @Override
        public MyBook[] newArray(int size) {
            return new MyBook[size];
        }
    };

    @Override
    public String toString() {
        return "MyBook{" +
                "bookPrice=" + bookPrice +
                ", bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
