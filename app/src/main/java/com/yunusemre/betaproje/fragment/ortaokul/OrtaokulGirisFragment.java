package com.yunusemre.betaproje.fragment.ortaokul;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentGirisBinding;
import com.yunusemre.betaproje.databinding.FragmentOrtaokulGirisBinding;


public class OrtaokulGirisFragment extends Fragment {
    private FragmentOrtaokulGirisBinding binding;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrtaokulGirisBinding.inflate(inflater,container,false);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomMenu);
        bottomNavigationView.setVisibility(View.GONE);


        auth = FirebaseAuth.getInstance();



        binding.ortaokulBtnGiris.setOnClickListener(v -> {
            String eposta = binding.ortaokulGirisMailEditText.getText().toString();
            String sifre = binding.ortaokulGirisSifreEditText.getText().toString();

            auth.signInWithEmailAndPassword(eposta,sifre).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(getContext(), "Giriş işlemi başarılı", Toast.LENGTH_SHORT).show();
                    // ortaokul anasayfaya git
                    Navigation.findNavController(v).navigate(R.id.ortaokulGirisToOrtaokulAnasayfa);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("ortaokulGiris",e.getLocalizedMessage());
                }
            });
        });


        // kayıt ekranına git
        binding.ortaokulTextViewKayitOl.setOnClickListener(v -> {
            NavController navController1 = Navigation.findNavController(requireView());
            navController1.navigate(R.id.ortaokulGirisToOrtaokulKayit);

        });

        // ana menü'ye dön

        binding.ortaokulGirisMenuDon.setOnClickListener(v -> {
            NavController navController1 = Navigation.findNavController(requireView());
            navController1.navigate(R.id.ortaokulGirisToGirisFragment);
        });















        return binding.getRoot();
    }
}