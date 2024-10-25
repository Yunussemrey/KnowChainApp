package com.yunusemre.betaproje.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yunusemre.betaproje.databinding.PaylasimCardBinding;
import com.yunusemre.betaproje.model.PaylasimModel;

import java.util.ArrayList;

public class OrtaokulDilAnlatimAdapter extends RecyclerView.Adapter<OrtaokulDilAnlatimAdapter.PaylasimCard> {
    private ArrayList<PaylasimModel> paylasimModels;

    public OrtaokulDilAnlatimAdapter(ArrayList<PaylasimModel> paylasimModelArrayList) {
        this.paylasimModels = paylasimModelArrayList;
    }

    @NonNull
    @Override
    public OrtaokulDilAnlatimAdapter.PaylasimCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PaylasimCardBinding paylasimCardBinding = PaylasimCardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PaylasimCard(paylasimCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrtaokulDilAnlatimAdapter.PaylasimCard holder, int position) {
        holder.tasarim.paylasimCardAdTextView.setText(paylasimModels.get(position).ad);
        holder.tasarim.paylasimCardPaylasimTextView.setText(paylasimModels.get(position).text);

    }

    @Override
    public int getItemCount() {
        return paylasimModels.size();
    }

    public class PaylasimCard extends RecyclerView.ViewHolder {
        private PaylasimCardBinding tasarim;
        public PaylasimCard(PaylasimCardBinding binding) {
            super(binding.getRoot());
            this.tasarim = binding;
        }
    }
}
