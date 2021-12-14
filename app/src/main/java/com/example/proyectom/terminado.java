package com.example.proyectom;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class terminado extends Fragment {
    TextView tvNombre, tvId, tvNomMaterial,tvCantidad, tvPrecio, tvTotal, tvTotalFinal;
    Spinner spinNombre;
    EditText edtCantidad;
    Button btnAgregar, btnModificar, btnEliminar, btnGuardar;
    TableLayout tabTerminado;
    TableRow fila;
    RequestQueue requestQueue;
    ArrayList<String> materiales=new ArrayList<>();
    int idMueble, idTerminado;
    String nom;
    double preTotal=0;
    boolean modifica=false;
    String id;
    TableRow.LayoutParams layoutfila=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutId=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutNombre=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutCantidad=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutPrecio=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutTotal=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_terminado, container, false);
        cargarControles(root);
        cargarEventos();
        AlertaEntrar();
        cargarSpiner();
        CargarEncabezado();
        return root;
    }

    private void cargarEventos() {
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modifica){
                    insertarTerminado("http://192.168.8.8/ControlProyectoBD/modificar_terminado.php");
                }else{
                    insertarTerminado("http://192.168.8.8/ControlProyectoBD/insertar_terminado.php");
                }

            }
        });
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertaModificar();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertaEliminar();
            }
        });
    }
    private void AlertaEliminar() {
        AlertDialog.Builder alert=new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        alert.setTitle("Cotizar")
                .setMessage("Por favor ingresa el id del Material que desea Eliminar")
                .setView(input)
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        idTerminado= Integer.parseInt(input.getText().toString());
                        EliminarConfirm();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create().show();
    }
    private void EliminarConfirm() {
        AlertDialog.Builder alert=new AlertDialog.Builder(getActivity());
        alert.setTitle("Eliminar")
                .setMessage("¿Esta seguro que desea eliminar el Material?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminarTerminado("http://192.168.8.8/ControlProyectoBD/eliminar_terminado.php");

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create().show();
    }
    private void eliminarTerminado(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "Material Eliminado", Toast.LENGTH_SHORT).show();
                LimpiarTabla();
                CargarEncabezado();
                CargarDatos("http://192.168.8.8/ControlProyectoBD/consultar_terminado.php?id_Mueble="+id);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> parametros=new HashMap<String, String>();
                parametros.put("id_terminado",String.valueOf(idTerminado));
                parametros.put("id_Mueble",String.valueOf(id));
                return parametros;
            }
        };
        requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void AlertaModificar() {
        AlertDialog.Builder alert=new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        alert.setTitle("Cotizar")
                .setMessage("Por favor ingresa el id de la pieza que desea modificar")
                .setView(input)
                .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        idTerminado = Integer.parseInt(input.getText().toString());
                        BuscarTerminado("http://192.168.8.8/ControlProyectoBD/buscar_terminado.php?id_Mueble="+id+"&id_terminado="+idTerminado);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create().show();
    }

    private void BuscarTerminado(String URL) {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String nombre=jsonObject.getString("Nombre");
                        for (int j = 0; j < materiales.size(); j++) {
                            spinNombre.setSelection(j);
                            String valor = spinNombre.getSelectedItem().toString();
                            if(valor.equals(nombre)){
                                spinNombre.setSelection(j);
                                break;
                            }
                        }

                        edtCantidad.setText(jsonObject.getString("cantidad"));
                        modifica=true;
                        btnAgregar.setText("Modificar");
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

    private void insertarTerminado(String URL) {
        if(!tvNombre.equals("")){
            if(!spinNombre.getSelectedItem().toString().equals("Material..") &&
                !edtCantidad.getText().toString().isEmpty()){
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), "Operacion Exitosa", Toast.LENGTH_SHORT).show();
                        if(modifica){
                            LimpiarTabla();
                            CargarEncabezado();
                            CargarDatos("http://192.168.8.8/ControlProyectoBD/consultar_terminado.php?id_Mueble="+id);
                            btnAgregar.setText("Agregar");
                            modifica=false;
                        }else{
                            LimpiarTabla();
                            CargarEncabezado();
                            CargarDatos("http://192.168.8.8/ControlProyectoBD/consultar_terminado.php?id_Mueble="+id);
                            Limpiar();
                        }

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
                        parametros.put("id_terminado",String.valueOf(idTerminado));
                        parametros.put("Nombre",spinNombre.getSelectedItem().toString());
                        parametros.put("cantidad",edtCantidad.getText().toString());
                        parametros.put("id_Mueble",String.valueOf(idMueble));
                        return parametros;

                    }
                };
                requestQueue= Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);
            }else{
                Toast.makeText(getActivity(), "Llena todos los campos", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getActivity(), "No asignaste mueble", Toast.LENGTH_SHORT).show();
        }
    }

    private void Limpiar() {
        edtCantidad.setText("");
        spinNombre.setSelection(0);
    }
    private void LimpiarTabla() {
        int count = tabTerminado.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = tabTerminado.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
    }

    private void CargarEncabezado() {
        fila = new TableRow(getActivity());
        fila.setLayoutParams(layoutfila);

        tvId = new TextView(getActivity());
        tvId.setText("Id");
        tvId.setGravity(Gravity.CENTER);
        tvId.setBackgroundColor(Color.BLACK);
        tvId.setTextColor(Color.WHITE);
        tvId.setPadding(10,10,10,10);
        tvId.setLayoutParams(layoutId);
        fila.addView(tvId);

        tvNomMaterial = new TextView(getActivity());
        tvNomMaterial.setText("Material");
        tvNomMaterial.setGravity(Gravity.CENTER);
        tvNomMaterial.setBackgroundColor(Color.BLACK);
        tvNomMaterial.setTextColor(Color.WHITE);
        tvNomMaterial.setPadding(10,10,10,10);
        tvNomMaterial.setLayoutParams(layoutNombre);
        fila.addView(tvNomMaterial);

        tvCantidad = new TextView(getActivity());
        tvCantidad.setText("Cantidad");
        tvCantidad.setGravity(Gravity.CENTER);
        tvCantidad.setBackgroundColor(Color.BLACK);
        tvCantidad.setTextColor(Color.WHITE);
        tvCantidad.setPadding(10,10,10,10);
        tvCantidad.setLayoutParams(layoutCantidad);
        fila.addView(tvCantidad);

        tvPrecio = new TextView(getActivity());
        tvPrecio.setText("Precio");
        tvPrecio.setGravity(Gravity.CENTER);
        tvPrecio.setBackgroundColor(Color.BLACK);
        tvPrecio.setTextColor(Color.WHITE);
        tvPrecio.setPadding(10,10,10,10);
        tvPrecio.setLayoutParams(layoutPrecio);
        fila.addView(tvPrecio);

        tvTotal = new TextView(getActivity());
        tvTotal.setText("Largo");
        tvTotal.setGravity(Gravity.CENTER);
        tvTotal.setBackgroundColor(Color.BLACK);
        tvTotal.setTextColor(Color.WHITE);
        tvTotal.setPadding(10,10,10,10);
        tvTotal.setLayoutParams(layoutTotal);
        fila.addView(tvTotal);

        tabTerminado.addView(fila);
    }

    private void AlertaEntrar() {
        AlertDialog.Builder alert2=new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        alert2.setTitle("Cotizar")
                .setMessage("Por favor ingresa el id del mueble")
                .setView(input)
                .setPositiveButton("Acceder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        id =input.getText().toString();
                        buscarMueble("http://192.168.8.8/ControlProyectoBD/buscar_mueble.php?id_Mueble="+id);
                        CargarDatos("http://192.168.8.8/ControlProyectoBD/consultar_terminado.php?id_Mueble="+id);
                    }
                })
                .create().show();
    }
    private void CargarDatos(String URL) {
        preTotal=0;
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
                        tvId.setText(jsonObject.getString("id_terminado"));
                        tvId.setPadding(10,10,10,10);
                        tvId.setLayoutParams(layoutId);
                        fila.addView(tvId);

                        tvNomMaterial = new TextView(getActivity());
                        tvNomMaterial.setText(jsonObject.getString("Nombre"));
                        tvNomMaterial.setPadding(10,10,10,10);
                        tvNomMaterial.setLayoutParams(layoutNombre);
                        fila.addView(tvNomMaterial);

                        tvCantidad = new TextView(getActivity());
                        tvCantidad.setText(jsonObject.getString("cantidad"));
                        tvCantidad.setPadding(10,10,10,10);
                        tvCantidad.setLayoutParams(layoutCantidad);
                        fila.addView(tvCantidad);

                        tvPrecio = new TextView(getActivity());
                        tvPrecio.setText(jsonObject.getString("precio"));
                        tvPrecio.setPadding(10,10,10,10);
                        tvPrecio.setLayoutParams(layoutPrecio);
                        fila.addView(tvPrecio);

                        tvTotal = new TextView(getActivity());
                        tvTotal.setText(jsonObject.getString("Total"));
                        tvTotal.setPadding(10,10,10,10);
                        tvTotal.setLayoutParams(layoutTotal);
                        fila.addView(tvTotal);
                        double subTotal=Double.parseDouble(jsonObject.getString("Total"));
                        preTotal=preTotal+subTotal;

                        tabTerminado.addView(fila);
                        tvTotalFinal.setText(String.valueOf(preTotal));
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
    private void buscarMueble(String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        idMueble=Integer.parseInt(jsonObject.getString("id_Mueble"));
                        tvNombre.setText(jsonObject.getString("Nombre"));
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Id Incorrecto",Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
        requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }

    private void cargarSpiner() {
        consultarMateriales("http://192.168.8.8/ControlProyectoBD/consultar_materiales.php");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, materiales);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinNombre.setAdapter(adapter);
        adapter.add("Material..");
        adapter.notifyDataSetChanged();

    }

    private void consultarMateriales(String URL) {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        materiales.add(jsonObject.getString("Nombre"));
                        System.out.println(jsonObject.getString("Nombre"));
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Error de conexion",Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
        requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }

    private void cargarControles(View root) {
        tvNombre = root.findViewById(R.id.ter_tv_Nombre);
        spinNombre = root.findViewById(R.id.ter_spin_Nombre);
        edtCantidad = root.findViewById(R.id.ter_edt_Cantidad);
        btnAgregar = root.findViewById(R.id.ter_btn_Agregar);
        tabTerminado = root.findViewById(R.id.ter_tab_Material);
        btnModificar = root.findViewById(R.id.ter_btn_Modificar);
        btnEliminar = root.findViewById(R.id.ter_btn_Eliminar);
        btnGuardar = root.findViewById(R.id.ter_btn_Guardar);
        tvTotalFinal = root.findViewById(R.id.ter_tv_TotalFinal);
    }
}