package com.yunusemre.betaproje.fragment.mezun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentMezunOgrenciMenuBinding;


public class MezunOgrenciMenu extends Fragment {
    private FragmentMezunOgrenciMenuBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMezunOgrenciMenuBinding.inflate(inflater,container,false);












        return binding.getRoot();
    }
}