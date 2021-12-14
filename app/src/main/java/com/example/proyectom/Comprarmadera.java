package com.example.proyectom;

import android.graphics.Color;
import android.os.Bundle;


import androidx.annotation.NonNull;
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

import modelos.Comprarmaderaa;
import modelos.Descripcion;
import modelos.Material;
public class Comprarmadera  extends Fragment{

Spinner Spinpgds,SpinPies;
String []cant;
String []pgds;
String []Pies;
EditText txtprecio,txtproveedor,txtCantidad;
TextView tvTotal;
Button btn_comprar,btn_agregar;
    TableLayout TablaCompra;
    TableRow fila;
    TextView TVLargo, TVancho, TVCantidad, TVTotal,TVProveedor,TVPrecio;
    RequestQueue requestQueue;
    double ochox12=0,ochox10=0,ochox8=0,ochox6=0,ochox4=0,ochototal=0,precioocho=0;
    double seisx12=0,seisx10=0,seisx8=0,seisx6=0,seisx4=0,seistotal=0,precioseis=0;
    double sietex12=0,sietex10=0,sietex8=0,sietex6=0,sietex4=0,sietetotal=0,preciosiete=0;
    double cincox12=0,cincox10=0,cincox8=0,cincox6=0,cincox4=0,cincototal=0,preciocinco=0;
    double cuatrox12=0,cuatrox10=0,cuatrox8=0,cuatrox6=0,cuatrox4=0,cuatrototal=0,preciocuatro=0;
    double total_suma_madera=0;
    String provee="";
Button cancelar;
   public static ArrayList<Comprarmaderaa> comprar=new ArrayList<>();
    TableRow.LayoutParams layoutfila=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
    TableRow.LayoutParams layoutLargo=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
    TableRow.LayoutParams layoutAncho=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
    TableRow.LayoutParams layoutCantidad=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
    TableRow.LayoutParams layoutTotal=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
    TableRow.LayoutParams layoutProveedor=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
    TableRow.LayoutParams layoutPrecio=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);


        @Override
    public View onCreateView( LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_slideshow, container, false);
        CargarControles(root);
        cargarEncabezado();
        cargar_eventos();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargar_tabla();
    }

    private void cargar_eventos(){
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Limpiar();
                }
            });
        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar();
cargar_tabla();
total();
            }
        });
        btn_comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
