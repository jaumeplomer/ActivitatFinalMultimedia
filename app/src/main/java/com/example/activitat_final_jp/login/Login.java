package com.example.activitat_final_jp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.activitat_final_jp.R;
import com.example.activitat_final_jp.xarxa.Auxiliar;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private EditText email, password;
    private CheckBox checkBox;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        checkBox = findViewById(R.id.checkBox);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String result = Auxiliar.interacionPost(email.getText().toString(), password.getText().toString(), true);

                if (!result.trim().isEmpty())
                {
                    try
                    {
                        JSONObject json = new JSONObject(result);
                        Log.d("JSOM",result);
                        if (json.getBoolean("correcta"))
                        {
                            Intent intent = new Intent();
                            intent.putExtra("user", json.getString("dades"));
                            intent.putExtra("pass", password.getText().toString().toUpperCase());
                            intent.putExtra("recorda", checkBox.isChecked());
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        } else {
                            logInError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    logInError();
                }
            }
        });
    }

    private void logInError()
    {
        Toast.makeText(this,"LoginError", Toast.LENGTH_SHORT).show();
    }
}