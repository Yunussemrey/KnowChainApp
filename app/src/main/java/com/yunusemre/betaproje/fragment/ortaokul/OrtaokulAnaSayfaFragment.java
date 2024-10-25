package com.yunusemre.betaproje.fragment.ortaokul;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentOrtaokulAnaSayfaBinding;


public class OrtaokulAnaSayfaFragment extends Fragment {
    private FragmentOrtaokulAnaSayfaBinding binding;
    private FirebaseUser user;
    private FirebaseAuth auth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrtaokulAnaSayfaBinding.inflate(inflater,container,false);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomMenu);
        bottomNavigationView.setVisibility(View.VISIBLE);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();




        binding.ortaokulBtnSettings.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Hesabım", Toast.LENGTH_SHORT).show();
            NavController navController1 = Navigation.findNavController(requireView());
            navController1.navigate(R.id.ortaokulAnasayfaToOrtaokulHesabim);
        });

            // geri tuş fonksiyonu
            backTouch();


            binding.ortaokulDilAnlatimbtn.setOnClickListener(v -> {
                NavController navController1 = Navigation.findNavController(requireView());
                navController1.navigate(R.id.ortaokulToOrtaDil);
            });









        return binding.getRoot();
    }

    public void backTouch() {
        OnBackPressedCallback geriTus = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Snackbar.make(getView(),"KnowChain'den çıkmak ister misin ?",Snackbar.LENGTH_SHORT).setAction("Evet", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requireActivity().finish();
                    }
                }).show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),geriTus);
    }




}


