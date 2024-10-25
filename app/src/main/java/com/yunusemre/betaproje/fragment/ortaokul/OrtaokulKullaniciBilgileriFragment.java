package com.yunusemre.betaproje.fragment.ortaokul;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.gson.Gson;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentOrtaokulKullaniciBilgileriBinding;
import com.yunusemre.betaproje.model.CityResponse;
import com.yunusemre.betaproje.model.OrtaokulCity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrtaokulKullaniciBilgileriFragment extends Fragment {
    private FragmentOrtaokulKullaniciBilgileriBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;
    Map<String, List<String>> okulVerisi = new HashMap<>();
    private List<OrtaokulCity> cityList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrtaokulKullaniciBilgileriBinding.inflate(inflater,container,false);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        // Şehir ve Okul Bilgileri
       /* okulVerisi.put("Ankara", Arrays.asList("Ortaokul 1", "Ortaokul 2", "Ortaokul 3"));
        okulVerisi.put("İstanbul", Arrays.asList("Ortaokul A", "Ortaokul B", "Ortaokul C"));
        okulVerisi.put("İzmir", Arrays.asList("Ortaokul X", "Ortaokul Y", "Ortaokul Z"));

        // Şehirler listesini al
        List<String> sehirListesi = new ArrayList<>(okulVerisi.keySet());

        // Şehirler için AutoCompleteTextView adapter
        ArrayAdapter<String> sehirAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, sehirListesi);
        binding.ortaokulSehirSec.setAdapter(sehirAdapter);
        binding.ortaokulSehirSec.setOnClickListener(v -> {
            binding.ortaokulSehirSec.showDropDown();
        });

        // Şehir seçildiğinde ilgili okulları göster
        binding.ortaokulSehirSec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String secilenSehir = parent.getItemAtPosition(position).toString();
                List<String> ortaokulListesi = okulVerisi.get(secilenSehir);

                if (ortaokulListesi != null) {
                    ArrayAdapter<String> ortaokulAdapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_dropdown_item_1line, ortaokulListesi);
                    binding.ortaokulsec.setAdapter(ortaokulAdapter);
                    binding.ortaokulsec.setOnClickListener(v -> {
                        binding.ortaokulsec.showDropDown();
                    });
                }
            }
        });
        binding.ortaokulsec.setOnClickListener(v -> {
            String sehir = binding.ortaokulSehirSec.getText().toString();
            if (sehir.isEmpty()) {
                Toast.makeText(getContext(), "Lütfen Yaşadığınız İli Seçiniz", Toast.LENGTH_SHORT).show();
            }
        });*/



        // Sınıf seçme
        ArrayList<String> siniflar = new ArrayList<>();
        siniflar.add("5. sınıf");
        siniflar.add("6. sınıf");
        siniflar.add("7. sınıf");
        siniflar.add("8. sınıf");

        ArrayAdapter sinifAdapter = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,siniflar);
        binding.ortaokulSinifSec.setAdapter(sinifAdapter);
        binding.ortaokulSinifSec.setOnClickListener(v -> {
            binding.ortaokulSinifSec.showDropDown();
        });



            binding.ortaokulKullanicibtnKayit.setOnClickListener(v -> {
                // girilen değerleri al
                String ad = binding.ortaokulAdEditText.getText().toString();
                String kullaniciAd = binding.ortaokulKullaniciEdittext.getText().toString();
                String soyad = binding.ortaokulSoyadEditText.getText().toString();
                String sehir = binding.ortaokulSehirSec.getText().toString();
                String okul = binding.ortaokulsec.getText().toString();
                String sinif = binding.ortaokulSinifSec.getText().toString();
                String seviye = binding.ortaokulSeviyeEditText.getText().toString();
                String email = user.getEmail();


                // değerleri kontrol et .. !!

                // değerleri kaydet

                Map<String,Object> kullaniciBilgi = new HashMap<>();
                kullaniciBilgi.put("ad",ad);
                kullaniciBilgi.put("kullaniciAd",kullaniciAd);
                kullaniciBilgi.put("soyad",soyad);
                kullaniciBilgi.put("sehir",sehir);
                kullaniciBilgi.put("okul",okul);
                kullaniciBilgi.put("sinif",sinif);
                kullaniciBilgi.put("seviye",seviye);
                kullaniciBilgi.put("eMail",email);

                // firestore ..
                    HashMap<String,Object> girisMap = new HashMap<>();
                    girisMap.put("seviye",seviye);
                    girisMap.put("posta",email);

                if (user != null) {
                    String usermail = user.getEmail();
                     String guncelUid = user.getUid();
                        firestoreGirisBilgiKayit(girisMap,guncelUid);
                    collectionReference = firebaseFirestore.collection("OrtaOkul").document("GirişBilgileri").collection(usermail);
                    collectionReference.add(kullaniciBilgi).addOnCompleteListener(task -> {
                       if (task.isSuccessful()) {
                           Toast.makeText(getContext(), "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                           NavController navController1 = Navigation.findNavController(requireView());
                           navController1.navigate(R.id.ortaokulKullBilgiToOrtaokulAnasayfa);
                       }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Kayıt başarısız", Toast.LENGTH_SHORT).show();
                    });
                }

            });





        // JSON dosyasını oku ve şehir listesine aktar
        loadCityData();

        // Şehirleri AutoCompleteTextView'e ekle
        setupCityAutoComplete();







        return binding.getRoot();
    }
    private void firestoreGirisBilgiKayit(HashMap<String,Object> map,String uid) {
        firebaseFirestore.collection("giris").document(uid).set(map).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "tamamdır", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "tamam değil", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadCityData() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.ortaokul); // JSON dosyasını aç
            InputStreamReader reader = new InputStreamReader(inputStream);

            Gson gson = new Gson();
            CityResponse cityResponse = gson.fromJson(reader, CityResponse.class); // JSON'u deserialize et
            cityList = cityResponse.getCities(); // Şehir listesi

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupCityAutoComplete() {
        List<String> cityNames = new ArrayList<>();
        for (OrtaokulCity city : cityList) {
            cityNames.add(city.getName()); // Şehir isimlerini listeye ekle
        }

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, cityNames);
        binding.ortaokulSehirSec.setAdapter(cityAdapter); // Şehirler için adapter
        binding.ortaokulSehirSec.setOnClickListener(v -> {
            binding.ortaokulSehirSec.showDropDown();
        });

        // Şehir seçildiğinde okulları doldur
        binding.ortaokulSehirSec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = (String) parent.getItemAtPosition(position);
                setupSchoolAutoComplete(selectedCity);
            }
        });
    }

    private void setupSchoolAutoComplete(String selectedCity) {
        List<String> schoolNames = new ArrayList<>();
        for (OrtaokulCity city : cityList) {
            if (city.getName().equals(selectedCity)) {
                schoolNames.addAll(city.getSchools()); // Seçilen şehre ait okulları ekle
                break;
            }
        }

        ArrayAdapter<String> schoolAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, schoolNames);
        binding.ortaokulsec.setAdapter(schoolAdapter); // Okullar için adapter
        binding.ortaokulsec.setOnClickListener(v -> {
            binding.ortaokulsec.showDropDown();
        });
    }
}
