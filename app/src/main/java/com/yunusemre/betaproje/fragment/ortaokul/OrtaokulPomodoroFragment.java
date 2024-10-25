package com.yunusemre.betaproje.fragment.ortaokul;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yunusemre.betaproje.R;
import com.yunusemre.betaproje.adapter.PomodoroAdapter;
import com.yunusemre.betaproje.databinding.FragmentOrtaokulPomodoroBinding;
import com.yunusemre.betaproje.model.PomodoroDersModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class OrtaokulPomodoroFragment extends Fragment {

  private FragmentOrtaokulPomodoroBinding binding;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private boolean isWorkSession = true;
    private int sessionCount = 1; // Seans numarası
    private long timeLeftInMillis = 1500000; // 25 dakika (milisaniye olarak) - 1500000
    private final long workTime = 1500000; // 25 dakika - 1500000
    private final long shortBreakTime = 300000; // 5 dakika - 300000
    private final long longBreakTime = 1200000; // 20 dakika
    private MediaPlayer mediaPlayer;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;
    private FirebaseUser user;
    private Handler handler = new Handler();
    private Runnable runnable;
    private ArrayList<PomodoroDersModel> arrayList;
    private PomodoroAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrtaokulPomodoroBinding.inflate(inflater, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        arrayList = new ArrayList<>();

        backTouch();
        String tarih = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        binding.pomodoroTarihTextView.setText(tarih);

        binding.pomodoroBaslaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                    binding.pomodoroDersEkle.setOnClickListener(v1 -> {
                        Toast.makeText(getContext(), "süre devam ederken yeni ders ekleyemezsin", Toast.LENGTH_SHORT).show();
                    });

                }
            }
        });
        updateTimerText();
        updateSessionText();



        binding.pomodoroDersEkle.setOnClickListener(v -> {
            alertDersEkle();
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                updateDateTime();
                handler.postDelayed(this, 1000); // Her dakika güncelle
            }
        };

        // Runnable'ı başlat
        handler.post(runnable);


        // varsayılan dersleri yükle
        dersGetir("ders geldi");

        binding.pomodoroRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PomodoroAdapter(arrayList);
        binding.pomodoroRv.setAdapter(adapter);


















        return binding.getRoot();
    }
    private void updateDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM - HH:mm:ss", Locale.getDefault());
        String currentDateTime = sdf.format(new Date());
        binding.pomodoroTarihTextView.setText(currentDateTime);
    }
    public void backTouch() {
        OnBackPressedCallback geriTus = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Snackbar.make(getView(),"KnowChain'den çıkmak ister misin ?",Snackbar.LENGTH_SHORT).setAction("Evet", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                }).show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),geriTus);
    }


    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                // Alarm çal
                playAlarm();
                if (isWorkSession) {
                    sessionCount++;
                    // 25 dakkalık çalışma süresi bitti
                    // güncelleme işlemleri



                    if (sessionCount ==5) {
                        timeLeftInMillis = longBreakTime;
                        binding.pomodoroDurumTextView.setText("20 Dakikalık Mola!");
                       binding.pomodoroYontemTextView.setText("Uzun aradasınız");
                       sessionCount = 1;

                    } else {
                        timeLeftInMillis = shortBreakTime;
                        binding.pomodoroDurumTextView.setText("5 Dakikalık Mola!");
                    }
                } else {
                    // mola bittikten sonraki işlemler


                    timeLeftInMillis = workTime;
                    binding.pomodoroDurumTextView.setText("25 Dakikalık Çalışma!");
                }
                if (timeLeftInMillis == longBreakTime) {
                    binding.pomodoroDurumTextView.setText("20 Dakikalık Mola!");
                } else if (isWorkSession) {
                    binding.pomodoroDurumTextView.setText("25 Dakikalık Çalışma!");
                } else {
                    binding.pomodoroDurumTextView.setText("5 Dakikalık Mola!");
                }

                isWorkSession = !isWorkSession;
                updateSessionText();
                updateLongBreakInfo();
                if (isWorkSession) {
                    binding.pomodoroBaslaBtn.setText("çalışmayı Başlat");

                }else {
                    binding.pomodoroBaslaBtn.setText("Molayı Başlat");
                }

                isRunning = false;
            }
        }.start();

        binding.pomodoroBaslaBtn.setText("Duraklat");
        isRunning = true;
        if (isWorkSession) {
            binding.pomodoroDurumTextView.setText("25 Dakikalık Çalışma!");
        } else {
            binding.pomodoroDurumTextView.setText("5 Dakikalık Mola!");
        }

    }

    private void pauseTimer() {
        countDownTimer.cancel();
        binding.pomodoroBaslaBtn.setText("Devam Et");
        isRunning = false;
        if (isWorkSession) {
            binding.pomodoroDurumTextView.setText("Çalışmaya ara verildi");
        }else {
            binding.pomodoroDurumTextView.setText("mola durduruldu");
        }

    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        binding.pomodoroSaat.setText(timeFormatted);
    }

    private void updateSessionText() {
        String sessionText = "Seans: " + sessionCount;
        binding.pomodoroSeansTextView.setText(sessionText);
    }

    private void updateLongBreakInfo() {
        int sessionsToLongBreak = 4 - sessionCount;
        String longBreakInfo = "uzun araya "+sessionsToLongBreak+" seans";
        binding.pomodoroYontemTextView.setText(longBreakInfo);
    }





    private void playAlarm() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.alarm); // Ses dosyasını çal
        }
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release(); // Ses bittiğinde MediaPlayer'i serbest bırak
                mediaPlayer = null;
            }
        });
    }

    private void alertDersEkle() {
        LinearLayout linearLayout = requireActivity().findViewById(R.id.alert_dialog_settings);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.ortaokul_setting_alert_dialog,linearLayout);
        Button mat = view.findViewById(R.id.pomodoroDers_1);
        Button turkce = view.findViewById(R.id.pomodoroDers_2);
        Button fen = view.findViewById(R.id.pomodoroDers_3);
        Button dil = view.findViewById(R.id.pomodoroDers_4);
        Button sosyal = view.findViewById(R.id.pomodoroDers_5);
        Button tarih = view.findViewById(R.id.pomodoroDers_6);
        Button din = view.findViewById(R.id.pomodoroDers_7);
        Button ing = view.findViewById(R.id.pomodoroDers_8);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        final AlertDialog alertDialog = alert.create();
        mat.setVisibility(View.VISIBLE);
        mat.setOnClickListener(v -> {
            dersKayit("matematik");
            alertDialog.dismiss();
        });
        turkce.setVisibility(View.VISIBLE);
        turkce.setOnClickListener(v -> {
            dersKayit("türkçe");
            alertDialog.dismiss();
        });
        fen.setVisibility(View.VISIBLE);
        fen.setOnClickListener(v -> {
            dersKayit("fen bilgisi");
            alertDialog.dismiss();
        });
        dil.setVisibility(View.VISIBLE);
        dil.setOnClickListener(v -> {
            dersKayit("dil ve anlatım");
            alertDialog.dismiss();
        });
        sosyal.setVisibility(View.VISIBLE);
        sosyal.setOnClickListener(v -> {
            dersKayit("Sosyal Bilgiler");
            alertDialog.dismiss();
        });
        tarih.setVisibility(View.VISIBLE);
        tarih.setOnClickListener(v -> {
            dersKayit("T.C. İnkılap Tarihi ve Atatürkçülük");
            alertDialog.dismiss();
        });
        din.setVisibility(View.VISIBLE);
        din.setOnClickListener(v -> {
            dersKayit("Din Kültürü ve Ahlak Bilgisi");
            alertDialog.dismiss();
        });
        ing.setVisibility(View.VISIBLE);
        ing.setOnClickListener(v -> {
            dersKayit("İngilizce");
            alertDialog.dismiss();
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
    private void dersKayit(String dersAdi) {
        String tarih = binding.pomodoroTarihTextView.getText().toString();
        String userEmail = user.getEmail();
        Map<String,Object> map = new HashMap<>();
        map.put("DersAdi",dersAdi);
        map.put("tarih",tarih);
        map.put("email",userEmail);


        firebaseFirestore.collection("OrtaOkul").document("PomodoroDers").collection(userEmail).add(map).addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
            Toast.makeText(getContext(), "Ders çalışma durumu kaydedildi", Toast.LENGTH_SHORT).show();


        }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void dersGetir(String calismaDurumu) {
        String userEmail = user.getEmail();
        collectionReference = firebaseFirestore.collection("OrtaOkul").document("PomodoroDers").collection(userEmail);
        collectionReference.orderBy("tarih", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                if (value != null) {
                    for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                        Map<String, Object> map = documentSnapshot.getData();

                        String tarih = (String) map.get("tarih");
                        String ders = (String) map.get("DersAdi");
                        String mail = (String) map.get("email");
                        PomodoroDersModel pomodoroDersModel = new PomodoroDersModel(ders, tarih, calismaDurumu, mail);
                        arrayList.add(pomodoroDersModel);


                    }
                    adapter.notifyDataSetChanged();
                }

            }
        });


    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        // Handler'ı temizle
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }
    private  void depo() {
    }
}