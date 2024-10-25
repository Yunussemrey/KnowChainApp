package com.yunusemre.betaproje.fragment.ortaokul;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.adapter.OrtaokulDilAnlatimAdapter;
import com.yunusemre.betaproje.databinding.FragmentOrtaokulDilAnlatimBinding;
import com.yunusemre.betaproje.model.PaylasimModel;

import java.util.ArrayList;
import java.util.Map;


public class OrtaokulDilAnlatimFragment extends Fragment {
    private FragmentOrtaokulDilAnlatimBinding binding;
    private ArrayList<PaylasimModel> arrayList;
    private DocumentReference documentReference;
    private FirebaseFirestore firebaseFirestore;
    OrtaokulDilAnlatimAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrtaokulDilAnlatimBinding.inflate(inflater,container,false);
        arrayList = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();


        binding.ortaokulDilEkleBtn.setOnClickListener(v -> {
            // dialog sheet göster
            Toast.makeText(getContext(), "Paylaşım yapma butonu aktif", Toast.LENGTH_SHORT).show();
            NavController navController1 = Navigation.findNavController(requireView());
            navController1.navigate(R.id.ortaokulDilToPaylasim);
        });


        binding.ortaokulDilAnlatimRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrtaokulDilAnlatimAdapter(arrayList);
        binding.ortaokulDilAnlatimRv.setAdapter(adapter);


               // getData();
                shareGetData();





        return binding.getRoot();
    }
    public void getData() {
        // paylasim fragment da paylaşılan verileri al
        firebaseFirestore.collection("OrtaOkul").document("paylasimlar").addSnapshotListener((documentSnapshot, error) -> {
            if (error != null) {
                Toast.makeText(requireContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                return; // Hata varsa, dinleyici işlemi bitir.
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                Toast.makeText(requireContext(), "Veri aldık", Toast.LENGTH_SHORT).show();

                arrayList.clear(); // Önce listeyi temizleyin, çünkü her güncellemede listeyi sıfırdan dolduracağız.

                Map<String, Object> map = documentSnapshot.getData();
                if (map != null) {
                    String gonderen = (String) map.get("gönderen");
                    String mesaj = (String) map.get("mesaj");

                    PaylasimModel paylasimModel = new PaylasimModel(gonderen, mesaj);
                    arrayList.add(paylasimModel);
                }

                // RecyclerView adaptörünü bilgilendirin
                adapter.notifyDataSetChanged();
            }
        });

    }
    public void shareGetData() {
        firebaseFirestore.collection("share").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(requireContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                if (value != null) {
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String,Object> map = snapshot.getData();
                        String gonderen = (String) map.get("gönderen");
                        String text = (String) map.get("mesaj");
                        PaylasimModel paylasimModel = new PaylasimModel(gonderen,text);
                        arrayList.add(paylasimModel);

                    }
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }

}