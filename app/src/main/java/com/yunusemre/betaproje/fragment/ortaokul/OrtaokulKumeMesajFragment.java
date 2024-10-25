package com.yunusemre.betaproje.fragment.ortaokul;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentOrtaokulKumeMesajBinding;


public class OrtaokulKumeMesajFragment extends Fragment {
    private FragmentOrtaokulKumeMesajBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrtaokulKumeMesajBinding.inflate(inflater,container,false);












          backTouch();





        return binding.getRoot();
    }
    public void backTouch() {
        OnBackPressedCallback geriTus = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Snackbar.make(getView(),"KnowChain'den çıkmak ister misin ?",Snackbar.LENGTH_SHORT).setAction("Evet", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                }).show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),geriTus);
    }
}