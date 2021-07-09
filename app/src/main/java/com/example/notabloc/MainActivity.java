package com.example.notabloc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void crearNota(View v){
        Intent i = new Intent(this, CrearNotas.class);
        startActivity(i);
        overridePendingTransition(R.anim.transicion_3, R.anim.transicion_4);
    }

    public void eliminarNota(View v){
        Intent i = new Intent(this, EliminarNota.class);
        startActivity(i);
        overridePendingTransition(R.anim.transicion_1, R.anim.transicion_4);
    }

    public void verNotas(View v){
        Intent i = new Intent(this, MostrarNotas.class);
        startActivity(i);
        overridePendingTransition(R.anim.transicion_3, R.anim.transiciones_2);
    }

    public void editarNota(View v){
        Intent i = new Intent(this, EditarNota.class);
        startActivity(i);
        overridePendingTransition(R.anim.transicion_1, R.anim.transiciones_2);
    }
}
