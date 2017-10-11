package com.miku.ktv.miku_android.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miku.ktv.miku_android.R;

/**
 * Created by 焦帆 on 2017/10/11.
 */

public class HistroyFragment extends Fragment {

    private View inflateView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflateView = inflater.inflate(R.layout.fragment_histroy, container, false);
        return inflateView;
    }
}
