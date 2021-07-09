package com.example.notabloc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


import static android.widget.Toast.LENGTH_LONG;

public class MostrarNotas extends AppCompatActivity  {

    private ArrayAdapter<String> adapter;
    ListView lv;
    Datos datos;
    private SQLiteDatabase db;
    ArrayList<String> listaNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_notas);
        lv = findViewById(R.id.listaNotas);
        datos = new Datos(getApplicationContext(), "Datos", null, 1);
        buscar();
        adapter = new ArrayAdapter(this, R.layout.personalizarlista, listaNotas);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String titulo = listaNotas.get(position);
                Intent i = new Intent(lv.getContext(), MostrarNota.class);
                i.putExtra("titulo", titulo);
                startActivity(i);
                overridePendingTransition(R.anim.transicion_3, R.anim.transicion_4);
            }
        });

    }

    public void buscar() {
        listaNotas = new ArrayList<String>();
        db = datos.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT titulo FROM Notas", null);
            if (cursor.moveToFirst()) {
                do {
                    listaNotas.add(cursor.getString(0));
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this, "No existen datos", LENGTH_LONG).show();
            }
            db.close();
        } catch (Exception e) {
            Toast.makeText(this, "No existe la base de datos. ", LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        datos = new Datos(this, "Datos", null, 1);
        db = datos.getReadableDatabase();
    }

}
