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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Libro extends AppCompatActivity {

    Button btnAgregar;
    EditText txtIDLibro, txtTituloLibro, txtAutor, txtFechaPublicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro);

        btnAgregar = findViewById(R.id.btnAgregarLibro);
        txtIDLibro = findViewById(R.id.txtIDLibro);
        txtTituloLibro = findViewById(R.id.txtTituloLibro);
        txtTituloLibro =findViewById(R.id.txtTituloLibro);
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
        String url = "http://192.168.18.12/service/php/agregar_libro.php"; // Asegúrate de cambiar a tu URL correcta

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jso = new JSONObject(response);
                            boolean error = jso.getBoolean("error");
                            String mensaje = jso.getString("mensaje");
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
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", txtIDLibro.getText().toString());
                params.put("titulo", txtTituloLibro.getText().toString());
                params.put("autor", txtAutor.getText().toString());
                params.put("anio", txtFechaPublicacion.getText().toString()); // Asegúrate de que coincida con el nombre en PHP
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}
