package com.example.activitat_final_jp.xarxa;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activitat_final_jp.database.InterficieBBDD;
import com.example.activitat_final_jp.missatges.Missatge;
import com.example.activitat_final_jp.missatges.MissatgeAdapter;
import com.example.activitat_final_jp.preferencies.Preferencies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Auxiliar {
    static String a = "a";


    public static String interacionPost(String arg, String codiUser, boolean login)
    {
        return a;
    }

    public static String interacioGet(Preferencies p, boolean prova)
    {
        StringBuilder text = new StringBuilder();
        URL url;
        try {
            url = new URL("http://54.211.78.147/uepcomanam.tb/public/provamissatge/");

            HttpURLConnection httpConnexio = (HttpURLConnection) url.openConnection();
            httpConnexio.setReadTimeout(15000);
            httpConnexio.setChunkedStreamingMode(25000);
            httpConnexio.setRequestMethod("GET");
            httpConnexio.connect();

            int responseCode = httpConnexio.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpConnexio.getInputStream()));
                String liniatxt;
                while ((liniatxt = in.readLine()) != null)
                {
                    text.append(liniatxt);
                }
                in.close();
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        return text.toString();
    }

  /*  public static String verificacioUsuari(Preferencies pref)
    {
        return a;
    }*/

    public static void processarMissatges(RecyclerView rv, Context ct, Preferencies pref, String resultat, boolean prova)
    {
        try {
            JSONObject json = new JSONObject(resultat);
            JSONArray jArr = json.getJSONArray("dades");
            if (prova)
            {
                InterficieBBDD.buidaMissatges();
            }
            for (int i = 0; i < jArr.length(); i++)
            {
                JSONObject jsonObject = jArr.getJSONObject(i);
                InterficieBBDD.emmagatzemaRebut(jsonObject);
            }
            if (prova)
            {
                mostrarMissatges(rv, ct, 0, pref.getCodiusuari());
            } else {
                mostrarMissatges(rv, ct, pref.getDarrerMissatge(), pref.getCodiusuari());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void mostrarMissatges(RecyclerView rv, Context ct, int darrerMissatge, String idUser)
    {
        ArrayList<Missatge> missatges = InterficieBBDD.llistaMissatges(darrerMissatge);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(ct, LinearLayoutManager.VERTICAL, false));

        MissatgeAdapter missatgeAdapter = new MissatgeAdapter(ct, missatges, idUser);
        rv.setAdapter(missatgeAdapter);
        rv.smoothScrollToPosition(missatges.size());
    }
}
