package com.example.notabloc;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class MostrarNota extends AppCompatActivity {

    private Datos datos;
    private SQLiteDatabase db;
    private TextView tv_contenido, tv_titulo;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_nota);
        title = getIntent().getStringExtra("titulo");
        tv_contenido = findViewById(R.id.textView_contenidoNota);
        tv_titulo = findViewById(R.id.textView_tituloNota);
        tv_titulo.setText(title);

        datos = new Datos(getApplicationContext(), "Datos", null, 1);
        db = datos.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT contenido FROM Notas where titulo='"+title+"'", null);
            if (cursor.moveToFirst()) {
                tv_contenido.setText(cursor.getString(0));
            } else {
                Toast.makeText(this, "No existen datos", LENGTH_LONG).show();
            }
            db.close();
        } catch (Exception e) {
            Toast.makeText(this, "No existe la base de datos. ", LENGTH_LONG).show();
        }
    }
}
