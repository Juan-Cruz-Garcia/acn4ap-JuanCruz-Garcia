package com.example.codegym.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codegym.R;
import com.example.codegym.dto.EjercicioDTO;

import java.util.List;

//Es un puente entre un RecyclerView y los datos a mostrar.
public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder> {
    //lista donde se guardan los datos traidos de la base de datos
    private List<EjercicioDTO> ejercicios;

    public ExercisesAdapter(List<EjercicioDTO> ejercicios) {
        this.ejercicios = ejercicios;
    }

    //agarra el xml base (activity_item) y lo convierte en una view manipulable
    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    //Asocia los datos del objeto EjercicioDTO a las vistas correspondientes.
    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        EjercicioDTO ejercicio = ejercicios.get(position);
        if (ejercicio != null && ejercicio.getNombre() != null) {
            holder.nombreTextView.setText(ejercicio.getNombre());
            // Cambia el hint del EditText segun el tipo de ejercicio
            if ("peso".equalsIgnoreCase(ejercicio.getTipo())) {
                holder.ingresoPesoEditText.setHint("Peso levantado");
            } else if ("tiempo".equalsIgnoreCase(ejercicio.getTipo())) {
                holder.ingresoPesoEditText.setHint("Tiempo sostenido");
            } else if ("repeticion".equalsIgnoreCase(ejercicio.getTipo())) {
                holder.ingresoPesoEditText.setHint("cantidad echa");
            } else {
                holder.ingresoPesoEditText.setHint("Dato no especificado");
            }
        }

    }   //devuelve el numero total de elementos en  la lista

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    //llama a las variables a modificar
    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreTextView;
        private EditText ingresoPesoEditText;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreEjercicioTextView);
            ingresoPesoEditText = itemView.findViewById(R.id.ingresoPesoEditText);

        }
    }
}
