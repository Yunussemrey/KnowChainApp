package com.yunusemre.betaproje.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.yunusemre.betaproje.databinding.PomodoroDersListBinding;
import com.yunusemre.betaproje.model.PomodoroDersModel;

import java.util.ArrayList;

public class PomodoroAdapter extends RecyclerView.Adapter<PomodoroAdapter.PomodoroDersList> {
    private ArrayList<PomodoroDersModel> arrayList;


    public PomodoroAdapter(ArrayList<PomodoroDersModel> arrayList) {
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public PomodoroDersList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PomodoroDersListBinding pomodoroDersList = PomodoroDersListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PomodoroDersList(pomodoroDersList);
    }

    @Override
    public void onBindViewHolder(@NonNull PomodoroAdapter.PomodoroDersList holder, int position) {
        holder.binding.pomodoroRvCalismaDurumu.setText(arrayList.get(position).getCalismaDurum()); // çalışma durumunu sayaca göre kontrol et !!
        holder.binding.pomodoroRvTarih.setText(arrayList.get(position).getTarih());
        holder.binding.pomodoroRvDersAdi.setText(arrayList.get(position).getDersAdi());
        holder.binding.pomodoroRvMail.setText(arrayList.get(position).getEmail());

    }






    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class PomodoroDersList extends RecyclerView.ViewHolder {
        private PomodoroDersListBinding binding;
        public PomodoroDersList(PomodoroDersListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
