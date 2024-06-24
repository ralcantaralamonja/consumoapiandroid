package com.example.ep3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

 import androidx.appcompat.app.AppCompatActivity;

public class DetalleLibroActivity extends AppCompatActivity {
    TextView txtId, txtTitulo, txtAutor, txtAnio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_libro);

        txtId = findViewById(R.id.txtId);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtAutor = findViewById(R.id.txtAutor);
        txtAnio = findViewById(R.id.txtAnio);

        // Obtener datos del intent
        // Obtener datos del intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        int idLibro = Integer.parseInt(id); // Convertir de String a int

        String titulo = intent.getStringExtra("titulo");
        String autor = intent.getStringExtra("autor");
        String anio = intent.getStringExtra("anio");

// Mostrar los datos en los TextViews
        txtId.setText("ID: " + id);
        txtTitulo.setText("Título: " + titulo);
        txtAutor.setText("Autor: " + autor);
        txtAnio.setText("Año de Publicación: " + anio);


    };
    }
