package com.lin.jiang.app.aidl;

import android.app.Activity;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;

/**
 * Created by JiangLin.<br>
 * Date: 2019/02/01 16:41<br>
 * Description: BookManagerStub <br>
 * <a href="https://www.google.com">文档地址</a>
 *
 * @author JiangLin
 */
public abstract class BookManagerStub extends IBookManager.Stub {
    @Override
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        return super.onTransact(code, data, reply, flags);
    }
}
