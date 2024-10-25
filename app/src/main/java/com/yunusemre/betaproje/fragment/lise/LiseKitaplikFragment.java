package com.yunusemre.betaproje.fragment.lise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentLiseKitaplikBinding;


public class LiseKitaplikFragment extends Fragment {
    private FragmentLiseKitaplikBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentLiseKitaplikBinding.inflate(inflater,container,false);
















        return binding.getRoot();
    }
}