package com.example.ep3;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class DetalleLibroActivity extends AppCompatActivity {
    EditText txtId, txtTitulo, txtAutor, txtAnio;
    Button btnEditar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_libro);

        txtId = findViewById(R.id.txtIdLibro);
        txtTitulo = findViewById(R.id.txtTituloLibro);
        txtAutor = findViewById(R.id.txtAutorLibro);
        txtAnio = findViewById(R.id.txtfechaPublicacion);
        btnEditar = findViewById(R.id.btnEditarLibro);
        btnEliminar = findViewById(R.id.btnEliminarLibro);

        // Obtener datos del intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String id = extras.getString("id");
            String titulo = extras.getString("titulo");
            String autor = extras.getString("autor");
            String anio = extras.getString("anio");

            // Mostrar los datos en los EditText
            txtId.setText(id);
            txtTitulo.setText(titulo);
            txtAutor.setText(autor);
            txtAnio.setText(anio);

            // Configurar clic en el botón de Eliminar
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmarEliminarLibro(id);
                }
            });

            // Configurar clic en el botón de Editar
            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Habilitar la edición de campos
                    txtTitulo.setEnabled(true);
                    txtAutor.setEnabled(true);
                    txtAnio.setEnabled(true);

                    // Cambiar texto del botón Editar a Guardar
                    btnEditar.setText("Guardar");
                    btnEditar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            actualizarLibro(id);
                        }
                    });
                }
            });
        }
    }

    // Método para mostrar un diálogo de confirmación antes de eliminar el libro
    private void confirmarEliminarLibro(final String idLibro) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar eliminación");
        builder.setMessage("¿Estás seguro de que deseas eliminar este libro?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                eliminarLibro(idLibro);
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    // Método para realizar la solicitud de eliminación del libro al backend
    private void eliminarLibro(String idLibro) {
        String url = "http://192.168.1.36/servicios/php/eliminar.php";
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
                        Log.d("EliminarLibro", "Respuesta eliminar libro: " + response.toString());
                        procesarRespuestaEliminar(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error al eliminar libro: " + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("EliminarLibro", "Error en la solicitud POST: " + error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    // Método para procesar la respuesta del servidor después de intentar eliminar el libro
    private void procesarRespuestaEliminar(JSONObject response) {
        try {
            boolean error = response.getBoolean("error");
            String mensaje = response.getString("mensaje");

            if (!error) {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
                finish(); // Cerrar la actividad después de eliminar el libro
            } else {
                Toast.makeText(this, "Error al eliminar libro: " + mensaje, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("EliminarLibro", "Error al procesar respuesta eliminar libro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para actualizar los datos del libro en el servidor
    private void actualizarLibro(String idLibro) {
        String url = "http://192.168.1.36/servicios/php/actualizar.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("id", idLibro);
            jsonParams.put("titulo", txtTitulo.getText().toString().trim());
            jsonParams.put("autor", txtAutor.getText().toString().trim());
            jsonParams.put("anio", txtAnio.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ActualizarLibro", "Respuesta actualizar libro: " + response.toString());
                        procesarRespuestaActualizar(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error al actualizar libro: " + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ActualizarLibro", "Error en la solicitud POST: " + error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    // Método para procesar la respuesta del servidor después de intentar actualizar el libro
    private void procesarRespuestaActualizar(JSONObject response) {
        try {
            boolean error = response.getBoolean("error");
            String mensaje = response.getString("mensaje");

            if (!error) {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

                // Deshabilitar la edición de campos después de guardar
                txtTitulo.setEnabled(false);
                txtAutor.setEnabled(false);
                txtAnio.setEnabled(false);

                // Restaurar texto del botón Editar
                btnEditar.setText("Editar");
            } else {
                Toast.makeText(this, "Error al actualizar libro: " + mensaje, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("ActualizarLibro", "Error al procesar respuesta actualizar libro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
