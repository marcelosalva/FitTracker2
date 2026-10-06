package com.example.fittracker2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import android.widget.ProgressBar;
import android.widget.RatingBar;


public class RegistroActivity extends AppCompatActivity {

    EditText txtEjercicio, txtDuracion, txtCalorias, txtFecha;
    Button btnGuardar, btnHistorial;
    CheckBox chkEntrenamientoCompletado;

    CheckBox chkCalentamiento, chkHidratacion, chkEstiramiento;

    RadioGroup radioGroupIntensidad;
    RadioButton rbBaja, rbMedia, rbAlta;

    Spinner spinnerEntrenamiento;

    RecyclerView recyclerEntrenamientos;
    ArrayList<Entrenamiento> listaEntrenamientos;
    EntrenamientoAdapter entrenamientoAdapter;

    ProgressBar progressMeta;
    RatingBar ratingEsfuerzo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        txtEjercicio = findViewById(R.id.editTextText);
        txtDuracion = findViewById(R.id.editTextNumberDecimal);
        txtCalorias = findViewById(R.id.editTextNumberDecimal2);
        txtFecha = findViewById(R.id.editTextDate);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnHistorial = findViewById(R.id.btnHistorial);
        chkEntrenamientoCompletado =
                findViewById(R.id.chkEntrenamientoCompletado);

        radioGroupIntensidad = findViewById(R.id.radioGroupIntensidad);
        rbBaja = findViewById(R.id.rbBaja);
        rbMedia = findViewById(R.id.rbMedia);
        rbAlta = findViewById(R.id.rbAlta);


        spinnerEntrenamiento = findViewById(R.id.spinnerEntrenamiento);


        chkCalentamiento = findViewById(R.id.chkCalentamiento);
        chkHidratacion = findViewById(R.id.chkHidratacion);
        chkEstiramiento = findViewById(R.id.chkEstiramiento);

        progressMeta = findViewById(R.id.progressMeta);
        ratingEsfuerzo = findViewById(R.id.ratingEsfuerzo);

        String[] tipos = {"Fuerza", "Cardio", "Yoga", "Calistenia"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tipos
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerEntrenamiento.setAdapter(adapter);

        recyclerEntrenamientos = findViewById(R.id.recyclerEntrenamientos);

        listaEntrenamientos = new ArrayList<>();

        entrenamientoAdapter = new EntrenamientoAdapter(listaEntrenamientos);

        recyclerEntrenamientos.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerEntrenamientos.setAdapter(entrenamientoAdapter);

        btnGuardar.setOnClickListener(v -> {

            String ejercicio = txtEjercicio.getText().toString();
            String duracion = txtDuracion.getText().toString();
            String calorias = txtCalorias.getText().toString();
            String fecha = txtFecha.getText().toString();

            if (ejercicio.isEmpty() || duracion.isEmpty() ||
                    calorias.isEmpty() || fecha.isEmpty()) {

                Toast.makeText(
                        RegistroActivity.this,
                        "Completa todos los campos",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }


            // Obtener tipo de entrenamiento del Spinner
            String tipoEntrenamiento =
                    spinnerEntrenamiento.getSelectedItem().toString();

            // Obtener intensidad seleccionada
            int idIntensidad = radioGroupIntensidad.getCheckedRadioButtonId();

            String intensidad = "Sin seleccionar";

            if (idIntensidad != -1) {
                RadioButton radioSeleccionado = findViewById(idIntensidad);
                intensidad = radioSeleccionado.getText().toString();
            }

            // Obtener valores de los CheckBox
            boolean calentamiento = chkCalentamiento.isChecked();
            boolean hidratacion = chkHidratacion.isChecked();
            boolean estiramiento = chkEstiramiento.isChecked();

             // Obtener progreso y esfuerzo

            float esfuerzo = ratingEsfuerzo.getRating();

            try {
                int minutos = (int) Double.parseDouble(duracion);

                int nuevoProgreso = progressMeta.getProgress() + minutos;

                if (nuevoProgreso > 100) {
                    nuevoProgreso = 100;
                }

                progressMeta.setProgress(nuevoProgreso);

            } catch (NumberFormatException e) {
                Toast.makeText(
                        RegistroActivity.this,
                        "La duración debe ser un número",
                        Toast.LENGTH_SHORT
                ).show();
            }





            SharedPreferences preferences =
                    getSharedPreferences("entrenamientos", MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("ejercicio", ejercicio);
            editor.putString("duracion", duracion);
            editor.putString("calorias", calorias);
            editor.putString("fecha", fecha);

            editor.apply();


            Entrenamiento nuevoEntrenamiento =
                    new Entrenamiento(ejercicio, duracion, calorias, fecha);

            listaEntrenamientos.add(nuevoEntrenamiento);

            entrenamientoAdapter.notifyItemInserted(
                    listaEntrenamientos.size() - 1
            );




            String resumen =
                    "Guardado: " + tipoEntrenamiento +
                            "\nIntensidad: " + intensidad +
                            "\nEsfuerzo: " + esfuerzo + "/5" +
                            "\nCalentamiento: " + (calentamiento ? "Sí" : "No") +
                            "\nHidratación: " + (hidratacion ? "Sí" : "No") +
                            "\nEstiramiento: " + (estiramiento ? "Sí" : "No") +
                            "\nProgreso: " + progressMeta.getProgress() + "%";

            Toast.makeText(
                    RegistroActivity.this,
                    resumen,
                    Toast.LENGTH_LONG
            ).show();

            txtEjercicio.setText("");
            txtDuracion.setText("");
            txtCalorias.setText("");
            txtFecha.setText("");

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}