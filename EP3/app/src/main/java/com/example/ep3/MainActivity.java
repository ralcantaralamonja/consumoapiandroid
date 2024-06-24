package com.example.ep3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inicialización de vistas
        lstLibro = findViewById(R.id.lstLibro);
        btnMantenimiento = findViewById(R.id.btnMantenimiento);

        // Aplicar insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
        String url = "http://172.17.128.1:8080/servicios_rest/biblioteca.php";
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
            JSONArray jsa = new JSONArray(response);
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

            for (int i = 0; i < jsa.length(); i++) {
                JSONObject jsonObject = jsa.getJSONObject(i);

                // Obtener valores del JSON
                String id = jsonObject.getString("id");
                String titulo = jsonObject.getString("titulo");
                String autor = jsonObject.getString("autor");
                String añoPublicacion = jsonObject.getString("año_publicacion");

                // Mapear los valores en HashMap
                HashMap<String, String> map = new HashMap<>();
                map.put("id", id);
                map.put("titulo", titulo);
                map.put("autor", autor);
                map.put("año_publicacion", añoPublicacion);

                arrayList.add(map);
            }

            // Crear el adaptador y configurar el ListView
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, arrayList, R.layout.libro_lista,
                    new String[]{"titulo", "autor", "año_publicacion"}, // Utiliza las claves correctas del JSON aquí
                    new int[]{R.id.txtTituloLibros, R.id.txtAutorLibros, R.id.txtAñoPublicacionLibro}); // IDs de los elementos en libro_lista.xml

            lstLibro.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    // Al regresar de otra actividad volvemos a cargar la Lista de Autores
    @Override
    protected void onResume() {
        super.onResume();
        MostrarLibros();
    }
}
