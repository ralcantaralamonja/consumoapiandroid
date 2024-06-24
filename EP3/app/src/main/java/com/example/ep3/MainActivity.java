package com.example.ep3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
import org.json.JSONException;
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

        // Aplicar insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Mostrar la lista de libros al iniciar la actividad
        MostrarLibros();

        // Configurar el botón para ir a la actividad de mantenimiento
        btnMantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Libro.class);
                startActivity(intent);
            }
        });

        // Configurar el listener para el clic en los elementos de la lista
        lstLibro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el libro seleccionado
                HashMap<String, String> selectedItem = (HashMap<String, String>) parent.getItemAtPosition(position);
                String libroId = selectedItem.get("id");

                // Mostrar ID en un Toast
                Toast.makeText(MainActivity.this, "ID del libro seleccionado: " + libroId, Toast.LENGTH_SHORT).show();

                // Imprimir en la consola para verificar
                Log.d("MainActivity", "ID del libro seleccionado: " + libroId);

                // Llamar a la función para buscar el libro por su ID
                // buscarLibro(libroId); // Comentado temporalmente para no realizar la solicitud al servicio web en este ejemplo
            }
        });
    }

    // Método para obtener y mostrar la lista de libros desde el servicio web
    void MostrarLibros() {
        String url = "http://192.168.18.12/service/php/biblioteca.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LLenarLista(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error al obtener la lista de libros: " + volleyError.toString(), Toast.LENGTH_LONG).show();
                volleyError.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);
    }

    // Método para llenar el ListView con la lista de libros obtenida del servicio web
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
                    new String[]{"id", "titulo", "autor", "año_publicacion"}, // Claves del HashMap
                    new int[]{R.id.txtLibro, R.id.txtTituloLibros, R.id.txtAutorLibros, R.id.txtAñoPublicacionLibro}); // IDs de los TextView en libro_lista.xml

            lstLibro.setAdapter(adapter);

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error al procesar la respuesta JSON", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Método para buscar y mostrar los detalles de un libro por su ID
    // Este método se puede implementar según sea necesario para realizar la solicitud al servidor y mostrar los detalles del libro
}
