package com.lin.jiang.app.autocreated;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lin.jiang.app.R;
import com.lin.jiang.app.autocreated.ui.fragmentwithviewmodel.FragmentWithViewModelFragment;

public class FragmentWithViewModelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_with_view_model_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FragmentWithViewModelFragment.newInstance())
                    .commitNow();
        }
    }
}
