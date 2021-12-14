package com.example.proyectom;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import modelos.DatosReporte;

public class adaptador extends RecyclerView.Adapter<adaptador.ViewHolderDatos>{
    ArrayList<DatosReporte> listadatos;

    public adaptador(ArrayList<DatosReporte> listadatos) {
        this.listadatos = listadatos;
    }




    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.itemreporte,null,false);
        return new ViewHolderDatos(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {

        holder.id.setText(listadatos.get(position).getId());
        holder.fecha.setText(listadatos.get(position).getFecha());
        holder.provee.setText(listadatos.get(position).getProvee());

        holder.odo.setText(listadatos.get(position).getOdo());
        holder.odi.setText(listadatos.get(position).getOdi());
        holder.och.setText(listadatos.get(position).getOch());
        holder.ose.setText(listadatos.get(position).getOse());
        holder.ocu.setText(listadatos.get(position).getOcu());

        holder.sido.setText(listadatos.get(position).getSido());
        holder.sidi.setText(listadatos.get(position).getSidi());
        holder.sich.setText(listadatos.get(position).getSich());
        holder.sise.setText(listadatos.get(position).getSise());
        holder.sicu.setText(listadatos.get(position).getSicu());

        holder.sedo.setText(listadatos.get(position).getSedo());
        holder.sedi.setText(listadatos.get(position).getSedi());
        holder.sech.setText(listadatos.get(position).getSech());
        holder.sese.setText(listadatos.get(position).getSese());
        holder.secu.setText(listadatos.get(position).getSecu());

        holder.cido.setText(listadatos.get(position).getCido());
        holder.cidi.setText(listadatos.get(position).getCidi());
        holder.cich.setText(listadatos.get(position).getCich());
        holder.cise.setText(listadatos.get(position).getCise());
        holder.cicu.setText(listadatos.get(position).getCicu());

        holder.cudo.setText(listadatos.get(position).getCudo());
        holder.cudi.setText(listadatos.get(position).getCudi());
        holder.cuch.setText(listadatos.get(position).getCuch());
        holder.cuse.setText(listadatos.get(position).getCuse());
        holder.cucu.setText(listadatos.get(position).getCucu());

        holder.to8.setText(listadatos.get(position).getTo8());
        holder.to7.setText(listadatos.get(position).getTo7());
        holder.to6.setText(listadatos.get(position).getTo6());
        holder.to5.setText(listadatos.get(position).getTo5());
        holder.to4.setText(listadatos.get(position).getTo4());

        holder.pre8.setText(listadatos.get(position).getPre8());
        holder.pre7.setText(listadatos.get(position).getPre7());
        holder.pre6.setText(listadatos.get(position).getPre6());
        holder.pre5.setText(listadatos.get(position).getPre5());
        holder.pre4.setText(listadatos.get(position).getPre4());

        holder.total.setText(listadatos.get(position).getTotal());
    }
    @Override
    public int getItemCount() {
        return listadatos.size();
    }
    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView id,fecha,provee;

        TextView odo,odi,och,ose,ocu;
        TextView sido,sidi,sich,sise,sicu;
        TextView sedo,sedi,sech,sese,secu;
        TextView cido,cidi,cich,cise,cicu;
        TextView cudo,cudi,cuch,cuse,cucu;
        TextView to8,to7,to6,to5,to4;

        TextView pre8,pre7,pre6,pre5,pre4,total;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.id);
            fecha=itemView.findViewById(R.id.fecha);
            provee=itemView.findViewById(R.id.proveedor);

            odo=itemView.findViewById(R.id.o12);
            odi=itemView.findViewById(R.id.o10);
            och=itemView.findViewById(R.id.o8);
            ose=itemView.findViewById(R.id.o6);
            ocu=itemView.findViewById(R.id.o4);

            sido=itemView.findViewById(R.id.si12);
            sidi=itemView.findViewById(R.id.si10);
            sich=itemView.findViewById(R.id.si8);
            sise=itemView.findViewById(R.id.si6);
            sicu=itemView.findViewById(R.id.si4);

            sedo=itemView.findViewById(R.id.se12);
            sedi=itemView.findViewById(R.id.se10);
            sech=itemView.findViewById(R.id.se8);
            sese=itemView.findViewById(R.id.se6);
            secu=itemView.findViewById(R.id.se4);

            cido=itemView.findViewById(R.id.c12);
            cidi=itemView.findViewById(R.id.c10);
            cich=itemView.findViewById(R.id.c8);
            cise=itemView.findViewById(R.id.c6);
            cicu=itemView.findViewById(R.id.c4);

            cudo=itemView.findViewById(R.id.cu12);
            cudi=itemView.findViewById(R.id.cu10);
            cuch=itemView.findViewById(R.id.cu8);
            cuse=itemView.findViewById(R.id.cu6);
            cucu=itemView.findViewById(R.id.cu4);

            pre8=itemView.findViewById(R.id.preciopie8);
            pre7=itemView.findViewById(R.id.preciopie7);
            pre6=itemView.findViewById(R.id.preciopie6);
            pre5=itemView.findViewById(R.id.preciopie5);
            pre4=itemView.findViewById(R.id.preciopie4);

            to8=itemView.findViewById(R.id.totalo);
            to7=itemView.findViewById(R.id.totasi);
            to6=itemView.findViewById(R.id.totase);
            to5=itemView.findViewById(R.id.totalc);
            to4=itemView.findViewById(R.id.totalcu);

            total=itemView.findViewById(R.id.Total);



        }


    }
}
