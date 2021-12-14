package com.example.proyectom;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import modelos.Descripcion;
import modelos.Material;

public class Registrar_Material extends Fragment {
    EditText edtNombre, edtPrecio;
    Button Agregar;
    TableLayout TablaMa;
    TableRow fila;
    Spinner spinUnidad;
    TextView tvId, tvNombre, tvPrecio, tvUnidad;
    RequestQueue requestQueue;
    public static ArrayList<Material> listaMaterial=new ArrayList<>();
    TableRow.LayoutParams layoutfila=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutId=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutNombre=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutPrecio=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutUnidad=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registrar__material, container, false);
        cargarControle(root);
        cargarEncabezado();
        CargarDatos("http://192.168.8.8/ControlProyectoBD/consultar_materiales.php");
        CargarEventos();
        return root;
    }

    private void cargarEncabezado() {
        fila = new TableRow(getActivity());
        fila.setLayoutParams(layoutfila);

        tvId = new TextView(getActivity());
        tvId.setText("ID");
        tvId.setGravity(Gravity.CENTER);
        tvId.setBackgroundColor(Color.BLACK);
        tvId.setTextColor(Color.WHITE);
        tvId.setPadding(10,10,10,10);
        tvId.setLayoutParams(layoutId);
        fila.addView(tvId);

        tvNombre = new TextView(getActivity());
        tvNombre.setText("NOMBRE");
        tvNombre.setGravity(Gravity.CENTER);
        tvNombre.setBackgroundColor(Color.BLACK);
        tvNombre.setTextColor(Color.WHITE);
        tvNombre.setPadding(10,10,10,10);
        tvNombre.setLayoutParams(layoutNombre);
        fila.addView(tvNombre);

        tvPrecio = new TextView(getActivity());
        tvPrecio.setText("PRECIO$");
        tvPrecio.setGravity(Gravity.CENTER);
        tvPrecio.setBackgroundColor(Color.BLACK);
        tvPrecio.setTextColor(Color.WHITE);
        tvPrecio.setPadding(10,10,10,10);
        tvPrecio.setLayoutParams(layoutPrecio);
        fila.addView(tvPrecio);

        tvUnidad = new TextView(getActivity());
        tvUnidad.setText("Unidad");
        tvUnidad.setGravity(Gravity.CENTER);
        tvUnidad.setBackgroundColor(Color.BLACK);
        tvUnidad.setTextColor(Color.WHITE);
        tvUnidad.setPadding(10,10,10,10);
        tvUnidad.setLayoutParams(layoutUnidad);
        fila.addView(tvUnidad);
        TablaMa.addView(fila);
    }

    private void CargarEventos() {
        Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarMaterial("http://192.168.8.8/ControlProyectoBD/insertar_material.php");
            }
        });
        tvNombre.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
    }

    private void Limpiar() {
        edtNombre.setText("");
        edtPrecio.setText("");
        spinUnidad.setSelection(0);
    }

    private void AgregarMaterial(String URL) {
        if(!edtNombre.getText().toString().isEmpty() &&
        !edtPrecio.getText().toString().isEmpty() &&
        !spinUnidad.getSelectedItem().toString().equals("Unidad...")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getActivity(), "Operacion Exitosa", Toast.LENGTH_SHORT).show();
                    CargarNuevoDato("http://192.168.8.8/ControlProyectoBD/consultar_materiales.php");
                    Limpiar();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros=new HashMap<String, String>();
                    parametros.put("Nombre",edtNombre.getText().toString());
                    parametros.put("Precio",edtPrecio.getText().toString());
                    parametros.put("unidad_de_medida",spinUnidad.getSelectedItem().toString());
                    return parametros;

                }
            };
            requestQueue= Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(getActivity(), "Llena todos los campos", Toast.LENGTH_SHORT).show();
        }


    }

    private void CargarNuevoDato(String URL) {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        if(i==response.length()-1){
                            jsonObject = response.getJSONObject(i);
                            fila = new TableRow(getActivity());
                            fila.setLayoutParams(layoutfila);

                            tvId = new TextView(getActivity());
                            tvId.setText(jsonObject.getString("id_material"));
                            tvId.setPadding(10,10,10,10);
                            tvId.setLayoutParams(layoutId);
                            fila.addView(tvId);

                            tvNombre = new TextView(getActivity());
                            tvNombre.setText(jsonObject.getString("Nombre"));
                            tvNombre.setPadding(10,10,10,10);
                            tvNombre.setLayoutParams(layoutNombre);
                            fila.addView(tvNombre);

                            tvPrecio = new TextView(getActivity());
                            tvPrecio.setText(jsonObject.getString("Precio"));
                            tvPrecio.setPadding(10,10,10,10);
                            tvPrecio.setLayoutParams(layoutPrecio);
                            fila.addView(tvPrecio);

                            tvUnidad = new TextView(getActivity());
                            tvUnidad.setText(jsonObject.getString("unidad_de_medida"));
                            tvUnidad.setPadding(10,10,10,10);
                            tvUnidad.setLayoutParams(layoutUnidad);
                            fila.addView(tvUnidad);
                            TablaMa.addView(fila);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Error de Conexion",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }

    private void CargarDatos(String URL) {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        fila = new TableRow(getActivity());
                        fila.setLayoutParams(layoutfila);

                        tvId = new TextView(getActivity());
                        tvId.setText(jsonObject.getString("id_material"));
                        tvId.setPadding(10,10,10,10);
                        tvId.setLayoutParams(layoutId);
                        fila.addView(tvId);

                        tvNombre = new TextView(getActivity());
                        tvNombre.setText(jsonObject.getString("Nombre"));
                        tvNombre.setPadding(10,10,10,10);
                        tvNombre.setLayoutParams(layoutNombre);
                        fila.addView(tvNombre);

                        tvPrecio = new TextView(getActivity());
                        tvPrecio.setText(jsonObject.getString("Precio"));
                        tvPrecio.setPadding(10,10,10,10);
                        tvPrecio.setLayoutParams(layoutPrecio);
                        fila.addView(tvPrecio);

                        tvUnidad = new TextView(getActivity());
                        tvUnidad.setText(jsonObject.getString("unidad_de_medida"));
                        tvUnidad.setPadding(10,10,10,10);
                        tvUnidad.setLayoutParams(layoutUnidad);
                        fila.addView(tvUnidad);
                        TablaMa.addView(fila);
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Error de Conexion",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);

    }

    private void cargarControle(View root) {
        edtNombre = root.findViewById(R.id.ma_tx_Nombre);
        edtPrecio = root.findViewById(R.id.ma_tx_Precio);
        Agregar = root.findViewById(R.id.ma_btn_Agregar);
        TablaMa = root.findViewById(R.id.ma_tab_material);
        spinUnidad = root.findViewById(R.id.ma_spin_Unidad);
    }


}