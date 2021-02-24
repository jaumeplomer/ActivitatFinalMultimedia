package com.example.activitat_final_jp.xarxa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ReceptorXarxa extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        ActualitzaEstatXarxa(context);
    }

    public void ActualitzaEstatXarxa(Context context)
    {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected())
        {
            Toast.makeText(context,"Xarxa OK",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"Xarxa no OK",Toast.LENGTH_SHORT).show();
        }
    }
}
