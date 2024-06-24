package com.example.ep3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    ListView lstLibro;
    Button btnMantenimiento;
    EditText editTextID;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de vistas
        lstLibro = findViewById(R.id.lstLibro);
        btnMantenimiento = findViewById(R.id.btnMantenimiento);
        editTextID = findViewById(R.id.editTextID);
        btnBuscar = findViewById(R.id.btnDetalles);

        // Mostrar lista de libros al iniciar la actividad
        MostrarLibros();

        // Configurar clic en el botón de Mantenimiento
        btnMantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Libro.class);
                startActivity(intent);
            }
        });

        // Configurar clic en el botón de Buscar
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idString = editTextID.getText().toString().trim();
                if (!idString.isEmpty()) {
                    int idLibro = Integer.parseInt(idString);
                    obtenerDetalleLibro(idLibro);
                } else {
                    Toast.makeText(MainActivity.this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar clic en un elemento de la lista
        lstLibro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Obtener los datos del libro seleccionado
                HashMap<String, String> libro = (HashMap<String, String>) adapterView.getItemAtPosition(position);
                int idLibro = Integer.parseInt(libro.get("id")); // Convertir String a int

                Log.d(TAG, "ID del libro seleccionado: " + idLibro);

                // Llamar al método para obtener detalles del libro
                obtenerDetalleLibro(idLibro);
            }
        });

    }

    // Método para obtener la lista de libros desde el servicio web
    void MostrarLibros() {
        String url = "http://192.168.1.36/servicios/php/listar.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Respuesta obtener lista de libros: " + response.toString());
                        LLenarLista(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error al obtener lista de libros: " + error.toString(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error en la solicitud GET: " + error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    // Método para llenar la lista de libros con los datos recibidos del servicio web
    void LLenarLista(JSONObject response) {
        try {
            Log.d(TAG, "Respuesta JSON lista de libros: " + response.toString());
            JSONArray jsa = response.getJSONArray("libros");
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
            Toast.makeText(this, "Error al procesar lista de libros: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Error al procesar JSON de lista de libros: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para obtener los detalles de un libro específico desde el servicio web
    void obtenerDetalleLibro(int idLibro) {
        String url = "http://192.168.1.36/servicios/php/buscar.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("id", idLibro);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Respuesta obtener detalles del libro: " + response.toString());
                        mostrarDetalleLibroEnDetalleActivity(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error al obtener detalles del libro: " + error.toString(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error en la solicitud POST: " + error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    // Método para mostrar los detalles del libro seleccionado en DetalleLibroActivity
    void mostrarDetalleLibroEnDetalleActivity(JSONObject response) {
        try {
            Log.d(TAG, "Respuesta JSON detalles del libro: " + response.toString());
            boolean error = response.getBoolean("error");

            if (!error) {
                JSONObject libroJson = response.getJSONObject("libro");

                int id = libroJson.getInt("id"); // Obtener como entero
                String titulo = libroJson.getString("titulo");
                String autor = libroJson.getString("autor");
                String anio = libroJson.getString("anio");

                Log.d(TAG, "Detalles del libro: ID=" + id + ", Título=" + titulo + ", Autor=" + autor + ", Año=" + anio);

                // Abrir DetalleLibroActivity y pasar datos del libro
                Intent intent = new Intent(getApplicationContext(), DetalleLibroActivity.class);
                intent.putExtra("id", String.valueOf(id));
                intent.putExtra("titulo", titulo);
                intent.putExtra("autor", autor);
                intent.putExtra("anio", anio);
                startActivity(intent);

            } else {
                Toast.makeText(this, "Error al obtener detalles del libro", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Error al procesar JSON de detalles del libro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista de libros al volver a la actividad
        MostrarLibros();
    }
}
