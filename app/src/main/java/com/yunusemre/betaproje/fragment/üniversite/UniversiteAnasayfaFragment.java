package com.yunusemre.betaproje.fragment.üniversite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentUniversiteAnasayfaBinding;


public class UniversiteAnasayfaFragment extends Fragment {
    private FragmentUniversiteAnasayfaBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUniversiteAnasayfaBinding.inflate(inflater,container,false);

        BottomNavigationView bottomNavigationView1 = getActivity().findViewById(R.id.bottomMenuUni);
        bottomNavigationView1.setVisibility(View.VISIBLE);



















        return binding.getRoot();
    }
}