package com.yunusemre.betaproje.fragment.ortaokul;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentOrtaokulKayitBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class OrtaokulKayitFragment extends Fragment {
    private FragmentOrtaokulKayitBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrtaokulKayitBinding.inflate(inflater,container,false);

        // initialize
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();





                binding.ortaokulbtnKayit.setOnClickListener(v -> {
                    String ePosta = binding.ortaokulemailEditText.getText().toString();
                    String sifre = binding.ortaokulsifreEditText.getText().toString();
                    kullaniciKayit(ePosta,sifre);
                });






        binding.ortaokultextViewGirisYap.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.ortaokulKayitToOrtaokulGiris);
        });





        return binding.getRoot();
    }


    public void kullaniciKayit(String eMail,String sifre) {
        auth.createUserWithEmailAndPassword(eMail,sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(),"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
                    // ortaokul menü sayfasına git
                    NavController navController1 = Navigation.findNavController(requireView());
                    navController1.navigate(R.id.ortaKayitToOrtaKull);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}