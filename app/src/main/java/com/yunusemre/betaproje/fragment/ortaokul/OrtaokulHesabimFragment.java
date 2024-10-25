package com.yunusemre.betaproje.fragment.ortaokul;

import static com.yunusemre.betaproje.R.id.hesabim_menu_duzenle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentOrtaokulHesabimBinding;


public class OrtaokulHesabimFragment extends Fragment {
    private FragmentOrtaokulHesabimBinding binding;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private CollectionReference collectionReference;
    private FirebaseFirestore firebaseFirestore;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrtaokulHesabimBinding.inflate(inflater,container,false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomMenu);
        bottomNavigationView.setVisibility(View.GONE);


        String userMail = user.getEmail();
        collectionReference = firebaseFirestore.collection("OrtaOkul").document("GirişBilgileri").collection(userMail);
        collectionReference.get().addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
               for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                   String ad = (String) documentSnapshot.get("ad");
                   String soyad = (String) documentSnapshot.get("soyad");
                   String email = (String) documentSnapshot.get("eMail");
                   String kullaniciAd = (String) documentSnapshot.get("kullaniciAd");
                   String okul = (String) documentSnapshot.get("okul");
                   String seviye = (String) documentSnapshot.get("seviye");
                   String sinif = (String) documentSnapshot.get("sinif");

                   binding.ortaokulHesabimAdTextView.setText(ad+" "+soyad);
                   binding.ortaokulHesabimKullaniciAdTextView.setText("@"+kullaniciAd);
                  /* binding.ortaokulHesabimOkulTextView.setText(okul);
                   binding.ortaokulHesabimPostaTextView.setText(email);
                   binding.ortaokulHesabimSeviyeTextView.setText(seviye);
                   binding.ortaokulHesabimSinifTextView.setText(sinif);*/
                   Toast.makeText(getContext(), "çalıştı", Toast.LENGTH_SHORT).show();


               }
           }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Verilere erişilemedi", Toast.LENGTH_SHORT).show();
        });






        binding.hesabimOption.setOnClickListener(v -> {
            menuGoster(v);
        });



        return binding.getRoot();
    }

    private void menuGoster(View v) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.hesabim_menu, popupMenu.getMenu());

        // Menü item'ları için tıklama olaylarını yönet

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();

                    if (id == R.id.hesabim_menu_duzenle) {
                        // Düzenle seçeneği işlemleri
                        Toast.makeText(getContext(), "Düzenle seçildi", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (id == R.id.hesabim_menu_ayarlar) {
                        // Ayarlar seçeneği işlemleri
                        Toast.makeText(getContext(), "Ayarlar seçildi", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (id == R.id.hesabim_menu_cikis) {
                        Toast.makeText(getContext(), "Çıkış Yap seçildi", Toast.LENGTH_SHORT).show();
                        auth.signOut();
                        NavController navController1 = Navigation.findNavController(requireView());
                        navController1.navigate(R.id.hesabimToGiris);
                        return true;
                    } else if (id == R.id.hesabim_menu_kisisel_bilgiler) {
                        Toast.makeText(getContext(), "Kişisel Bilgiler", Toast.LENGTH_SHORT).show();
                            return true;
                    } else if (id == R.id.hesabim_arkadaslar) {
                        Toast.makeText(getContext(), "Arkadaşlarım", Toast.LENGTH_SHORT).show();
                        return true;
                    } else {
                        return false;
                    }
                }

            });


        // Menüyü göster
        popupMenu.show();

    }

}