package com.yunusemre.betaproje.fragment.üniversite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentUniversitePaylasimAkisBinding;


public class UniversitePaylasimAkisFragment extends Fragment {
    private FragmentUniversitePaylasimAkisBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUniversitePaylasimAkisBinding.inflate(inflater,container,false);


















        return binding.getRoot();
    }
}