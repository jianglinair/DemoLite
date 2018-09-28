package com.lin.jiang.app.dialogfragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


import com.lin.jiang.app.R;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;

/**
 * @author Jiang Lin
 * @date 2018/5/11 0011
 * Description: CustomDialogFragment
 */
public class CustomDialogFragment extends DialogFragment {
    private static final String TAG = "CustomDialogFragment";

    private Context mContext;
    private TextView mTvSendDanmaku;
    private EditText mEtInputDanmaku;
    private CustomDialogFragmentCallback mCallback;

    interface CustomDialogFragmentCallback {
        void onSendButtonClick(String danmaku);

        void removeOnGlobalLayoutListener();
    }

    public void addCallback(CustomDialogFragmentCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // DialogFragment.STYLE_NO_FRAME 设置此 style 返回的 view 完全是自定义的，不会给 dialog 加 margin
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 不要 title
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置可外部点击消失
        getDialog().setCanceledOnTouchOutside(true);

        View view = inflater.inflate(R.layout.dialog_player_edit_barrage, container);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEtInputDanmaku = view.findViewById(R.id.et_edit);
        if (Build.VERSION.SDK_INT <= JELLY_BEAN_MR2) {
            mEtInputDanmaku.setImeOptions(EditorInfo.IME_ACTION_DONE);
            mEtInputDanmaku.setInputType(EditorInfo.TYPE_CLASS_TEXT);
            mEtInputDanmaku.setSingleLine(true);
        }

        mTvSendDanmaku = view.findViewById(R.id.tv_send);
        mTvSendDanmaku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String danmaku = mEtInputDanmaku.getText().toString();
                if (mCallback != null) {
                    mCallback.onSendButtonClick(danmaku);
                }
            }
        });
        // 自动展示软键盘并获得焦点
        mEtInputDanmaku.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                getDialog().getWindow().getDecorView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            mEtInputDanmaku.requestFocus();
                            imm.showSoftInput(mEtInputDanmaku, 0);
                        }
                    }
                }, 100);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // 设置 dialog 出现位置
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
    }

    @Override
    public void dismiss() {
        Log.d(TAG, "dismiss: " + mEtInputDanmaku.getWindowToken());
        super.dismiss();
        if (mCallback != null) {
            mCallback.removeOnGlobalLayoutListener();
        }
    }
}
