package com.jiang.lin.demo.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.jiang.app.R;


/**
 * Created by JiangLin.<br>
 * Date: 2018/08/20 19:54<br>
 * Description: Fragment1
 *
 * @author JiangLin
 */
public class Fragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_player_edit_barrage, container, false);
        return view;
    }
}
