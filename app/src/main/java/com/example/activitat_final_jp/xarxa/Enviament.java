package com.example.activitat_final_jp.xarxa;

import android.os.AsyncTask;

import com.example.activitat_final_jp.preferencies.Preferencies;

public class Enviament extends AsyncTask<String, Integer, String> {

    private Preferencies pref;

    public Enviament(Preferencies pref) {
        this.pref = pref;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        return Auxiliar.interacionPost(params[0], pref.getCodiusuari(), false);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
