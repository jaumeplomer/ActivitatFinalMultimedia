package com.example.activitat_final_jp.missatges;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activitat_final_jp.R;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;

public class MissatgeAdapter extends RecyclerView.Adapter<MissatgeAdapter.Holder> {

    public int TIPUS_IGUAL = 1;
    public int TIPUS_DIFF = 2;
    public LayoutInflater inflador = null;
    public Context ct;
    public List<Missatge> llista_missatges;
    private int idUserActualEnter;


    public MissatgeAdapter(Context ct, ArrayList<Missatge> llista_missatges, String idUserActual)
    {
        this.ct = ct;
        this.llista_missatges = llista_missatges;
    }

    @Override
    public int getItemViewType(int position) {
        if (idUserActualEnter == Integer.parseInt(llista_missatges.get(position).getIdUsuari()))
        {
            return TIPUS_IGUAL;
        }
        else {
            return TIPUS_DIFF;
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {


        //viewType = TIPUS_DIFF;
        inflador = LayoutInflater.from(parent.getContext());
       if (viewType == TIPUS_IGUAL)
       {

           View view = inflador.inflate(R.layout.missatge_dreta, parent, false);
           return new Holder(view);
       }
       else {
           View view = inflador.inflate(R.layout.missatge_esquerra, parent, false);
           return new Holder(view);
       }
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Missatge missatge = llista_missatges.get(position);
        holder.etMissatge.setText(missatge.getMissatge());
        holder.etData.setText(missatge.getData());
        holder.etNom.setText(missatge.getNom());
    }


    @Override
    public int getItemCount() {
        return llista_missatges.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        public TextView etMissatge, etData, etNom;

        public Holder(View itemView) {
            super(itemView);
            etData = itemView.findViewById(R.id.dataDr);
            etMissatge = itemView.findViewById(R.id.missatgeDr);
            etNom = itemView.findViewById(R.id.nomDr);
        }
    }
}

