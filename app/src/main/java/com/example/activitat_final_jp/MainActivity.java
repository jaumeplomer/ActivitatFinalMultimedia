package com.example.activitat_final_jp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
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

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receptor = new ReceptorXarxa();
        this.registerReceiver(receptor, filter);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        pref = new Preferencies(this);
        db = new InterficieBBDD(this);

        intent = new Intent(this, Login.class);
        startActivityForResult(intent, LOGIN);

        /*
        try {
            JSONObject prova_token = new JSONObject(Auxiliar.verificacioUsuari(pref));
            if (!prova_token.getBoolean("correcta")) {
                intent = new Intent(this, Login.class);
                startActivityForResult(intent, LOGIN);
            }
            pref.setDarrerMissatge(prova_token.getInt("darrermissatge"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.layout.recycler_contingut);
        editText = findViewById(R.id.msg);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviar(v);
            }
        });

        run = new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    new Recepcio(MainActivity.this, recyclerView, pref, provaMissatge).execute();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                handler.postDelayed(this, 1000*60);
            }
        };
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

                    pref.setRecorda((data.getBooleanExtra("recorda", false)));

                    //JSONObject prova_usuari = new JSONObject(Auxiliar.verificacioUsuari(pref));
                    //pref.setDarrerMissatge(prova_usuari.getInt("darrermissatge"));

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