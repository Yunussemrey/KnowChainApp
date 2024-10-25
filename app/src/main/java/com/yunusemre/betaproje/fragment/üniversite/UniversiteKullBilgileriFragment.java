package com.yunusemre.betaproje.fragment.üniversite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentUniversiteKullBilgileriBinding;

import java.util.HashMap;


public class UniversiteKullBilgileriFragment extends Fragment {
    private FragmentUniversiteKullBilgileriBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUniversiteKullBilgileriBinding.inflate(inflater,container,false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

            // Değerleri al
            String ad = binding.uniKullBilgiAdEditText.getText().toString();
            String soyad = binding.uniKullBilgiSoyadEditText.getText().toString();
            String kullAd = binding.uniKullBilgiKullaniciAdiEditText.getText().toString();
            String seviye = binding.uniKullBilgiSeviyeText.getText().toString();
            String sehir = binding.uniKullBilgiSehirSec.getText().toString();
            String derece = binding.uniKullBilgiDereceSec.getText().toString();
            String okul = binding.uniKullBilgiOkulSec.getText().toString();
            String bolum = binding.uniKullBilgiBolumSec.getText().toString();
            String sinif = binding.uniKullBilgiSinifSec.getText().toString();
            String posta = user.getEmail();
            String uid = user.getUid();

            binding.uniKullBilgiKayitBtn.setOnClickListener(v -> {
                HashMap<String,Object> girisMap = new HashMap<>();
                girisMap.put("posta",posta);
                girisMap.put("seviye",seviye);

                girisBilgiKayit(girisMap,uid);

                HashMap<String,Object> map = new HashMap<>();
                map.put("ad",ad);
                map.put("soyad",soyad);
                map.put("kullanıcı adı",kullAd);
                map.put("seviye",seviye);
                map.put("şehir",sehir);
                map.put("Öğrenim Derecesi",derece);
                map.put("okul",okul);
                map.put("bölüm",bolum);
                map.put("sınıf",sinif);
                map.put("posta",posta);

                firestoreKayit(posta,map,v);



            });















        return binding.getRoot();
    }
    private void firestoreKayit(String posta, HashMap<String,Object> map,View view) {
        firebaseFirestore.collection("Üniversite").document("GirisBilgileri").collection(posta).add(map).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Öğrenci bilgileri kaydedildi", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.uniKullBilgiToUniAnasayfa);
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Öğrenci bilgileri kaydolmadı", Toast.LENGTH_SHORT).show();
        });
    }
    private void girisBilgiKayit(HashMap<String,Object> map,String uid) {
        firebaseFirestore.collection("giris").document(uid).set(map).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "giriş bilgileri kaydı tamamdır", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "giriş bilgileri kaydı olmadı", Toast.LENGTH_SHORT).show();
        });
    }

}