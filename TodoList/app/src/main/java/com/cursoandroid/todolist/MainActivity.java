package com.cursoandroid.todolist;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private TextView textoTarefa;
    private Button botaoAdicionar;
    private ListView listaTarefas;
    private SQLiteDatabase bancoDados;
    private ArrayAdapter<String> itensAdaptador;
    private ArrayList<String> itens;
    private ArrayList<Integer> ids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            //Recuperar componentes
            listaTarefas = (ListView) findViewById(R.id.listViewId);
            botaoAdicionar = (Button) findViewById(R.id.botaoAdicionarId);

            //Banco de dados
            bancoDados = openOrCreateDatabase("apptarefas", MODE_PRIVATE, null);

            //Tabela tarefas
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tarefas ( id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR )");

            //bancoDados.execSQL("DELETE FROM tarefas");

            botaoAdicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    salvarTarefa();
                }
            });

            listaTarefas.setLongClickable(true);
            listaTarefas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    removerTarefa(ids.get(i));
                    return true;
                }
            });


            //Recuperar lista de tarefas
            recuperarTarefas();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void salvarTarefa() {

        try {

            textoTarefa = (TextView) findViewById(R.id.textoId);
            String textoDigitado = textoTarefa.getText().toString();

            if (textoDigitado.equals("")) {
                Toast.makeText(MainActivity.this, "Digite uma tarefa", Toast.LENGTH_SHORT).show();
            } else {
                bancoDados.execSQL("INSERT INTO tarefas ( tarefa ) VALUES ( '" + textoDigitado + "' )");
                Toast.makeText(MainActivity.this, "Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show();
                recuperarTarefas();
                textoTarefa.setText("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recuperarTarefas() {

        try {

            Cursor cursor = bancoDados.rawQuery("SELECT * FROM tarefas ORDER BY id", null);

            //Recuperar os indices dos campos
            int indiceCampoId = cursor.getColumnIndex("id");
            int indiceCampoTarefa = cursor.getColumnIndex("tarefa");

            itens = new ArrayList<String>();
            ids = new ArrayList<Integer>();

            itensAdaptador = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    itens);

            listaTarefas.setAdapter( itensAdaptador );


            //Lista tarefas
            cursor.moveToFirst();
            while (cursor != null) {

                itens.add(cursor.getString(indiceCampoTarefa));
                ids.add(cursor.getInt(indiceCampoId));
                Log.i("Resultado - ", "Tarefa id:" + cursor.getString(indiceCampoId));
                Log.i("Resultado - ", "Tarefa tarefa:" + cursor.getString(indiceCampoTarefa));

                cursor.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removerTarefa(int id) {

        try {

            bancoDados.execSQL("DELETE FROM tarefas WHERE id=" + id);
            recuperarTarefas();
            Toast.makeText(MainActivity.this, "Tarefa removida com sucesso!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