txtproveedor.setEnabled(true);
asignar_valores();
Ejecutar_servicio("http://192.168.8.8/controlProyectoBD/insertarCompra.php");
            }
        });
    }
    private void agregar(){
        if(!txtprecio.getText().toString().isEmpty()&&!txtproveedor.getText().toString().isEmpty()&&
        !txtCantidad.getText().toString().isEmpty()){
txtproveedor.setEnabled(false);
            //Tabla de 4 pies de largo
            if(Double.parseDouble(SpinPies.getSelectedItem().toString())==4) {
                boolean tempo = false;
                int pos = 0;
                switch (Integer.parseInt(Spinpgds.getSelectedItem().toString())) {
                    case 4:
                        for (int i = 0; i < comprar.size(); i++) {
                            if ((Double.parseDouble(comprar.get(i).getAncho() + "") == 4) && (Double.parseDouble(comprar.get(i).getLargo()+"")==4)) {
                                tempo = true;
                                pos = i;
                                break;
                            }

                        }
                        if (!tempo) {

                            Comprarmaderaa compr = new Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            , Double.parseDouble(txtprecio.getText().toString()),
                                            ((1 * Double.parseDouble(txtprecio.getText().toString())) * Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                            cargar_tabla();
                        } else {
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString()) + comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal() + (Double.parseDouble(txtCantidad.getText().toString()) * Double.parseDouble(txtprecio.getText().toString()) * 1));
                            cargar_tabla();
                            tempo=false;
                        }
                        cargar_tabla();
                        break;
                    case 6:
                        for (int i = 0; i < comprar.size(); i++) {
                            if (Double.parseDouble(comprar.get(i).getAncho() + "") == 6 && (Double.parseDouble(comprar.get(i).getLargo()+"")==4)) {
                                tempo = true;
                                pos = i;
                                break;
                            }

                        }
                        if (!tempo) {
                            Comprarmaderaa compr = new Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            , Double.parseDouble(txtprecio.getText().toString()),
                                            ((1.5 * Double.parseDouble(txtprecio.getText().toString())) * Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                            cargar_tabla();
                        } else {

                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString()) + comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal() + (Double.parseDouble(txtCantidad.getText().toString()) * Double.parseDouble(txtprecio.getText().toString()) * 1.5));
                            cargar_tabla();
                            tempo=false;
                        }

                        cargar_tabla();
                        break;
                    case 8:
                        for (int i = 0; i < comprar.size(); i++) {
                            if (Double.parseDouble(comprar.get(i).getAncho() + "") == 8 && (Double.parseDouble(comprar.get(i).getLargo()+"")==4)) {
                                tempo = true;
                                pos = i;
                                break;
                            }

                        }
                        if (!tempo) {
                            Comprarmaderaa compr = new Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            , Double.parseDouble(txtprecio.getText().toString()),
                                            ((2 * Double.parseDouble(txtprecio.getText().toString())) * Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                            cargar_tabla();
                        } else {

                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString()) + comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal() + (Double.parseDouble(txtCantidad.getText().toString()) * Double.parseDouble(txtprecio.getText().toString()) * 2));
                            cargar_tabla();
                            tempo=false;
                        }


                        cargar_tabla();
                        break;
                    case 10:
                        for (int i = 0; i < comprar.size(); i++) {
                            if (Double.parseDouble(comprar.get(i).getAncho() + "") == 10 && (Double.parseDouble(comprar.get(i).getLargo()+"")==4)) {
                                tempo = true;
                                pos = i;
                                break;
                            }

                        }
                        if (!tempo) {
                            Comprarmaderaa compr = new Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            , Double.parseDouble(txtprecio.getText().toString()),
                                            ((2.5 * Double.parseDouble(txtprecio.getText().toString())) * Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                            cargar_tabla();
                        } else {

                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString()) + comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal() + (Double.parseDouble(txtCantidad.getText().toString()) * Double.parseDouble(txtprecio.getText().toString()) * 2.5));
                            cargar_tabla();
                            tempo=false;
                        }
                        cargar_tabla();
                        break;
                    case 12:
                        for (int i = 0; i < comprar.size(); i++) {
                            if (Double.parseDouble(comprar.get(i).getAncho() + "") == 12 && (Double.parseDouble(comprar.get(i).getLargo()+"")==4)) {
                                tempo = true;
                                pos = i;
                                break;
                            }

                        }
                        if (!tempo) {
                            Comprarmaderaa compr = new Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            , Double.parseDouble(txtprecio.getText().toString()),
                                            ((3 * Double.parseDouble(txtprecio.getText().toString())) * Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                            cargar_tabla();


                        } else {
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString()) + comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal() + (Double.parseDouble(txtCantidad.getText().toString()) * Double.parseDouble(txtprecio.getText().toString()) * 3));
                            tempo=false;
                        }
                        cargar_tabla();
                        break;
                }
            }
                //Tabla de 5 pies de largo
                if(Double.parseDouble(SpinPies.getSelectedItem().toString())==5){
                    boolean tempo=false;
                    int pos=0;
                    switch (Integer.parseInt(Spinpgds.getSelectedItem().toString())){
                        case 4:
                            for (int i = 0; i <comprar.size() ; i++) {
                                if(Double.parseDouble(comprar.get(i).getAncho()+"")==4&& (Double.parseDouble(comprar.get(i).getLargo()+"")==5)){
                                    tempo=true;
                                    pos=i;
                                    break;
                                }

                            }
                            if(tempo){
                                comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                                comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*1.25));
                                cargar_tabla();
                                tempo=false;
                            }else{
                                Comprarmaderaa compr=new  Comprarmaderaa
                                        (Double.parseDouble(txtCantidad.getText().toString()),
                                                Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                                Double.parseDouble(SpinPies.getSelectedItem().toString())
                                                ,Double.parseDouble(txtprecio.getText().toString()),
                                                ((1.25*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                                txtproveedor.getText().toString());
                                comprar.add(compr);
                                cargar_tabla();
                            }
                            cargar_tabla();
                            break;
                        case 6:
                            for (int i = 0; i <comprar.size() ; i++) {
                                if(Double.parseDouble(comprar.get(i).getAncho()+"")==6&& (Double.parseDouble(comprar.get(i).getLargo()+"")==5)){
                                    tempo=true;
                                    pos=i;
                                    break;
                                }

                            }
                            if(tempo){
                                comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                                comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*1.875));
                                tempo=false;
                            }else{
                                Comprarmaderaa compr=new  Comprarmaderaa
                                        (Double.parseDouble(txtCantidad.getText().toString()),
                                                Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                                Double.parseDouble(SpinPies.getSelectedItem().toString())
                                                ,Double.parseDouble(txtprecio.getText().toString()),
                                                ((1.875*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                                txtproveedor.getText().toString());
                                comprar.add(compr);
                                cargar_tabla();
                            }

                            cargar_tabla();
                            break;
                        case 8:
                            for (int i = 0; i <comprar.size() ; i++) {
                                if(Double.parseDouble(comprar.get(i).getAncho()+"")==8&& (Double.parseDouble(comprar.get(i).getLargo()+"")==5)){
                                    tempo=true;
                                    pos=i;
                                    break;
                                }

                            }
                            if(tempo){
                                comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                                comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*2.5));
                                cargar_tabla();
                                tempo=false;
                            }else{
                                Comprarmaderaa compr=new  Comprarmaderaa
                                        (Double.parseDouble(txtCantidad.getText().toString()),
                                                Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                                Double.parseDouble(SpinPies.getSelectedItem().toString())
                                                ,Double.parseDouble(txtprecio.getText().toString()),
                                                ((2.5*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                                txtproveedor.getText().toString());
                                comprar.add(compr);
                                cargar_tabla();
                            }


                            cargar_tabla();
                            break;
                        case 10:
                            for (int i = 0; i <comprar.size() ; i++) {
                                if(Double.parseDouble(comprar.get(i).getAncho()+"")==10&& (Double.parseDouble(comprar.get(i).getLargo()+"")==5)){
                                    tempo=true;
                                    pos=i;
                                    break;
                                }

                            }
                            if(tempo){
                                comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                                comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*3.125));
                                cargar_tabla();
                                tempo=false;
                            }else{
                                Comprarmaderaa compr=new  Comprarmaderaa
                                        (Double.parseDouble(txtCantidad.getText().toString()),
                                                Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                                Double.parseDouble(SpinPies.getSelectedItem().toString())
                                                ,Double.parseDouble(txtprecio.getText().toString()),
                                                ((3.125*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                                txtproveedor.getText().toString());
                                comprar.add(compr);
                                cargar_tabla();
                            }
                            cargar_tabla();
                            break;
                        case 12:
                            for (int i = 0; i <comprar.size() ; i++) {
                                if(Double.parseDouble(comprar.get(i).getAncho()+"")==12&& (Double.parseDouble(comprar.get(i).getLargo()+"")==5)){
                                    tempo=true;
                                    pos=i;
                                    break;
                                }

                            }
                            if(tempo){
                                comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                                comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*3.7));
                                cargar_tabla();
                                tempo=false;
                            }else{
                                Comprarmaderaa compr=new  Comprarmaderaa
                                        (Double.parseDouble(txtCantidad.getText().toString()),
                                                Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                                Double.parseDouble(SpinPies.getSelectedItem().toString())
                                                ,Double.parseDouble(txtprecio.getText().toString()),
                                                ((3.7*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                                txtproveedor.getText().toString());
                                comprar.add(compr);
                                cargar_tabla();
                            }
                            cargar_tabla();
                            break;
                    }
                }
            //Tabla de 6 pies de largo
            if(Double.parseDouble(SpinPies.getSelectedItem().toString())==6){
                boolean tempo=false;
                int pos=0;
                switch (Integer.parseInt(Spinpgds.getSelectedItem().toString())){
                    case 4:
                        for (int i = 0; i <comprar.size() ; i++) {
                            if(Double.parseDouble(comprar.get(i).getAncho()+"")==4&&(comprar.get(i).getLargo()==6)){
                                tempo=true;
                                pos=i;
                                break;
                            }

                        }
                        if(tempo){
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*1.5));
                            tempo=false;
                        }else{
                            Comprarmaderaa compr=new  Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            ,Double.parseDouble(txtprecio.getText().toString()),
                                            ((1.5*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                        }
                        cargar_tabla();
                        break;
                    case 6:
                        for (int i = 0; i <comprar.size() ; i++) {
                            if(Double.parseDouble(comprar.get(i).getAncho()+"")==6&&(comprar.get(i).getLargo()==6)){
                                tempo=true;
                                pos=i;
                                break;
                            }

                        }
                        if(tempo){
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*2.25));
                            tempo=false;
                        }else{
                            Comprarmaderaa compr=new  Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            ,Double.parseDouble(txtprecio.getText().toString()),
                                            ((2.25*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                        }

                        cargar_tabla();
                        break;
                    case 8:
                        for (int i = 0; i <comprar.size() ; i++) {
                            if(Double.parseDouble(comprar.get(i).getAncho()+"")==8&&(comprar.get(i).getLargo()==6)){
                                tempo=true;
                                pos=i;
                                break;
                            }

                        }
                        if(tempo){
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*3));
                            tempo=false;
                        }else{
                            Comprarmaderaa compr=new  Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            ,Double.parseDouble(txtprecio.getText().toString()),
                                            ((3*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                        }


                        cargar_tabla();
                        break;
                    case 10:
                        for (int i = 0; i <comprar.size() ; i++) {
                            if(Double.parseDouble(comprar.get(i).getAncho()+"")==10&&(comprar.get(i).getLargo()==6)){
                                tempo=true;
                                pos=i;
                                break;
                            }

                        }
                        if(tempo){
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*3.75));
                            tempo=false;
                        }else{
                            Comprarmaderaa compr=new  Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            ,Double.parseDouble(txtprecio.getText().toString()),
                                            ((3.75*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                        }
                        cargar_tabla();
                        break;
                    case 12:
                        for (int i = 0; i <comprar.size() ; i++) {
                            if(Double.parseDouble(comprar.get(i).getAncho()+"")==12&&(comprar.get(i).getLargo()==6)){
                                tempo=true;
                                pos=i;
                                break;
                            }

                        }
                        if(tempo){
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*4.5));
                            tempo=false;
                        }else{
                            Comprarmaderaa compr=new  Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            ,Double.parseDouble(txtprecio.getText().toString()),
                                            ((4.5*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                        }
                        cargar_tabla();
                        break;
                }}

            //Tabla de 7 pies de largo
            if(Double.parseDouble(SpinPies.getSelectedItem().toString())==7){
                boolean tempo=false;
                int pos=0;
                switch (Integer.parseInt(Spinpgds.getSelectedItem().toString())){
                    case 4:
                        for (int i = 0; i <comprar.size() ; i++) {
                            if(Double.parseDouble(comprar.get(i).getAncho()+"")==4&&(comprar.get(i).getLargo()==7)){
                                tempo=true;
                                pos=i;
                                break;
                            }

                        }
                        if(tempo){
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*1.75));
                            tempo=false;
                        }else{
                            Comprarmaderaa compr=new  Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            ,Double.parseDouble(txtprecio.getText().toString()),
                                            ((1.75*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                        }
                        cargar_tabla();
                        break;
                    case 6:
                        for (int i = 0; i <comprar.size() ; i++) {
                            if(Double.parseDouble(comprar.get(i).getAncho()+"")==6&&(comprar.get(i).getLargo()==7)){
                                tempo=true;
                                pos=i;
                                break;
                            }

                        }
                        if(tempo){
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*2.625));
                            tempo=false;
                        }else{
                            Comprarmaderaa compr=new  Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            ,Double.parseDouble(txtprecio.getText().toString()),
                                            ((2.625*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                        }

                        cargar_tabla();
                        break;
                    case 8:
                        for (int i = 0; i <comprar.size() ; i++) {
                            if(Double.parseDouble(comprar.get(i).getAncho()+"")==8&&(comprar.get(i).getLargo()==7)){
                                tempo=true;
                                pos=i;
                                break;
                            }

                        }
                        if(tempo){
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*3.5));
                            tempo=false;
                        }else{
                            Comprarmaderaa compr=new  Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            ,Double.parseDouble(txtprecio.getText().toString()),
                                            ((3.5*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                        }


                        cargar_tabla();
                        break;
                    case 10:
                        for (int i = 0; i <comprar.size() ; i++) {
                            if(Double.parseDouble(comprar.get(i).getAncho()+"")==10&&(comprar.get(i).getLargo()==7)){
                                tempo=true;
                                pos=i;
                                break;
                            }

                        }
                        if(tempo){
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*4.375));
                            tempo=false;
                        }else{
                            Comprarmaderaa compr=new  Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            ,Double.parseDouble(txtprecio.getText().toString()),
                                            ((4.375*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                        }
                        cargar_tabla();
                        break;
                    case 12:
                        for (int i = 0; i <comprar.size() ; i++) {
                            if(Double.parseDouble(comprar.get(i).getAncho()+"")==12&&(comprar.get(i).getLargo()==7)){
                                tempo=true;
                                pos=i;
                                break;
                            }

                        }
                        if(tempo){
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*5.25));
                            tempo=false;
                        }else{
                            Comprarmaderaa compr=new  Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            ,Double.parseDouble(txtprecio.getText().toString()),
                                            ((5.25*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                        }
                        cargar_tabla();
                        break;
                }
            }
            //Tabla de 8 pies de largo
            if(Double.parseDouble(SpinPies.getSelectedItem().toString())==8){
                boolean tempo=false;
                int pos=0;
                switch (Integer.parseInt(Spinpgds.getSelectedItem().toString())){
                    case 4:
                        for (int i = 0; i <comprar.size() ; i++) {
                            if(Double.parseDouble(comprar.get(i).getAncho()+"")==4&&(comprar.get(i).getLargo()==8)){
                                tempo=true;
                                pos=i;
                                break;
                            }

                        }
                        if(tempo){
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*2));
                            tempo=false;
                        }else{
                            Comprarmaderaa compr=new  Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            ,Double.parseDouble(txtprecio.getText().toString()),
                                            ((2*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                        }
                        cargar_tabla();
                        break;
                    case 6:
                        for (int i = 0; i <comprar.size() ; i++) {
                            if(Double.parseDouble(comprar.get(i).getAncho()+"")==6&&(comprar.get(i).getLargo()==8)){
                                tempo=true;
                                pos=i;
                                break;
                            }

                        }
                        if(tempo){
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*3));
                            tempo=false;
                        }else{
                            Comprarmaderaa compr=new  Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            ,Double.parseDouble(txtprecio.getText().toString()),
                                            ((3*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                        }

                        cargar_tabla();
                        break;
                    case 8:
                        for (int i = 0; i <comprar.size() ; i++) {
                            if(Double.parseDouble(comprar.get(i).getAncho()+"")==8&&(comprar.get(i).getLargo()==8)){
                                tempo=true;
                                pos=i;
                                break;
                            }

                        }
                        if(tempo){
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*4));
                            tempo=false;
                        }else{
                            Comprarmaderaa compr=new  Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            ,Double.parseDouble(txtprecio.getText().toString()),
                                            ((4*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                        }


                        cargar_tabla();
                        break;
                    case 10:
                        for (int i = 0; i <comprar.size() ; i++) {
                            if(Double.parseDouble(comprar.get(i).getAncho()+"")==10&&(comprar.get(i).getLargo()==8)){
                                tempo=true;
                                pos=i;
                                break;
                            }

                        }
                        if(tempo){
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*5));
                            tempo=false;
                        }else{
                            Comprarmaderaa compr=new  Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            ,Double.parseDouble(txtprecio.getText().toString()),
                                            ((5*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                        }
                        cargar_tabla();
                        break;
                    case 12:
                        for (int i = 0; i <comprar.size() ; i++) {
                            if(Double.parseDouble(comprar.get(i).getAncho()+"")==12&&(comprar.get(i).getLargo()==8)){
                                tempo=true;
                                pos=i;
                                break;
                            }

                        }
                        if(tempo){
                            comprar.get(pos).setCantidad(Double.parseDouble(txtCantidad.getText().toString())+comprar.get(pos).getCantidad());
                            comprar.get(pos).setTotal(comprar.get(pos).getTotal()+(Double.parseDouble(txtCantidad.getText().toString())*Double.parseDouble(txtprecio.getText().toString())*6));
                            tempo=false;
                        }else{
                            Comprarmaderaa compr=new  Comprarmaderaa
                                    (Double.parseDouble(txtCantidad.getText().toString()),
                                            Double.parseDouble(Spinpgds.getSelectedItem().toString()),
                                            Double.parseDouble(SpinPies.getSelectedItem().toString())
                                            ,Double.parseDouble(txtprecio.getText().toString()),
                                            ((6*Double.parseDouble(txtprecio.getText().toString()))*Double.parseDouble(txtCantidad.getText().toString())),
                                            txtproveedor.getText().toString());
                            comprar.add(compr);
                        }
                        cargar_tabla();
                        break;
                }
            }




        }else{
           Toast.makeText(getActivity(), "Completa los campos", Toast.LENGTH_SHORT).show();
        }
    }
