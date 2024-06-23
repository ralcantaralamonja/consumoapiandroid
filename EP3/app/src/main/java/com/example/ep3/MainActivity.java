package com.example.ep3;

import android.os.Bundle;
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

    ListView lstLibros;
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
        lstLibros = (ListView) findViewById(R.id.lstLibros);
        MostrarAutores();
    }
    void MostrarAutores(){
        String url ="xx";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LLenarLista(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),volleyError.toString()+"", Toast.LENGTH_LONG).show();
            }
        }
        );
        requestQueue.add(stringRequest);
    }
    void LLenarLista(String response){
        JSONObject jso;
        JSONArray jsa;
        try{
            //Verificamos que existan datos
            if(!response.isEmpty()){
                jso = new JSONObject(response);
                jsa = jso.getJSONArray("autor");
                ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
                for (int i = 0 ; i<jsa.length();i++){
                    JSONObject jsonObject = jsa.getJSONObject(i);
                    HashMap<String,String> map = new HashMap<>();
                    map.put("id",jsonObject.getInt("id")+"");
                    map.put("nombre",jsonObject.getString("nombre")+"");
                    map.put("nacionalidad",jsonObject.getString("nacionalidad")+"");
                    map.put("fecha_nacimiento",jsonObject.getString("fecha_nacimiento")+"");
                    map.put("biografia",jsonObject.getString("biografia")+"");
                    arrayList.add(map);
                }
                //Creamos un adaptador
                ListAdapter adapter = new SimpleAdapter(this,arrayList,R.layout.libro_lista,new String[]{"nombre","nacionalidad","fecha_nacimiento","biografia"}, 
                        new int[]{R.id.txtnombre,R.id.txtnacionalidad,R.id.txtfecha,R.id.txtbiografia});
                //Poblamos el listView de Productos
                lstLibros.setAdapter(adapter);
            }       
        }
        catch (Exception er){
                Toast.makeText(this,er.toString(),Toast.LENGTH_LONG).show();
        }
    }
}