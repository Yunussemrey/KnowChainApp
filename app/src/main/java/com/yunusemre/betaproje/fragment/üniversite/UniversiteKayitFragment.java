package com.yunusemre.betaproje.fragment.üniversite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentUniversiteKayitBinding;


public class UniversiteKayitFragment extends Fragment {
    private FragmentUniversiteKayitBinding binding;
    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUniversiteKayitBinding.inflate(inflater,container,false);

        auth = FirebaseAuth.getInstance();

        binding.uniKayitButon.setOnClickListener(v -> {

            String posta = binding.uniKayitPostaEditText.getText().toString();
            String sifre = binding.uniKayitSifreEditText.getText().toString();

            auth.createUserWithEmailAndPassword(posta,sifre).addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                   Toast.makeText(getContext(), "auth kayıt işlemi tamam", Toast.LENGTH_SHORT).show();
                   Navigation.findNavController(v).navigate(R.id.uniKayitToUniKullBilgi);
               }
            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), "kayıt işlemi başarısız", Toast.LENGTH_SHORT).show();
                Log.e("uniKayit",e.getLocalizedMessage());
            });
        });





















        return binding.getRoot();
    }
}