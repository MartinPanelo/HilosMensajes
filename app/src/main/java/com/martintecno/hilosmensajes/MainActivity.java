package com.martintecno.hilosmensajes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, 1000);

        }

        intent = new Intent(this, ServicioMensaje.class);
    }

    public void IniciarServicio(View view){

        this.startService(intent);
    }
    public void FinalizarServicio(View view){

        this.stopService(intent);
    }









}


/*
    public void iniciarHilo(){
        hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    while (true) { no es true es un bool que se manjeda desde el create o destroy del service
                        Log.d("Contador","numero : " + contador);
                        contador++;
                        Thread.sleep(5000);
                    }

                }catch (Exception ex){

                }
            }
        });
    }*/
