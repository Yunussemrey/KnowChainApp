package com.yunusemre.betaproje.fragment.giris;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentGirisBinding;
import com.yunusemre.betaproje.fragment.ortaokul.OrtaokulAnaSayfaFragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class GirisFragment extends Fragment {
    private FragmentGirisBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore firebaseFirestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGirisBinding.inflate(inflater,container,false);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomMenu);
        bottomNavigationView.setVisibility(View.GONE);
        BottomNavigationView bottomNavigationView1 = getActivity().findViewById(R.id.bottomMenuLise);
        bottomNavigationView1.setVisibility(View.GONE);
        BottomNavigationView bottomNavigationView2 = getActivity().findViewById(R.id.bottomMenuUni);
        bottomNavigationView2.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();





        binding.gbtnogrenci.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.girisToOgrenci);
        });

        binding.gbtnogretmen.setOnClickListener(v -> {
            // öğretmen branş ve seviye belirleme
        });

      backTouch();


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

    @Override
    public void onResume() {
        super.onResume();
        if (user != null) {
            String currentUser = user.getEmail();
            String currentUid = user.getUid();
            firebaseFirestore.collection("giris").document(currentUid).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String seviye = documentSnapshot.getString("seviye");

                            // Seviyeye göre yönlendirme yap
                            if (seviye != null) {
                                if (seviye.equals("Orta Okul")) {
                                    // OrtaokulFragment'a yönlendir
                                    Toast.makeText(getContext(), "ortaokul", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(getView()).navigate(R.id.girisSoOrtaokul);
                                } else if (seviye.equals("Lise")) {
                                    // LiseFragment'a yönlendir
                                    Toast.makeText(getContext(), "lise", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(getView()).navigate(R.id.girisToLiseAnasayfa);
                                } else if (seviye.equals("Üniversite")) {
                                    Toast.makeText(getContext(), "Üniversite", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(getView()).navigate(R.id.girisToUniAnasayfa);
                                }
                            }
                        }
                    }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), "giris koleksiyonuna ulaşılamadı", Toast.LENGTH_SHORT).show();
            });
        }else {
            Toast.makeText(getContext(), "Kayıtlı hesap bulunamadı", Toast.LENGTH_SHORT).show();
        }

    }
}
