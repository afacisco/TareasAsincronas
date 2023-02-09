package com.example.tareasasincronas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

/*
Autor: Juan Francisco Sánchez González
Fecha: 07/02/2023
Clase: Actividad que contiene un cuadro de diálogo de progreso en espera de la simulación de una copia de
 seguridad. Se ejecuta una tarea asíncrona para realizar la simulación.
*/

public class MainActivity extends AppCompatActivity {

    // Constantes Progreso y Tiempo de progreso
    private final static int INICIO_BARRA_PROGRESO = 0;
    private final static int MAX_BARRA_PROGRESO = 1000;
    private final static int BARRA_PROGRESO = 200;
    private final static int TIEMPO_PROGRESO = 300;
    private final static int BARRA_PORCENTAJE = 100;


    private ProgressDialog dialogo;
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Componente diálogo de progreso
        dialogo = new ProgressDialog(this);
        dialogo.setMessage(getResources().getString(R.string.dialogo_mens));
        dialogo.setTitle(getResources().getString(R.string.dialogo_titulo));
        dialogo.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        // Componente Button
        boton = (Button) findViewById(R.id.button);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se ejecuta la tarea asíncrona
                new TareaAsincrona().execute();
            }
        });
    }

    // Clase con la lógica de la tarea de la asíncrona (tarea en segundo plano)
    private class TareaAsincrona extends AsyncTask<Void, Float, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            for (int i = 0; i < BARRA_PROGRESO; i++) {
                try {
                    Thread.sleep(TIEMPO_PROGRESO);
                } catch (InterruptedException e) {}
                publishProgress(i/(float)BARRA_PROGRESO);
            }
            return BARRA_PROGRESO;
        }


        @Override
        protected void onPostExecute(Integer bytes) {
            dialogo.dismiss();
        }


        @Override
        protected void onPreExecute() {
            dialogo.setProgress(INICIO_BARRA_PROGRESO);
            dialogo.setMax(MAX_BARRA_PROGRESO);
            dialogo.show();
        }


        @Override
        protected void onProgressUpdate(Float... valores) {
            int valor = Math.round(BARRA_PORCENTAJE * valores[0]);
            dialogo.setProgress(valor);

        }
    }


}
