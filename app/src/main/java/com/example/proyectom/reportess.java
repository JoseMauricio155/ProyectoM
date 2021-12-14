package com.example.proyectom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import modelos.DatosReporte;

public class reportess extends Fragment {
String otro="";
    JSONObject jsonObject;
    RequestQueue requestQueue;
    String id,fecha,provee;
    String odo,odi,och,ose,ocu;
    String sido,sidi,sich,sise,sicu;
    String sedo,sedi,sech,sese,secu;
    String cido,cidi,cich,cise,cicu;
    String cudo,cudi,cuch,cuse,cucu;
    String to8,to7,to6,to5,to4;
    String pre8,pre7,pre6,pre5,pre4,total;
    RecyclerView recyclerdatos;
    adaptador adapter;
ArrayList<DatosReporte> listadatos;
    JsonArrayRequest jsonArrayRequest;
DatosReporte dat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista= inflater.inflate(R.layout.fragment_reportess, container, false);
       cargar_controles(vista);
        listadatos=new ArrayList<DatosReporte>();
        recyclerdatos.setLayoutManager(new LinearLayoutManager(getContext()));

       CargarDatos("http://192.168.8.6/ControlProyectoBD/consulta _compras.php");
       // llenarLista();
         adapter=new adaptador(listadatos);
        recyclerdatos.setAdapter(adapter);
        Toast.makeText(getActivity(), adapter.getItemCount()+"", Toast.LENGTH_SHORT).show();
        return vista;
    }

    public void llenarLista() {
        listadatos.add(new DatosReporte("1","2","3","4","5","6","7","8","9","10","11","12","13"
        ,"14","15","16","17","18","19","20","21","22","23","24","25","26","27","28",
        "29","30","31","32","33","34","35","36","37","38","39"));
        listadatos.add(new DatosReporte("1","2","3","4","5","6","7","8","9","10","11","12","13"
                ,"14","15","16","17","18","19","20","21","22","23","24","25","26","27","28",
                "29","30","31","32","33","34","35","36","37","38",otro));
    }
    private void CargarDatos(String URL) {
         jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                 jsonObject = null;

                //Toast.makeText(getActivity(), "Yeaaaa", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);

                                id=jsonObject.getString("id_Reporte");
                                fecha=jsonObject.getString("fecha");
                                provee=jsonObject.getString("Proveedor");
                                odo=jsonObject.getString("cant_Tab_8Px12");
                                odi=jsonObject.getString("cant_Tab_8Px10");
                                och=jsonObject.getString("cant_Tab_8Px8");
                                ose=jsonObject.getString("cant_Tab_8Px6");
                                ocu=jsonObject.getString("cant_Tab_8Px4");
                                sido=jsonObject.getString("Total_8pies");
                                sidi=jsonObject.getString("cant_Tab_7Px12");
                                sich=jsonObject.getString("cant_Tab_7Px10");
                                sise=jsonObject.getString("cant_Tab_7Px8");
                                sicu=jsonObject.getString("cant_Tab_7Px6");
                                sedo=jsonObject.getString("cant_Tab_7Px4");
                                sedi=jsonObject.getString("Total_7pies");
                                sech=jsonObject.getString("cant_Tab_6Px12");
                                sese=jsonObject.getString("cant_Tab_6Px10");
                                secu=jsonObject.getString("cant_Tab_6Px8");
                                cido=jsonObject.getString("cant_Tab_6Px6");
                                cidi=jsonObject.getString("cant_Tab_6Px4");
                                cich=jsonObject.getString("Total_6pies");
                                cudo=jsonObject.getString("cant_Tab_5Px12");
                                cudi=jsonObject.getString("cant_Tab_5Px10");
                                cuch=jsonObject.getString("cant_Tab_5Px8");
                                cuse=jsonObject.getString("cant_Tab_5Px6");
                                cucu=jsonObject.getString("cant_Tab_5Px4");
                                to8=jsonObject.getString("Total_5pies");
                                to7=jsonObject.getString("cant_Tab_4Px12");
                                to6=jsonObject.getString("cant_Tab_4Px10");
                                to5=jsonObject.getString("cant_Tab_4Px8");
                                to4=jsonObject.getString("cant_Tab_4Px6");
                                pre8=jsonObject.getString("cant_Tab_4Px4");
                                pre7=jsonObject.getString("Total_4pies");
                                pre6=jsonObject.getString("precio_por_pie8");
                                pre5=jsonObject.getString("precio_por_pie7");
                                pre4=jsonObject.getString("precio_por_pie6");
                                cise= jsonObject.getString("precio_por_pie5");
                                cicu=jsonObject.getString("precio_por_pie4");
                                total=jsonObject.getString("Total");

                        listadatos.add( new DatosReporte("5",  fecha,  provee,  odo,  odi,  och,  ose,  ocu,  sido,  sidi,  sich,  sise,  sicu,  sedo,  sedi,  sech,  sese,  secu,  cido,  cidi,  cich,  cise,  cicu,  cudo,  cudi,  cuch,  cuse,  cucu,  to8,  to7,  to6,  to5,  to4,  pre8,  pre7,  pre6,  pre5,  pre4,  total));


                        otro=jsonObject.getString("cant_Tab_8Px8");
                        Toast.makeText(getActivity(), fecha+"ee", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                   // Toast.makeText(getActivity(), listadatos.size()+1+"", Toast.LENGTH_SHORT).show();
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

    public void cargar_controles(View view){
        recyclerdatos=view.findViewById(R.id.Recycler_id);

    }
}