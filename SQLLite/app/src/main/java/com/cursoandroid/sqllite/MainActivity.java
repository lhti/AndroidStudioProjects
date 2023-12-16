package com.cursoandroid.sqllite;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            //tabela
            //bancoDados.execSQL("DROP TABLE pessoa");
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoa ( id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR, idade INT(3) )");

            //inserir dados
            //bancoDados.execSQL("DELETE FROM pessoa");

            //bancoDados.execSQL("INSERT INTO pessoa ( nome, idade ) VALUES ( 'Marcos', 30 )");
            //bancoDados.execSQL("INSERT INTO pessoa ( nome, idade ) VALUES ( 'Ana', 20 )");
            bancoDados.execSQL("INSERT INTO pessoa ( nome, idade ) VALUES ( 'Mariana', 18 )");
            //bancoDados.execSQL("INSERT INTO pessoa ( nome, idade ) VALUES ( 'Tiago', 50 )");

            Cursor cursor = bancoDados.rawQuery("SELECT * FROM pessoa", null);

            int indiceColunaNome = cursor.getColumnIndex("nome");
            int indiceColunaIdade = cursor.getColumnIndex("idade");
            int indiceColunaId = cursor.getColumnIndex("id");

            cursor.moveToFirst();
            while (cursor != null) {

                Log.i("RESULTADO - nome: ", cursor.getString(indiceColunaNome));
                Log.i("RESULTADO - idade: ", cursor.getString(indiceColunaIdade));
                Log.i("RESULTADO - id: ", cursor.getString(indiceColunaId));

                cursor.moveToNext();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
