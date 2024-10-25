package com.yunusemre.betaproje.fragment.ortaokul;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentOrtaokulPaylasimBinding;
import com.yunusemre.betaproje.model.PaylasimModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class OrtaokulPaylasimFragment extends Fragment {
    private FragmentOrtaokulPaylasimBinding binding;
    private ArrayList<PaylasimModel> paylasimModels;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser user;
    private CollectionReference collectionReference;
    private CollectionReference collectionReference1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrtaokulPaylasimBinding.inflate(inflater,container,false);

        paylasimModels = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();



        String userMail = user.getEmail();

        binding.ortaokulPaylasimBtn.setOnClickListener(v -> {
            String paylasim = binding.ortaokulPaylasimEditText.getText().toString();

            Map<String,Object> map = new HashMap<>();
            map.put("gönderen",userMail);
            map.put("mesaj",paylasim);


            firebaseFirestore.collection("share")
                    .add(map).addOnCompleteListener(task -> {
                       if (task.isSuccessful()) {
                           Toast.makeText(getContext(), "Paylaşıldı", Toast.LENGTH_SHORT).show();
                       }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    });


        });












        return binding.getRoot();
    }
}