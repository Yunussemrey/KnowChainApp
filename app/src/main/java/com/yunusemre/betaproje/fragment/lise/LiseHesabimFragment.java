package com.yunusemre.betaproje.fragment.lise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.databinding.FragmentLiseHesabimBinding;


public class LiseHesabimFragment extends Fragment {
    private FragmentLiseHesabimBinding binding;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLiseHesabimBinding.inflate(inflater,container,false);

        auth = FirebaseAuth.getInstance();







        binding.liseHesabimSecenekBtn.setOnClickListener(v -> {
            optionMenu(v);
        });









        return binding.getRoot();
    }
    private void optionMenu(View v) {
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
                    navController1.navigate(R.id.liseHesabimToGiris);
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