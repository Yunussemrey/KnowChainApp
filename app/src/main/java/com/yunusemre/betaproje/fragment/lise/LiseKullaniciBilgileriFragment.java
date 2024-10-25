package com.yunusemre.betaproje.fragment.lise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentLiseKullaniciBilgileriBinding;

import java.util.HashMap;


public class LiseKullaniciBilgileriFragment extends Fragment {
    private FragmentLiseKullaniciBilgileriBinding binding;
    String gelenPosta;
    String gelenSifre;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Handler handler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentLiseKullaniciBilgileriBinding.inflate(inflater,container,false);

//            LiseKullaniciBilgileriFragmentArgs  bundle = LiseKullaniciBilgileriFragmentArgs.fromBundle(getArguments());
//            gelenPosta = bundle.getEmail();
//            gelenSifre = bundle.getSifre();




            firebaseFirestore = FirebaseFirestore.getInstance();
            auth = FirebaseAuth.getInstance();
            user = FirebaseAuth.getInstance().getCurrentUser();




                // e-posta ve şifreyi aldım.


                binding.liseKullKayitButon.setOnClickListener(v -> {




                    //Değerleri alma
                    String ad = binding.liseKullAd.getText().toString();
                    String soyad = binding.liseKullSoyad.getText().toString();
                    String kullAdi = binding.liseKullKullaniciAd.getText().toString();
                    String seviye = binding.liseKullSeviye.getText().toString();
                    String sehir = binding.liseKullSehirSec.getText().toString();
                    String okul = binding.liseKullOkulSec.getText().toString();
                    String sinif = binding.liseKullSinifSec.getText().toString();
                    //kullaniciAdKayit(kullAdi);

                    // Kontroller

                   /* if (ad.isEmpty() || !ad.matches("[a-zA-Z]+")){
                        Toast.makeText(getContext(), "Lütfen geçerli bir ad giriniz", Toast.LENGTH_SHORT).show();
                    } else if (soyad.isEmpty() || !soyad.matches("[a-zA-Z]+")) {
                        Toast.makeText(getContext(), "Lütfen geçerli bir soyad giriniz", Toast.LENGTH_SHORT).show();
                    } else if (kullAdi.isEmpty() || !kullAdi.isEmpty()) {
                        Toast.makeText(getContext(), "Lütfen geçerli bir kullanıcı adı giriniz", Toast.LENGTH_SHORT).show();
                        kullaniciAdKontrol(kullAdi);
                    }else{*/

                            String guncelUid = user.getUid();
                           String guncelposta = user.getEmail();
                           HashMap<String,Object> map = new HashMap<>();
                           map.put("ad",ad);
                           map.put("eMail",guncelposta);
                           map.put("soyad",soyad);
                           map.put("kullaniciAdi",kullAdi);
                           map.put("Öğrenim seviyesi",seviye);
                           map.put("sehir",sehir);
                           map.put("okul",okul);
                           map.put("sinif",sinif);

                           HashMap<String,Object> girisMap = new HashMap<>();
                           girisMap.put("seviye",seviye);
                           girisMap.put("posta",guncelposta);

                           firestoreGirisBilgiKayit(girisMap,guncelUid);

                           firebaseFirestoreKayit(map,guncelposta);

                           NavController navController1 = Navigation.findNavController(requireView());
                           navController1.navigate(R.id.liseKullToLiseAnasayfa);









                  //  }


                });



















        return binding.getRoot();
    }

    private void kullaniciKayit(String eposta,String sifre) {
        auth.createUserWithEmailAndPassword(eposta,sifre).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(getContext(), "Kullanıcı kaydı oluşturuluyor", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(),"posta ve sifre kaydolmadı", Toast.LENGTH_SHORT).show();
        });
    }
    private void firebaseFirestoreKayit(HashMap<String,Object> map,String posta) {

        CollectionReference collectionReference = firebaseFirestore.collection("Lise").document("GirisBilgileri").collection(posta);
        collectionReference.add(map).addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
               Toast.makeText(getContext(), "Firebase kayıt oldu", Toast.LENGTH_SHORT).show();
           }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Firebase kayıt olmadı", Toast.LENGTH_SHORT).show();
        });
    }
    private void kullaniciAdKayit(String kullaniciAdi) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("kullaniciAdi",kullaniciAdi);

        firebaseFirestore.collection("KullaniciAdlari").add(map).addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
               Toast.makeText(getContext(), "Kullanıcı Adı kaydedildi", Toast.LENGTH_SHORT).show();
           }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "kullanıcı adı kayıt olmadı", Toast.LENGTH_SHORT).show();
        });
    }
    private void kullaniciAdKontrol(String kullaniciAdi) {
        firebaseFirestore.collection("kullaniciAdlari").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    HashMap<String,Object> map = (HashMap<String, Object>) documentSnapshot.getData();

                    String gelenKullAd = (String) map.get("kullaniciAdi");
                    if (kullaniciAdi.matches(gelenKullAd)) {
                        Toast.makeText(getContext(), "Bu kullanıcı adı zaten var", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "ad kullanılabilir", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "kull ad kontrol edilemedi", Toast.LENGTH_SHORT).show();
        });
    }

    private void firestoreGirisBilgiKayit(HashMap<String,Object> girisMap,String uid) {
        firebaseFirestore.collection("giris").document(uid).set(girisMap).addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
               Toast.makeText(getContext(), "tamamdır", Toast.LENGTH_SHORT).show();
           }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "tamam değil", Toast.LENGTH_SHORT).show();
        });


    }
}