private void total(){
            double total=0;
    for (int i = 0; i <comprar.size() ; i++) {
        total=total+comprar.get(i).getTotal();
    }
    tvTotal.setText(total+"");
}
    private void cargarEncabezado() {
        fila = new TableRow(getActivity());
        fila.setLayoutParams(layoutfila);

        TVLargo = new TextView(getActivity());
        TVLargo.setText("Largo en pies");
        TVLargo.setGravity(Gravity.CENTER);
        TVLargo.setBackgroundColor(Color.BLACK);
        TVLargo.setTextColor(Color.WHITE);
        TVLargo.setPadding(10,10,10,10);
        TVLargo.setLayoutParams(layoutLargo);
        fila.addView(TVLargo);

        TVancho = new TextView(getActivity());
        TVancho.setText("Ancho");
        TVancho.setGravity(Gravity.CENTER);
        TVancho.setBackgroundColor(Color.BLACK);
        TVancho.setTextColor(Color.WHITE);
        TVancho.setPadding(10,10,10,10);
        TVancho.setLayoutParams(layoutAncho);
        fila.addView(TVancho);

        TVPrecio = new TextView(getActivity());
        TVPrecio.setText("Precio");
        TVPrecio.setGravity(Gravity.CENTER);
        TVPrecio.setBackgroundColor(Color.BLACK);
        TVPrecio.setTextColor(Color.WHITE);
        TVPrecio.setPadding(10,10,10,10);
        TVPrecio.setLayoutParams(layoutPrecio);
        fila.addView(TVPrecio);

        TVCantidad = new TextView(getActivity());
        TVCantidad.setText("Cantidad");
        TVCantidad.setGravity(Gravity.CENTER);
        TVCantidad.setBackgroundColor(Color.BLACK);
        TVCantidad.setTextColor(Color.WHITE);
        TVCantidad.setPadding(10,10,10,10);
        TVCantidad.setLayoutParams(layoutCantidad);
        fila.addView(TVCantidad);



        TVProveedor = new TextView(getActivity());
        TVProveedor.setText("Proveedor");
        TVProveedor.setGravity(Gravity.CENTER);
        TVProveedor.setBackgroundColor(Color.BLACK);
        TVProveedor.setTextColor(Color.WHITE);
        TVProveedor.setPadding(10,10,10,10);
        TVProveedor.setLayoutParams(layoutProveedor);
        fila.addView(TVProveedor);

        TVTotal = new TextView(getActivity());
        TVTotal.setText("Total");
        TVTotal.setGravity(Gravity.CENTER);
        TVTotal.setBackgroundColor(Color.BLACK);
        TVTotal.setTextColor(Color.WHITE);
        TVTotal.setPadding(10,10,10,10);
        TVTotal.setLayoutParams(layoutTotal);
        fila.addView(TVTotal);
        TablaCompra.addView(fila);

    }
    private void cargar_tabla(){
        //Limpiar tabla

        TablaCompra.removeViews(1, Math.max(0, TablaCompra.getChildCount() - 1));

        for (int i = 0; i <comprar.size(); i++) {
            fila = new TableRow(getActivity());
            fila.setLayoutParams(layoutfila);

            TVLargo = new TextView(getActivity());
            TVLargo.setText(comprar.get(i).getLargo()+"");
            TVLargo.setPadding(10,10,10,10);
            TVLargo.setLayoutParams(layoutLargo);
            fila.addView(TVLargo);

            TVancho = new TextView(getActivity());
            TVancho.setText(comprar.get(i).getAncho()+"");
            TVancho.setPadding(10,10,10,10);
            TVancho.setLayoutParams(layoutAncho);
            fila.addView(TVancho);

            TVPrecio = new TextView(getActivity());
            TVPrecio.setText(comprar.get(i).getPrecio()+"");
            TVPrecio.setPadding(10,10,10,10);
            TVPrecio.setLayoutParams(layoutPrecio);
            fila.addView(TVPrecio);

            TVCantidad = new TextView(getActivity());
            TVCantidad.setText(comprar.get(i).getCantidad()+"");
            TVCantidad.setPadding(10,10,10,10);
            TVCantidad.setLayoutParams(layoutCantidad);
            fila.addView(TVCantidad);

            TVProveedor = new TextView(getActivity());
            TVProveedor.setText(comprar.get(i).getProvedor()+"");
            TVProveedor.setPadding(10,10,10,10);
            TVProveedor.setLayoutParams(layoutProveedor);
            fila.addView(TVProveedor);

            TVTotal = new TextView(getActivity());
            TVTotal.setText(comprar.get(i).getTotal()+"");
            TVTotal.setPadding(10,10,10,10);
            TVTotal.setLayoutParams(layoutTotal);
            fila.addView(TVTotal);
            TablaCompra.addView(fila);
        }
        System.out.println(comprar.size());
    }

    private void CargarControles(View root) {
        //Cargar spinners
        txtCantidad=root.findViewById(R.id.comprar_mad_cant);
        Spinpgds=root.findViewById(R.id.Comprar_mad_Pgds);
        SpinPies=root.findViewById(R.id.Comprar_mad_Pies);
        txtprecio=root.findViewById(R.id.Comprar_madera_Precio);
        txtproveedor=root.findViewById(R.id.Comprar_madera_Proveedor);
        btn_agregar=root.findViewById(R.id.Comprar_mad_agrega);
        btn_comprar=root.findViewById(R.id.Comprar_madera_compra);
        tvTotal=root.findViewById(R.id.Comprar_mad_total);
       TablaCompra=root.findViewById(R.id.Comprar_mad_tabla);
       cancelar=root.findViewById(R.id.cancelar_compra);
    }
