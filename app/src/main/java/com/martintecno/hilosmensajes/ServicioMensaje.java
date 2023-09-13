package com.martintecno.hilosmensajes;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;
import android.content.ContentResolver;

public class ServicioMensaje extends Service {
    private Thread hilo;
    private boolean flag=true;

    private int contador=1;

    @Override
    public void onCreate() {
        super.onCreate();
        RecuperarSMS();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {




        return  START_STICKY ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void RecuperarSMS() {

        Uri MensajesUri = Uri.parse("content://sms/inbox");
        ContentResolver cr = this.getContentResolver();


        hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    while (flag) {
                        if(contador == 6){
                            contador = 1;
                        }
                        Cursor cursor = cr.query(MensajesUri, null, null, null, "date DESC LIMIT 5");
                        String fechaMensaje = null;
                        String textoMensaje = null;
                        String contactoMensaje = null;
                        StringBuilder resultado = new StringBuilder();
                        Log.d("contador",cursor.getCount()+"");
                        if (cursor.getCount() > 0) {

                            while (cursor.moveToNext()) {

                                int fecha = cursor.getColumnIndex(Telephony.Sms.DATE);
                                int contacto = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
                                int mensaje = cursor.getColumnIndex(Telephony.Sms.BODY);

                                fechaMensaje = cursor.getString(fecha);
                                textoMensaje = cursor.getString(mensaje);
                                contactoMensaje = cursor.getString(contacto);
                                resultado.append(contador +" ) +fecha : " + fechaMensaje + "\ncontacto : " + contactoMensaje + "\nmensaje : " + textoMensaje + "\n------------------------\n");
                                contador++;
                            }
                        }
                        Log.d("salida", resultado.toString());

                        Thread.sleep(5000);
                    }

                } catch (Exception ex) {

                }
            }
        });
        hilo.start();

    }

}