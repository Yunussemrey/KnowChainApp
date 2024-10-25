package com.yunusemre.betaproje.fragment.lise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentLiseKayitBinding;


public class LiseKayitFragment extends Fragment {
    private FragmentLiseKayitBinding binding;
    private String email;
    private String sifre;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentLiseKayitBinding.inflate(inflater,container,false);

       auth = FirebaseAuth.getInstance();

            binding.liseKayitButon.setOnClickListener(v -> {

                email = binding.liseKayitPosta.getText().toString();
                sifre = binding.liseKayitSifre.getText().toString();

               /* LiseKayitFragmentDirections.LiseKayitToLiseKullBilgi gecis = LiseKayitFragmentDirections.liseKayitToLiseKullBilgi(email,sifre);
                gecis.setEmail(email);
                gecis.setSifre(sifre);*/

                auth.createUserWithEmailAndPassword(email,sifre).addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       Toast.makeText(getContext(), "kayıt tamam", Toast.LENGTH_SHORT).show();
                       Navigation.findNavController(v).navigate(R.id.liseKayitToLiseKullBilgi);
                   }
                }).addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "kayıt olmadı", Toast.LENGTH_SHORT).show();
                });




            });




















        return binding.getRoot();
    }
}