private void asignar_valores(){
            //Tablas de cuatro pies
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==4&&comprar.get(i).getAncho()==4){
            cuatrox4=comprar.get(i).getCantidad();
            preciocuatro=comprar.get(i).getPrecio();

            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==4&&comprar.get(i).getAncho()==6){
            cuatrox6=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==4&&comprar.get(i).getAncho()==8){
            cuatrox8=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==4&&comprar.get(i).getAncho()==10){
            cuatrox10=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==4&&comprar.get(i).getAncho()==12){
            cuatrox12=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==4){
            cuatrototal=cuatrototal+comprar.get(i).getTotal();
            break;
        }
    }
    //cinco pies
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==5&&comprar.get(i).getAncho()==4){
            cincox4=comprar.get(i).getCantidad();
            preciocinco=comprar.get(i).getPrecio();

            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==5&&comprar.get(i).getAncho()==6){
            cincox6=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==5&&comprar.get(i).getAncho()==8){
            cincox8=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==5&&comprar.get(i).getAncho()==10){
            cincox10=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==5&&comprar.get(i).getAncho()==12){
            cincox12=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==5){
            cincototal=cincototal+comprar.get(i).getTotal();
            break;
        }
    }
    //Pies seis
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==6&&comprar.get(i).getAncho()==4){
            seisx4=comprar.get(i).getCantidad();
            precioseis=comprar.get(i).getPrecio();

            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==6&&comprar.get(i).getAncho()==6){
            seisx6=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==6&&comprar.get(i).getAncho()==8){
            seisx8=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==6&&comprar.get(i).getAncho()==10){
            seisx10=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==6&&comprar.get(i).getAncho()==12){
            seisx12=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==6){
            seistotal=seistotal+comprar.get(i).getTotal();
            break;
        }
    }
    //Pies siete
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==7&&comprar.get(i).getAncho()==4){
            sietex4=comprar.get(i).getCantidad();
            preciosiete=comprar.get(i).getPrecio();

            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==7&&comprar.get(i).getAncho()==6){
            sietex6=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==7&&comprar.get(i).getAncho()==8){
            sietex8=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==7&&comprar.get(i).getAncho()==10){
            sietex10=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==7&&comprar.get(i).getAncho()==12){
            sietex12=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==7){
            sietetotal=sietetotal+comprar.get(i).getTotal();
            break;
        }
    }
    //Pies ochp
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==8&&comprar.get(i).getAncho()==4){
            ochox4=comprar.get(i).getCantidad();
            precioocho=comprar.get(i).getPrecio();

            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==8&&comprar.get(i).getAncho()==6){
            ochox6=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==8&&comprar.get(i).getAncho()==8){
            ochox8=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==8&&comprar.get(i).getAncho()==10){
            ochox10=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==8&&comprar.get(i).getAncho()==12){
            ochox12=comprar.get(i).getCantidad();
            break;
        }
    }
    for (int i = 0; i <comprar.size() ; i++) {
        if(comprar.get(i).getLargo()==8){
            ochototal=ochototal+comprar.get(i).getTotal();
            provee=comprar.get(i).getProvedor();
            break;
        }
    }


}
    private void Ejecutar_servicio(String URL) {
        if(comprar.size()>0){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getActivity(), "Operacion Exitosa", Toast.LENGTH_SHORT).show();
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
                    parametros.put("Proveedor",txtproveedor.getText().toString());

                    parametros.put("cant_Tab_8Px12",ochox12+"");
                    parametros.put("cant_Tab_8Px10",ochox10+"");
                    parametros.put("cant_Tab_8Px8",ochox8+"");
                    parametros.put("cant_Tab_8Px6",ochox6+"");
                    parametros.put("cant_Tab_8Px4",ochox4+"");
                    parametros.put("Total_8pies",ochototal+"");

                    parametros.put("cant_Tab_7Px12",sietex12+"");
                    parametros.put("cant_Tab_7Px10",sietex10+"");
                    parametros.put("cant_Tab_7Px8",sietex8+"");
                    parametros.put("cant_Tab_7Px6",sietex6+"");
                    parametros.put("cant_Tab_7Px4",sietex4+"");
                    parametros.put("Total_7pies",sietetotal+"");

                    parametros.put("cant_Tab_6Px12",seisx12+"");
                    parametros.put("cant_Tab_6Px10",seisx10+"");
                    parametros.put("cant_Tab_6Px8",seisx8+"");
                    parametros.put("cant_Tab_6Px6",seisx6+"");
                    parametros.put("cant_Tab_6Px4",seisx4+"");
                    parametros.put("Total_6pies",seistotal+"");

                    parametros.put("cant_Tab_5Px12",cincox12+"");
                    parametros.put("cant_Tab_5Px10",cincox10+"");
                    parametros.put("cant_Tab_5Px8",cincox8+"");
                    parametros.put("cant_Tab_5Px6",cincox6+"");
                    parametros.put("cant_Tab_5Px4",cincox4+"");
                    parametros.put("Total_5pies",cincototal+"");

                    parametros.put("cant_Tab_4Px12",cuatrox12+"");
                    parametros.put("cant_Tab_4Px10",cuatrox10+"");
                    parametros.put("cant_Tab_4Px8",cuatrox8+"");
                    parametros.put("cant_Tab_4Px6",cuatrox6+"");
                    parametros.put("cant_Tab_4Px4",cuatrox4+"");
                    parametros.put("Total_4pies",cuatrototal+"");

                    parametros.put("precio_por_pie8",precioocho+"");
                    parametros.put("precio_por_pie7",preciosiete+"");
                    parametros.put("precio_por_pie6",precioseis+"");
                    parametros.put("precio_por_pie5",preciocinco+"");
                    parametros.put("precio_por_pie4",preciocuatro+"");

                    parametros.put("Total",tvTotal.getText().toString());

                    return parametros;

                }
            };
            requestQueue= Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(getActivity(), "Agrega al carrito", Toast.LENGTH_SHORT).show();
        }
    }

    private void Limpiar() {
            txtproveedor.setText("");
            txtprecio.setText("");
            txtCantidad.setText("");
            tvTotal.setText("0");
            comprar.clear();
            cargar_tabla();

    }

}
