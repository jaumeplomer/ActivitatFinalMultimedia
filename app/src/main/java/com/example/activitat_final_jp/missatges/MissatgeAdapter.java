package com.example.activitat_final_jp.missatges;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activitat_final_jp.R;

import java.net.PortUnreachableException;
import java.util.ArrayList;

public class MissatgeAdapter extends RecyclerView.Adapter {

    private int TIPUS_IGUAL = 1;
    private int TIPUS_DIFF = 2;

    private Context ct;
    private ArrayList<Missatge> llista_missatges;
    private String idUserActual;
    private int idUserActualEnter;


    public MissatgeAdapter(Context ct, ArrayList<Missatge> llista_missatges, String idUserActual)
    {
        this.ct = ct;
        this.llista_missatges = llista_missatges;
        this.idUserActual = idUserActual;
        this.idUserActualEnter = Integer.parseInt(idUserActual);
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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = parent;
       if (viewType == TIPUS_IGUAL)
       {
           MissatgeDretaViewHolder mgdreta = new MissatgeDretaViewHolder(view);
           return mgdreta;
       }
       else {
           MissatgeEsquerraViewHolder mgesq = new MissatgeEsquerraViewHolder(view);
           return mgesq;
       }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MissatgeDretaViewHolder extends RecyclerView.ViewHolder {

        public TextView etNom, etMissatge, etData;

        public MissatgeDretaViewHolder(View itemView) {
            super(itemView);
            etMissatge = itemView.findViewById(R.id.missatgeDr);
            etNom = itemView.findViewById(R.id.nomUserDr);

            Missatge mg = new Missatge();
            mg.setMissatge(etMissatge.getText().toString());
            mg.setNom(etNom.getText().toString());

            assignaInformacio(mg);
        }

        private void assignaInformacio(Missatge missatge)
        {

        }
    }

    public static class MissatgeEsquerraViewHolder extends RecyclerView.ViewHolder {

        public TextView etNom, etMissatge, etData;

        public MissatgeEsquerraViewHolder(View itemView) {
            super(itemView);
            etMissatge = itemView.findViewById(R.id.missatgeEsq);
            etNom = itemView.findViewById(R.id.nomUserEsq);

            Missatge mg = new Missatge();
            mg.setMissatge(etMissatge.getText().toString());
            mg.setNom(etNom.getText().toString());

            assignaInformacio(mg);
        }

        private void assignaInformacio(Missatge missatge)
        {

        }
    }
}

