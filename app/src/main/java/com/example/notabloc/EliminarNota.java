package com.example.notabloc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class EliminarNota extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    ListView lv;
    Datos datos;
    private SQLiteDatabase db;
    ArrayList<String> listaNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_nota);
        lv = findViewById(R.id.listaNotasEliminar);
        datos = new Datos(getApplicationContext(), "Datos", null, 1);
        buscar();
        adapter = new ArrayAdapter(this, R.layout.personalizarlista, listaNotas);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String titulo = listaNotas.get(position);
                eliminar(titulo);
                Intent i = new Intent(lv.getContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void eliminar(String elimino){
        String[] titulo = {elimino};
        datos = new Datos(getApplicationContext(), "Datos", null, 1);
        db = datos.getReadableDatabase();

        try {
            //db.execSQL("Delete from Notas where titulo=?", titulo);
            if(db.delete("Notas", "titulo=?", titulo) != -1){
                Toast.makeText(this, "La nota " + titulo[0] + " fue eliminada. ", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
            else{
                Toast.makeText(this, "La nota no pudo ser eliminada. ", Toast.LENGTH_LONG).show();
            }
            db.close();
        } catch (Exception e) {
            Toast.makeText(this, "No existe la base de datos. ", LENGTH_LONG).show();
        }

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
