package com.example.activitat_final_jp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.activitat_final_jp.missatges.Missatge;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InterficieBBDD {

    private static AjudaBBDD db;
    private static SQLiteDatabase sqlDb;
    private static boolean igual = true;

    public InterficieBBDD(Context context)
    {
        db = new AjudaBBDD(context);
    }

    public void obri()
    {
        try
        {
            sqlDb = db.getWritableDatabase();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void emmagatzemaRebut(JSONObject jObject) {

        Missatge msg = new Missatge();
        Log.w("test", jObject.toString());

        try
        {
            msg.setMissatge(jObject.getString("msg"));
            msg.setId(jObject.getString("codimissatge"));
            msg.setData(jObject.getString("datahora"));
            msg.setIdUsuari(jObject.getString("codiusuari"));
            msg.setNom(jObject.getString("nom"));

            ContentValues contingut = new ContentValues();
            ContentValues contingut2 = new ContentValues();
            contingut.put(AjudaBBDD.COL_MSG_ID, msg.getId());
            contingut.put(AjudaBBDD.COL_MSG_MSG, msg.getMissatge());
            contingut.put(AjudaBBDD.COL_MSG_DATE, msg.getData());
            contingut.put(AjudaBBDD.COL_MSG_ID_USER, msg.getIdUsuari());

            contingut2.put(AjudaBBDD.COL_USER_ID, msg.getIdUsuari());
            contingut2.put(AjudaBBDD.COL_USER_NAME, msg.getNom());
            contingut2.put(AjudaBBDD.COL_USER_EMAIL, msg.getNom());
            contingut2.put(AjudaBBDD.COL_USER_IMG, msg.getNom());

            sqlDb.insert(AjudaBBDD.TAB_USER, null, contingut2);
            sqlDb.insert(AjudaBBDD.TAB_MSG, null, contingut);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static boolean codiUsuariExisteix(String codiUsuari)
    {

        return igual;
    }

    public static ArrayList<Missatge> llistaMissatges(int idUltimoMsg)
    {
        ArrayList<Missatge> test = new ArrayList<>();

        Cursor cursor = sqlDb.rawQuery("SELECT DISTINCT msg.id, msg.msg, msg.date, msg.userId, msg.lstMsg, usuario.id, usuario.name FROM msg, usuario  WHERE msg.userId = usuario.id",null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
          Missatge msg = cursorToMissatge(cursor);
          //msg.setNom(cursor1.getString(0));
          test.add(msg);
          cursor.moveToNext();
        }
        cursor.close();
        return test;
    }

    public static Missatge cursorToMissatge(Cursor cursor)
    {
        Missatge msg = new Missatge();
        msg.setId(cursor.getString(0));
        msg.setMissatge(cursor.getString(1));
        msg.setData(cursor.getString(2));
        msg.setIdUsuari(cursor.getString(3));
        msg.setNom(cursor.getString(6));
        return msg;
    }

    public static String getNomUsuari(String userId)
    {
        String select = "SELECT " + AjudaBBDD.COL_USER_NAME + " FROM " + AjudaBBDD.TAB_USER + " WHERE " + AjudaBBDD.COL_USER_ID + " = " + userId;
        return select;
    }

    public static void buidaMissatges()
    {
        sqlDb.delete(AjudaBBDD.TAB_MSG, null, null);
        sqlDb.delete(AjudaBBDD.TAB_USER, null, null);
    }
}
