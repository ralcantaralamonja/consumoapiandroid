package com.example.ep3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Autor extends AppCompatActivity {

    Button btnAgregar = (Button) findViewById(R.id.btnAgregarAutor);
    EditText txtIDAutor = (EditText) findViewById(R.id.txtIDAutor), txtNombreAutor = (EditText) findViewById(R.id.txtNombreAutor),
            txtNacionalidad = (EditText) findViewById(R.id.txtNacionalidad),
            txtFechaAutor = (EditText) findViewById(R.id.txtfechaAutor),
            txtBiografiaAutor = (EditText) findViewById(R.id.txtBiografiaAutor);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarAutor();
            }
        });
    }
    void AgregarAutor(){
        String url = "XX";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jso = new JSONObject(response);
                            String mensaje = jso.getString("mensaje");
                            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                            if (mensaje.equals("Autor Registrado Ok")){
                                finish();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),volleyError.toString()+"",Toast.LENGTH_LONG).show();

            }
        }){ // Pasamos variables

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prms = new HashMap<String,String>();
                prms.put("ID",txtIDAutor.getText().toString());
                prms.put("Nombre",txtNombreAutor.getText().toString());
                prms.put("Nacionalidad",txtNacionalidad.getText().toString());
                prms.put("Fecha",txtFechaAutor.getText().toString());
                prms.put("Biografia",txtBiografiaAutor.getText().toString());
                return prms;
            }
        };
        requestQueue.add(stringRequest);
    }
}
