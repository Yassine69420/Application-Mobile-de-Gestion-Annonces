package com.example.gestionannonce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionannonce.Models.Annonce;
import com.example.gestionannonce.R;

import java.util.List;
public class AnnonceAdapter extends RecyclerView.Adapter<AnnonceAdapter.AnnonceViewHolder> {
    private List<Annonce> annonces;
    private Context context;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(Annonce annonce);
    }

    public AnnonceAdapter(Context context, List<Annonce> annonces, OnItemClickListener listener) {
        this.context = context;
        this.annonces = annonces;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnnonceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_annonce, parent, false);
        return new AnnonceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnonceViewHolder holder, int position) {
        Annonce annonce = annonces.get(position);
        holder.title.setText(annonce.titre);
        holder.price.setText(annonce.prix + " DH");
        holder.description.setText(annonce.description);
        holder.category.setText(annonce.categorie);
        holder.date.setText(annonce.date);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(annonce);
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
        TextView title, price, description, category, date;

        public AnnonceViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.description);
            category = itemView.findViewById(R.id.category);
            date = itemView.findViewById(R.id.date);
        }
    }
}
