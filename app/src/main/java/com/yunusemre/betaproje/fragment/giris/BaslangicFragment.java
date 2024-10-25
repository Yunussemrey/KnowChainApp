package com.yunusemre.betaproje.fragment.giris;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentBaslangicBinding;

public class BaslangicFragment extends Fragment {
    private FragmentBaslangicBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBaslangicBinding.inflate(inflater,container,false);
























        return binding.getRoot();
    }
}