package com.example.notabloc;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CrearNotas extends AppCompatActivity {
    private Datos datos;
    private SQLiteDatabase db;
    private EditText et_contenido, et_titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_notas);
        et_titulo = findViewById(R.id.editText_tituloNota);
        et_contenido = findViewById(R.id.editText_contenidoNota);
    }

    public void crearNota(View v){
        if(db != null) {
            boolean creador = true;
            String titulo = et_titulo.getText().toString(), contenido = et_contenido.getText().toString();

            if (titulo.isEmpty() || contenido.isEmpty()) {
                Toast.makeText(this, "Debe ingresar titulo y contenido", Toast.LENGTH_LONG).show();
            }
            else {
                try {
                    db.execSQL("INSERT INTO Notas(titulo, contenido) VALUES('" + titulo + "','" + contenido + "')");
                    limpiar();
                }
                catch (Exception e) {
                    Toast.makeText(this,"Error en la insercion de datos", Toast.LENGTH_LONG).show();
                    creador = false;
                }
                if (creador) {
                    Toast.makeText(this, "Datos Insertados", Toast.LENGTH_LONG).show();
                }
            }
        }
        else{
            Toast.makeText(this, "Error de acceso a la base de datos", Toast.LENGTH_LONG).show();
        }
    }

    protected void onResume(){
        super.onResume();
        datos = new Datos(this,"Datos",null,1);
        db = datos.getWritableDatabase();
    }

    protected void onPause(){
        super.onPause();
        datos.close();
    }

    public void limpiar(){
        et_contenido.setText("");
        et_titulo.setText("");
    }
}
