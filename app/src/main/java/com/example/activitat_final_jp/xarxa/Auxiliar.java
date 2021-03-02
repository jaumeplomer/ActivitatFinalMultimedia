package com.example.activitat_final_jp.xarxa;

import android.content.Context;
import android.util.Log;

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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Auxiliar {

    public static String interacionPost(String arg, String codiUser, boolean login)
    {
        if (login)
        {
            StringBuilder text = new StringBuilder();
            URL url;
            try {
                url = new URL("http://54.211.78.147/uepcomanam.tb/public/login/");
                HttpURLConnection httpConnexio = (HttpURLConnection) url.openConnection();
                httpConnexio.setReadTimeout(15000);
                httpConnexio.setChunkedStreamingMode(25000);
                httpConnexio.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                httpConnexio.setRequestProperty("Accept", "application/json");

                httpConnexio.setRequestMethod("POST");


                JSONObject t = new JSONObject();
                t.put("email", arg);
                t.put("password", codiUser);

                OutputStream os = httpConnexio.getOutputStream();
                os.write(t.toString().getBytes("UTF-8"));

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
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            Log.w("prova_token",text.toString());
            return text.toString();
        }
        else
        {
            StringBuilder text = new StringBuilder();
            URL url;
            try {
                url = new URL("http://54.211.78.147/uepcomanam.tb/public/provamissatge/");
                HttpURLConnection httpConnexio = (HttpURLConnection) url.openConnection();
                httpConnexio.setReadTimeout(15000);
                httpConnexio.setChunkedStreamingMode(25000);
                httpConnexio.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                httpConnexio.setRequestProperty("Accept", "application/json");

                httpConnexio.setRequestMethod("POST");

                JSONObject t = new JSONObject();
                t.put("msg", arg);
                t.put("codiusuari",codiUser);

                OutputStream os = httpConnexio.getOutputStream();
                os.write(t.toString().getBytes("UTF-8"));

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
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return text.toString();
        }

    }

    public static String interacioGet(Preferencies pref, boolean prova)
    {
        StringBuilder text = new StringBuilder();
        URL url;
        try {
            url = new URL("http://54.211.78.147/uepcomanam.tb/public/provamissatge/");

            HttpURLConnection httpConnexio = (HttpURLConnection) url.openConnection();
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

            //Aqui fas coses JSON

        }catch (IOException e)
        {
            e.printStackTrace();
        }

        return text.toString();
    }

    public static void processarMissatges(RecyclerView rv, Context ct, Preferencies pref, String resultat, boolean prova)
    {
        Log.w("test", "processarMissatges");
        StringBuilder text = new StringBuilder();
        URL url;
        BufferedReader in = null;
        try {
            url = new URL("http://54.211.78.147/uepcomanam.tb/public/provamissatge/");

            HttpURLConnection httpConnexio = (HttpURLConnection) url.openConnection();
            httpConnexio.setRequestMethod("GET");
            httpConnexio.connect();

            int responseCode = httpConnexio.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                in = new BufferedReader(new InputStreamReader(httpConnexio.getInputStream()));
            }
            String liniatxt;
            while ((liniatxt = in.readLine()) != null)
            {
                text.append(liniatxt);
            }
            in.close();

            JSONObject json = new JSONObject(text.toString());
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
            Log.w("Debug", "Dbug-linia184-Auxiliar");
            if (false)
            {
                mostrarMissatges(rv, ct, 0, pref.getCodiusuari());
            } else {
                mostrarMissatges(rv, ct, pref.getDarrerMissatge(), pref.getCodiusuari());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e)  {
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

    public static String verificacioUsuari(Preferencies pref)
    {
        StringBuilder text = new StringBuilder();
        URL url;
        try {
            url = new URL("http://54.211.78.147/uepcomanam.tb/public/usuari/"+pref.getCodiusuari());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setChunkedStreamingMode(25000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Authorization",pref.getToken());
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String linia;
                while ((linia = in.readLine()) != null)
                {
                    text.append(linia);
                }
                in.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return text.toString();
    }
}
