package com.yunusemre.betaproje.fragment.lise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentLiseAnasayfaBinding;


public class LiseAnasayfaFragment extends Fragment {
    private FragmentLiseAnasayfaBinding binding;
    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLiseAnasayfaBinding.inflate(inflater,container,false);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomMenuLise);
        bottomNavigationView.setVisibility(View.VISIBLE);

            auth = FirebaseAuth.getInstance();

            binding.liseAnasayfaHesabim.setOnClickListener(v -> {
                Navigation.findNavController(v).navigate(R.id.liseToLiseHesabim);
            });








        return binding.getRoot();
    }
}