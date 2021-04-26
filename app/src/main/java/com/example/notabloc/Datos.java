package com.example.notabloc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Datos extends SQLiteOpenHelper {
    String crear = "CREATE TABLE Notas(titulo text Primary Key, contenido text)";

    public Datos(Context contexto, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(contexto, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(crear);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnt, int versionNue){
        db.execSQL("DROP TABLE IF EXISTS Notas");
        db.execSQL(crear);
    }
}
