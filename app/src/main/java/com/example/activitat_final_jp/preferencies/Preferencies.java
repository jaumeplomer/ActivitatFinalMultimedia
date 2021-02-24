package com.example.activitat_final_jp.preferencies;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencies {

    private static final String NOM_PREFERENCIES = "PreferenciesQuepassaEh";
    private final static String CLAU_CODIUSUARI="id";
    private final static String CLAU_USER="email";
    private final static String CLAU_PASSWD="passwd";
    private final static String TOKEN="token";
    private final static String CLAU_DARRER_MISSATGE="darrerMissatge";
    private final static String RECORDA="recorda";
    private String codiusuari;
    private String user;
    private String password;
    private String token;
    private int darrerMissatge;
    private Boolean recorda;
    private SharedPreferences prefs;

    public Preferencies(Context ctx) {
        this.prefs = ctx.getSharedPreferences(NOM_PREFERENCIES, ctx.MODE_PRIVATE);
        this.codiusuari = this.prefs.getString(CLAU_CODIUSUARI, "-1");
        this.user = this.prefs.getString(CLAU_USER, "");
        this.password = this.prefs.getString(CLAU_PASSWD, "");
        this.token = this.prefs.getString(TOKEN, "");
        this.recorda = this.prefs.getBoolean(RECORDA,false);
        this.darrerMissatge = this.prefs.getInt(CLAU_DARRER_MISSATGE,0);
    }

    public String getCodiusuari() {
        return codiusuari;
    }

    public void setCodiusuari(String codiusuari) {
        this.codiusuari = codiusuari;
        SharedPreferences.Editor editor = this.prefs.edit();
        editor.putString(CLAU_CODIUSUARI,codiusuari);
        editor.commit();
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
        SharedPreferences.Editor editor = this.prefs.edit();
        editor.putString(CLAU_USER,user);
        editor.commit();
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        SharedPreferences.Editor editor = this.prefs.edit();
        editor.putString(CLAU_PASSWD,password);
        editor.commit();
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
        SharedPreferences.Editor editor = this.prefs.edit();
        editor.putString(TOKEN,token);
        editor.commit();
    }

    public Boolean getRecorda() {
        return recorda;
    }
    public void setRecorda(Boolean recorda) {
        this.recorda = recorda;
        SharedPreferences.Editor editor = this.prefs.edit();
        editor.putBoolean(RECORDA,recorda);
        editor.commit();
    }

    public int getDarrerMissatge() {
        return darrerMissatge;
    }

    public void setDarrerMissatge(int darrerMissatge) {
        this.darrerMissatge = darrerMissatge;
        SharedPreferences.Editor editor = this.prefs.edit();
        editor.putInt(CLAU_DARRER_MISSATGE,darrerMissatge);
        editor.commit();
    }
}
