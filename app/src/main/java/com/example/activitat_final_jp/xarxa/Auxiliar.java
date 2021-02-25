package com.example.activitat_final_jp.xarxa;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.activitat_final_jp.preferencies.Preferencies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public static void processarMissatges(RecyclerView r, Context c, Preferencies p,String s, boolean b)
    {

    }
}
