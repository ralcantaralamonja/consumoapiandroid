package com.example.ep3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Libro extends AppCompatActivity {

    Button btnAgregar;
    EditText txtTituloLibro, txtAutor, txtFechaPublicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro);

        btnAgregar = findViewById(R.id.btnAgregarLibro);
        txtTituloLibro = findViewById(R.id.txtTituloLibro);
        txtAutor = findViewById(R.id.txtAutorLibro);
        txtFechaPublicacion = findViewById(R.id.txtfechaPublicacion);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarLibro();
            }
        });
    }

    void agregarLibro() {
        String url = "http://192.168.1.36/servicios/php/agregar.php"; // Cambiar a tu URL correcta

        // Crear un objeto JSON para enviar los datos
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("titulo", txtTituloLibro.getText().toString());
            jsonObject.put("autor", txtAutor.getText().toString());
            jsonObject.put("anio", txtFechaPublicacion.getText().toString()); // Debe ser un String, no un int
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Crear una solicitud JSON usando Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean error = response.getBoolean("error");
                            String mensaje = response.getString("mensaje");
                            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                            if (!error) {
                                // Libro registrado correctamente, puedes realizar acciones adicionales si es necesario
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error de conexión
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

        // Agregar la solicitud a la cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}
