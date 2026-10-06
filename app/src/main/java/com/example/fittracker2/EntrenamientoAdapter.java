package com.example.fittracker2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EntrenamientoAdapter
        extends RecyclerView.Adapter<EntrenamientoAdapter.ViewHolder> {

    private ArrayList<Entrenamiento> listaEntrenamientos;

    public EntrenamientoAdapter(ArrayList<Entrenamiento> listaEntrenamientos) {
        this.listaEntrenamientos = listaEntrenamientos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_entrenamiento, parent, false);

        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Entrenamiento entrenamiento = listaEntrenamientos.get(position);

        holder.txtEjercicio.setText(entrenamiento.getEjercicio());
        holder.txtDuracion.setText(
                "Duración: " + entrenamiento.getDuracion() + " minutos"
        );
        holder.txtCalorias.setText(
                "Calorías: " + entrenamiento.getCalorias()
        );
        holder.txtFecha.setText(
                "Fecha: " + entrenamiento.getFecha()
        );
    }

    @Override
    public int getItemCount() {
        return listaEntrenamientos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtEjercicio;
        TextView txtDuracion;
        TextView txtCalorias;
        TextView txtFecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtEjercicio = itemView.findViewById(R.id.txtItemEjercicio);
            txtDuracion = itemView.findViewById(R.id.txtItemDuracion);
            txtCalorias = itemView.findViewById(R.id.txtItemCalorias);
            txtFecha = itemView.findViewById(R.id.txtItemFecha);
        }
    }
}