package com.yunusemre.betaproje.fragment.giris;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentOgrenciBinding;


public class OgrenciFragment extends Fragment {
    private FragmentOgrenciBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOgrenciBinding.inflate(inflater,container,false);



            binding.obtnOrtaokul.setOnClickListener(v -> {
                Navigation.findNavController(v).navigate(R.id.ogrenciToOrtaKayit);
            });

            binding.obtnLise.setOnClickListener(v -> {
                Navigation.findNavController(v).navigate(R.id.ogrenciToLiseKayit);
                // Lise Kayıt ekranı
            });

            binding.obtnUni.setOnClickListener(v -> {
                Navigation.findNavController(v).navigate(R.id.ogrenciToUniKayit);
            });

            binding.obtnMezun.setOnClickListener(v -> {
                // Mezun Sınav seçme menüsü YKS - TYT - AYT - LGS - DGS - YDS - KPSS - KPSS ÖNLİSANS - KPSS ORTAÖĞRETİM - ...
            });











        return binding.getRoot();
    }


}