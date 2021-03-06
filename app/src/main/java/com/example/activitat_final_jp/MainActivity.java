package com.example.activitat_final_jp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activitat_final_jp.database.InterficieBBDD;
import com.example.activitat_final_jp.login.Login;
import com.example.activitat_final_jp.preferencies.Preferencies;
import com.example.activitat_final_jp.xarxa.Auxiliar;
import com.example.activitat_final_jp.xarxa.Enviament;
import com.example.activitat_final_jp.xarxa.Recepcio;
import com.example.activitat_final_jp.xarxa.ReceptorXarxa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ReceptorXarxa receptor;
    private final int LOGIN = 1;
    private Preferencies pref;
    private RecyclerView recyclerView;
    private final Handler handler = new Handler();
    private Runnable run;
    private Intent intent;
    private EditText editText;
    private InterficieBBDD db;
    private Boolean provaMissatge = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receptor = new ReceptorXarxa();
        this.registerReceiver(receptor, filter);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        pref = new Preferencies(this);
        db = new InterficieBBDD(this.getApplicationContext());
        db.obri();

        try {
            JSONObject prova_token = new JSONObject(Auxiliar.verificacioUsuari(pref));
            if (!prova_token.getBoolean("correcta")) {
                intent = new Intent(this, Login.class);
                startActivityForResult(intent, LOGIN);
            }
            pref.setDarrerMissatge(prova_token.getInt("darrermissatge"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.msg);
        Log.w("test", "hola");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviar(v);
            }
        });

        Runnable run = new Runnable() {
            public void run()
            {
                try
                {
                    Log.w("test", "run");
                    Auxiliar.processarMissatges(recyclerView,null, pref, null, provaMissatge);
                    new Recepcio(MainActivity.this, recyclerView, pref, provaMissatge).execute();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                handler.postDelayed(this, 1000*60);
            }
        };
        Auxiliar.processarMissatges(recyclerView,this, pref,null, provaMissatge);
        Log.w("test", "run2");
    }

    public void enviar(View v)
    {
        if (!editText.getText().toString().equals(""))
        {
            new Enviament(pref).execute(editText.getText().toString());
            editText.setText("");

            new Recepcio(this, recyclerView, pref, provaMissatge).execute();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    JSONObject user = new JSONObject(data.getStringExtra("user"));

                    pref.setCodiusuari(user.getString("codiusuari"));
                    pref.setUser(user.getString("email"));
                    pref.setToken(user.getString("token"));
                    pref.setPassword(data.getStringExtra("pass"));

                    pref.setRecorda((data.getBooleanExtra("recorda", true)));

                    JSONObject prova_usuari = new JSONObject(Auxiliar.verificacioUsuari(pref));
                    pref.setDarrerMissatge(prova_usuari.getInt("darrermissatge"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                intent  = new Intent(this, Login.class);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.removeCallbacks(run);
        handler.post(run);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receptor != null)
        {
            this.unregisterReceiver(receptor);
        }
    }
}