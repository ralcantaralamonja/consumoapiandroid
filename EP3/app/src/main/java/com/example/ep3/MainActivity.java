package com.example.ep3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView lstLibro;
    Button btnMantenimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de vistas
        lstLibro = findViewById(R.id.lstLibro);
        btnMantenimiento = findViewById(R.id.btnMantenimiento);

        MostrarLibros();

        btnMantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Libro.class);
                startActivity(intent);
            }
        });
    }

    void MostrarLibros() {
        String url = "http://192.168.18.12/service/php/listar.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LLenarLista(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);
    }

    void LLenarLista(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsa = jsonObject.getJSONArray("libros");
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

            for (int i = 0; i < jsa.length(); i++) {
                JSONObject libro = jsa.getJSONObject(i);

                String id = libro.getString("id");
                String titulo = libro.getString("titulo");
                String autor = libro.getString("autor");
                String anio = libro.getString("anio");

                HashMap<String, String> map = new HashMap<>();
                map.put("id", id);
                map.put("titulo", titulo);
                map.put("autor", autor);
                map.put("anio", anio);

                arrayList.add(map);
            }

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, arrayList, R.layout.libro_lista,
                    new String[]{"id", "titulo", "autor", "anio"},
                    new int[]{R.id.txtIdLibro, R.id.txtTituloLibros, R.id.txtAutorLibros, R.id.txtAñoPublicacionLibro}
            );

            lstLibro.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MostrarLibros();
    }
}
