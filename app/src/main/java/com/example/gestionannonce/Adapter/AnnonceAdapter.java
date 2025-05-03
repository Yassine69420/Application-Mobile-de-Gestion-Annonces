package com.example.gestionannonce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionannonce.Models.Annonce;

import java.util.List;

public class AnnonceAdapter extends RecyclerView.Adapter<AnnonceAdapter.AnnonceViewHolder> {

    private List<Annonce> annonces;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Annonce annonce);
    }

    private OnItemClickListener listener;

    public AnnonceAdapter(Context context, List<Annonce> annonces, OnItemClickListener listener) {
        this.context = context;
        this.annonces = annonces;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnnonceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new AnnonceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnonceViewHolder holder, int position) {
        Annonce a = annonces.get(position);
        holder.title.setText(a.titre);
        holder.desc.setText(a.prix + " DH - " + a.categorie);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(a);
        });
    }

    @Override
    public int getItemCount() {
        return annonces.size();
    }

    public void updateList(List<Annonce> newList) {
        annonces = newList;
        notifyDataSetChanged();
    }

    class AnnonceViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc;

        public AnnonceViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(android.R.id.text1);
            desc = itemView.findViewById(android.R.id.text2);
        }
    }
}
