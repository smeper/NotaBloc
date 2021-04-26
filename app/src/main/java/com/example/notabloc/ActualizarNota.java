package com.example.notabloc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class ActualizarNota extends AppCompatActivity {

    private Datos datos;
    private SQLiteDatabase db;
    private TextView tv_titulo;
    private EditText et_contenido;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_nota);

        title = getIntent().getStringExtra("titulo");
        //content = getIntent().getStringExtra("contenido");
        tv_titulo = findViewById(R.id.TextView_tituloNota_Actualizar);
        et_contenido = findViewById(R.id.editText_contenidoNota_Actualizar);
        tv_titulo.setText(title);

        datos = new Datos(getApplicationContext(), "Datos", null, 1);
        db = datos.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT contenido FROM Notas where titulo='"+title+"'", null);
            if (cursor.moveToFirst()) {
                et_contenido.setText(cursor.getString(0));
            } else {
                Toast.makeText(this, "No existen datos", LENGTH_LONG).show();
            }
            db.close();
        } catch (Exception e) {
            Toast.makeText(this, "No existe la base de datos. ", LENGTH_LONG).show();
        }
    }

    public void actualizarNota(View v){
        String title = tv_titulo.getText().toString().trim();
        String contenido = et_contenido.getText().toString().trim();

        if(title.isEmpty()){
            Toast.makeText(this, "Introduce el nombre de la nota a buscar. ", LENGTH_LONG).show();
        }
        else{
            datos = new Datos(getApplicationContext(), "Datos", null, 1);
            db = datos.getReadableDatabase();
            try {
                ContentValues valores = new ContentValues();
                valores.put("contenido", contenido);

                if(db.update("Notas", valores, "titulo ='" + title + "'", null) != -1){
                    Toast.makeText(this, "La nota " + title + " ha sido modificada. ", Toast.LENGTH_LONG).show();
                    tv_titulo.setText("");
                    et_contenido.setText("");
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(this, "El registro no puede ser modificado", Toast.LENGTH_LONG).show();
                }

                db.close();
            } catch (Exception e) {
                Toast.makeText(this, "No existe la base de datos. ", LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        datos = new Datos(this, "Datos", null, 1);
        db = datos.getWritableDatabase();
    }

    @Override
    protected void onPause() {
        super.onPause();
        db.close();
    }
}
