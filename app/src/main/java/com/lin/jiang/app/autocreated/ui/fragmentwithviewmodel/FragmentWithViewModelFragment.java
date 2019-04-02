package com.lin.jiang.app.autocreated.ui.fragmentwithviewmodel;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.jiang.app.R;


public class FragmentWithViewModelFragment extends Fragment {

    private FragmentWithViewModelViewModel mViewModel;

    public static FragmentWithViewModelFragment newInstance() {
        return new FragmentWithViewModelFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_with_view_model_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FragmentWithViewModelViewModel.class);
        // TODO: Use the ViewModel
    }

}
