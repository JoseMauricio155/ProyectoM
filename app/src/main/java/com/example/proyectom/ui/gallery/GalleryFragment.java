package com.example.proyectom.ui.gallery;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import modelos.Descripcion;

public class GalleryFragment extends Fragment {
    /////////////////////////////////////////////////////////////////////////////
    public static ArrayList<Descripcion> listaPiezas=new ArrayList<>();
    ////////////////////////////////////////////////////////////////////////////////
    TableRow.LayoutParams layoutfila=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutId=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutdescripcion=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutlargo=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutancho=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutgrueso=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutcantidad=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams layoutpies=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    ///////////////////////////////////////////////////////////////////////////////////////
    EditText edtLargo, edtAncho, edtGrueso, edtCantidad;
    Spinner spinDescripcion;
    Button btnAgregar, btnModificar, btnEliminar, btnGuardar;
    TableLayout tablaPieza;
    TableRow fila;
    TextView tv_Id,tv_Descripcion, tv_largo, tv_ancho, tv_grueso, tv_cantidad, tv_pies, tvSumaPies, tvNombre;
    TextView IDtv;
    String id;
    boolean modifica=false;
    RequestQueue requestQueue;

    double total_pies=0;
    int idPieza;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        cargarControles(root);
        cargaEventos();
        CargarLista();
        alerta2();
        return root;
    }
    private void alerta2() {
        AlertDialog.Builder alert2=new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        alert2.setTitle("Cotizar")
                .setMessage("Por favor ingresa el id del mueble")
                .setView(input)
                .setPositiveButton("Acceder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        id=input.getText().toString();
                        buscarMueble("http://192.168.0.22/ControlProyectoBD/buscar_mueble.php?id_Mueble="+id);
                        CargarDatos("http://192.168.0.22/ControlProyectoBD/consultar_piezas.php?id_Mueble="+id);
                    }
                })
                .create().show();
    }
    private void Modificar() {
        AlertDialog.Builder alert=new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        alert.setTitle("Cotizar")
                .setMessage("Por favor ingresa el id de la pieza que desea modificar")
                .setView(input)
                .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        idPieza = Integer.parseInt(input.getText().toString());
                        BuscarPieza("http://192.168.0.22/ControlProyectoBD/buscar_pieza.php?id_Mueble="+id+"&id_descripcion="+idPieza);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create().show();
    }
    private void AlertaEliminar() {
        AlertDialog.Builder alert=new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        alert.setTitle("Cotizar")
                .setMessage("Por favor ingresa el id de la pieza que desea modificar")
                .setView(input)
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        idPieza= Integer.parseInt(input.getText().toString());
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
                .setMessage("¿Esta seguro que desea eliminar la pieza?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminarPieza("http://192.168.0.22/ControlProyectoBD/eliminar_pieza.php");

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create().show();
    }

    private void buscarMueble(String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        IDtv.setText(jsonObject.getString("id_Mueble"));
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

    private void cargaEventos() {
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modifica){
                    agregarPieza("http://192.168.0.22/ControlProyectoBD/modificar_pieza.php");
                }else{
                    agregarPieza("http://192.168.0.22/ControlProyectoBD/insertar_pieza.php");
                }

            }
        });
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modificar();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertaEliminar();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardarPies("http://192.168.0.22/ControlProyectoBD/guardar_pies.php");
            }
        });
    }

    private void GuardarPies(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "Operacion Exitosa", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
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
                parametros.put("id_Mueble",String.valueOf(id));
                return parametros;
            }
        };
        requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void agregarPieza(String URL) {
        final double largo100, ancho100, grueso100, volumenM3_1, volumenM3_2, pies;
        double constante=0.00236;

        if(!edtLargo.getText().toString().isEmpty() &&
            !edtAncho.getText().toString().isEmpty() &&
            !edtGrueso.getText().toString().isEmpty() &&
            !edtCantidad.getText().toString().isEmpty() &&
            !spinDescripcion.getSelectedItem().toString().equals("Descripcion")){
            final int idMueble = Integer.parseInt(IDtv.getText().toString());
            final String descripcion = spinDescripcion.getSelectedItem().toString();
            final double largo = Double.parseDouble(edtLargo.getText().toString());
            final double ancho = Double.parseDouble(edtAncho.getText().toString());
            final double grueso = Double.parseDouble(edtGrueso.getText().toString());
            final int cantidad = Integer.parseInt(edtCantidad.getText().toString());
            largo100=largo/100;
            ancho100=ancho/100;
            grueso100=grueso/100;
            volumenM3_1=largo100*ancho100*grueso100;
            volumenM3_2=cantidad*volumenM3_1;
            pies=volumenM3_2/constante;
            total_pies=total_pies+pies;
            tvSumaPies.setText(String.valueOf(total_pies));
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getActivity(), "Operacion Exitosa", Toast.LENGTH_SHORT).show();
                    if(modifica){
                        LimpiarTabla();
                        CargarLista();
                        total_pies=0;
                        CargarDatos("http://192.168.0.22/ControlProyectoBD/consultar_piezas.php?id_Mueble="+id);
                        btnAgregar.setText("Agregar");
                        modifica=false;
                        
                    }else{
                        CargarNuevoDato("http://192.168.0.22/ControlProyectoBD/consultar_piezas.php?id_Mueble="+idMueble);
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
                    parametros.put("id_descripcion",String.valueOf(idPieza));
                    parametros.put("Nombre",descripcion);
                    parametros.put("Cantidad",String.valueOf(cantidad));
                    parametros.put("Largo",String.valueOf(largo));
                    parametros.put("Ancho",String.valueOf(ancho));
                    parametros.put("Grosor",String.valueOf(grueso));
                    parametros.put("Pies",String.valueOf(pies));
                    parametros.put("id_Mueble",String.valueOf(idMueble));
                    return parametros;

                }
            };
            requestQueue= Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
            Limpiar();
        }else{
            Toast.makeText(getActivity(), "Llena todos los campos", Toast.LENGTH_SHORT).show();
        }



    }
    ///////////////////////////////////////////////////
    private void eliminarPieza(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "Pieza Eliminada", Toast.LENGTH_SHORT).show();
                LimpiarTabla();
                CargarLista();
                total_pies=0;
                CargarDatos("http://192.168.0.22/ControlProyectoBD/consultar_piezas.php?id_Mueble="+id);
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
                parametros.put("id_descripcion",String.valueOf(idPieza));
                parametros.put("id_Mueble",String.valueOf(id));
                return parametros;
            }
        };
        requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void LimpiarTabla() {
        int count = tablaPieza.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = tablaPieza.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
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

                            tv_Id = new TextView(getActivity());
                            tv_Id.setText(jsonObject.getString("id_descripcion"));
                            tv_Id.setPadding(10,10,10,10);
                            tv_Id.setLayoutParams(layoutId);
                            fila.addView(tv_Id);

                            tv_Descripcion = new TextView(getActivity());
                            tv_Descripcion.setText(jsonObject.getString("Nombre"));
                            tv_Descripcion.setPadding(10,10,10,10);
                            tv_Descripcion.setLayoutParams(layoutdescripcion);
                            fila.addView(tv_Descripcion);

                            tv_cantidad = new TextView(getActivity());
                            tv_cantidad.setText(jsonObject.getString("Cantidad"));
                            tv_cantidad.setPadding(10,10,10,10);
                            tv_cantidad.setLayoutParams(layoutcantidad);
                            fila.addView(tv_cantidad);

                            tv_largo = new TextView(getActivity());
                            tv_largo.setText(jsonObject.getString("Largo"));
                            tv_largo.setPadding(10,10,10,10);
                            tv_largo.setLayoutParams(layoutlargo);
                            fila.addView(tv_largo);

                            tv_ancho = new TextView(getActivity());
                            tv_ancho.setText(jsonObject.getString("Ancho"));
                            tv_ancho.setPadding(10,10,10,10);
                            tv_ancho.setLayoutParams(layoutancho);
                            fila.addView(tv_ancho);

                            tv_grueso = new TextView(getActivity());
                            tv_grueso.setText(jsonObject.getString("Grosor"));
                            tv_grueso.setPadding(10,10,10,10);
                            tv_grueso.setLayoutParams(layoutgrueso);
                            fila.addView(tv_grueso);

                            tv_pies = new TextView(getActivity());
                            tv_pies.setText(jsonObject.getString("Pies"));
                            tv_pies.setPadding(10,10,10,10);
                            tv_pies.setLayoutParams(layoutpies);
                            fila.addView(tv_pies);

                            tablaPieza.addView(fila);
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
                        double pie = Double.valueOf(jsonObject.getString("Pies"));
                        total_pies=total_pies+pie;
                        fila = new TableRow(getActivity());
                        fila.setLayoutParams(layoutfila);

                        tv_Id = new TextView(getActivity());
                        tv_Id.setText(jsonObject.getString("id_descripcion"));
                        tv_Id.setPadding(10,10,10,10);
                        tv_Id.setLayoutParams(layoutId);
                        fila.addView(tv_Id);

                        tv_Descripcion = new TextView(getActivity());
                        tv_Descripcion.setText(jsonObject.getString("Nombre"));
                        tv_Descripcion.setPadding(10,10,10,10);
                        tv_Descripcion.setLayoutParams(layoutdescripcion);
                        fila.addView(tv_Descripcion);

                        tv_cantidad = new TextView(getActivity());
                        tv_cantidad.setText(jsonObject.getString("Cantidad"));
                        tv_cantidad.setPadding(10,10,10,10);
                        tv_cantidad.setLayoutParams(layoutcantidad);
                        fila.addView(tv_cantidad);

                        tv_largo = new TextView(getActivity());
                        tv_largo.setText(jsonObject.getString("Largo"));
                        tv_largo.setPadding(10,10,10,10);
                        tv_largo.setLayoutParams(layoutlargo);
                        fila.addView(tv_largo);

                        tv_ancho = new TextView(getActivity());
                        tv_ancho.setText(jsonObject.getString("Ancho"));
                        tv_ancho.setPadding(10,10,10,10);
                        tv_ancho.setLayoutParams(layoutancho);
                        fila.addView(tv_ancho);

                        tv_grueso = new TextView(getActivity());
                        tv_grueso.setText(jsonObject.getString("Grosor"));
                        tv_grueso.setPadding(10,10,10,10);
                        tv_grueso.setLayoutParams(layoutgrueso);
                        fila.addView(tv_grueso);

                        tv_pies = new TextView(getActivity());
                        tv_pies.setText(jsonObject.getString("Pies"));
                        tv_pies.setPadding(10,10,10,10);
                        tv_pies.setLayoutParams(layoutpies);
                        fila.addView(tv_pies);

                        tablaPieza.addView(fila);
                        tvSumaPies.setText(String.valueOf(total_pies));
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
    private void BuscarPieza(String URL) {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String nombre=jsonObject.getString("Nombre");
                        for (int j = 0; j < 19; j++) {
                            spinDescripcion.setSelection(j);
                            String valor = spinDescripcion.getSelectedItem().toString();
                            if(valor.equals(nombre)){
                                spinDescripcion.setSelection(j);
                                break;
                            }
                        }

                        edtLargo.setText(jsonObject.getString("Largo"));
                        edtAncho.setText(jsonObject.getString("Ancho"));
                        edtGrueso.setText(jsonObject.getString("Grosor"));
                        edtCantidad.setText(jsonObject.getString("Cantidad"));
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

    private void Limpiar() {
        edtLargo.setText("");
        edtAncho.setText("");
        edtGrueso.setText("");
        edtCantidad.setText("");
        spinDescripcion.setSelection(0);
    }

    private void CargarLista() {

        fila = new TableRow(getActivity());
            fila.setLayoutParams(layoutfila);

                tv_Id = new TextView(getActivity());
                tv_Id.setText("Id");
                tv_Id.setGravity(Gravity.CENTER);
                tv_Id.setBackgroundColor(Color.BLACK);
                tv_Id.setTextColor(Color.WHITE);
                tv_Id.setPadding(10,10,10,10);
                tv_Id.setLayoutParams(layoutId);
                fila.addView(tv_Id);

                tv_Descripcion = new TextView(getActivity());
                tv_Descripcion.setText("Descripcion");
                tv_Descripcion.setGravity(Gravity.CENTER);
                tv_Descripcion.setBackgroundColor(Color.BLACK);
                tv_Descripcion.setTextColor(Color.WHITE);
                tv_Descripcion.setPadding(10,10,10,10);
                tv_Descripcion.setLayoutParams(layoutdescripcion);
                fila.addView(tv_Descripcion);

                tv_largo = new TextView(getActivity());
                tv_largo.setText("Cantidad");
                tv_largo.setGravity(Gravity.CENTER);
                tv_largo.setBackgroundColor(Color.BLACK);
                tv_largo.setTextColor(Color.WHITE);
                tv_largo.setPadding(10,10,10,10);
                tv_largo.setLayoutParams(layoutlargo);
                fila.addView(tv_largo);

                tv_ancho = new TextView(getActivity());
                tv_ancho.setText("Largo");
                tv_ancho.setGravity(Gravity.CENTER);
                tv_ancho.setBackgroundColor(Color.BLACK);
                tv_ancho.setTextColor(Color.WHITE);
                tv_ancho.setPadding(10,10,10,10);
                tv_ancho.setLayoutParams(layoutancho);
                fila.addView(tv_ancho);

                tv_grueso = new TextView(getActivity());
                tv_grueso.setText("Ancho");
                tv_grueso.setGravity(Gravity.CENTER);
                tv_grueso.setBackgroundColor(Color.BLACK);
                tv_grueso.setTextColor(Color.WHITE);
                tv_grueso.setPadding(10,10,10,10);
                tv_grueso.setLayoutParams(layoutgrueso);
                fila.addView(tv_grueso);

                tv_cantidad = new TextView(getActivity());
                tv_cantidad.setText("Grueso");
                tv_cantidad.setGravity(Gravity.CENTER);
                tv_cantidad.setBackgroundColor(Color.BLACK);
                tv_cantidad.setTextColor(Color.WHITE);
                tv_cantidad.setPadding(10,10,10,10);
                tv_cantidad.setLayoutParams(layoutcantidad);
                fila.addView(tv_cantidad);

                tv_pies = new TextView(getActivity());
                tv_pies.setText("Pies");
                tv_pies.setGravity(Gravity.CENTER);
                tv_pies.setBackgroundColor(Color.BLACK);
                tv_pies.setTextColor(Color.WHITE);
                tv_pies.setPadding(10,10,10,10);
                tv_pies.setLayoutParams(layoutpies);
                fila.addView(tv_pies);

                tablaPieza.addView(fila);


            }



    private void cargarControles(View root) {
        IDtv = root.findViewById(R.id.co_tv_id);
        tvNombre = root.findViewById(R.id.co_tv_Nombre);
        spinDescripcion = root.findViewById(R.id.co_spin_descripcion);
        edtLargo = root.findViewById(R.id.co_edt_tex_largo);
        edtAncho = root.findViewById(R.id.co_edt_tex_ancho);
        edtGrueso = root.findViewById(R.id.co_edt_tex_Grueso);
        edtCantidad = root.findViewById(R.id.co_edt_tex_cantidad);
        btnAgregar = root.findViewById(R.id.co_btn_Agregar);
        tablaPieza = root.findViewById(R.id.tab_pies);
        tvSumaPies = root.findViewById(R.id.co_tv_total_pies);
        btnModificar = root.findViewById(R.id.co_btn_Modificar);
        btnEliminar = root.findViewById(R.id.co_btn_Eliminar);
        btnGuardar = root.findViewById(R.id.co_btn_Guardar);


    }

}