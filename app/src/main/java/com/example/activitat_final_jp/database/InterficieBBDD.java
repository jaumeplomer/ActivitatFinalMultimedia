package com.example.activitat_final_jp.database;

import android.content.Context;
import android.database.Cursor;

import com.example.activitat_final_jp.missatges.Missatge;

import org.json.JSONObject;

import java.util.ArrayList;

public class InterficieBBDD {

    private static AjudaBBDD db;
    private static boolean igual = true;

    public InterficieBBDD(Context context)
    {
        db = new AjudaBBDD(context);
    }

    public static void emmagatzemaRebut(JSONObject jObject)
    {

    }

    public static boolean codiUsuariExisteix(String codiUsuari)
    {

        return igual;
    }

    public static ArrayList<Missatge> llistaMissatges(int idUltimoMsg)
    {

        return null;
    }

    public static Missatge cursorToMissatge(Cursor cursor)
    {
        return null;
    }

    public static String getNomUsuari(String userId)
    {
        return null;
    }

    public static void buidaMissatges()
    {

    }
}
