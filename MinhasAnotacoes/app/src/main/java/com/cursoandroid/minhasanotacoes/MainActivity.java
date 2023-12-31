package com.cursoandroid.minhasanotacoes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText texto;
    private ImageView botaoSalvar;
    private static final String NOME_ARQUIVO = "arquivo_anotacao.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (EditText) findViewById(R.id.textoId);
        botaoSalvar = (ImageView) findViewById(R.id.botaoSalvarId);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoDigitado = texto.getText().toString();
                gravarNoArquivo( textoDigitado );
                Toast.makeText(MainActivity.this, "Texto salvo com sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        if (lerArquivo() != null) {
            texto.setText(lerArquivo());
        }

    }

    private void gravarNoArquivo (String texto) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter( openFileOutput(NOME_ARQUIVO, Context.MODE_PRIVATE) );
            outputStreamWriter.write (texto);
            outputStreamWriter.close();

        } catch (IOException e) {
            Log.v("MainActivity", e.toString());
        }
    }

    private String lerArquivo() {

        String resultado = "";

        try {
            //Abrir arquivo
            InputStream arquivo = openFileInput(NOME_ARQUIVO);
            if (arquivo != null) {
                //ler o arquivo
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);

                //gerar o buffer do arquivo lido
                BufferedReader bufferedReader = new BufferedReader( inputStreamReader );

                //Recupera texto do arquivo
                String linhaArquivo = "";
                while ( (linhaArquivo = bufferedReader.readLine()) != null ) {
                    resultado += linhaArquivo;
                }

                arquivo.close();
            }

        } catch (IOException e) {
            Log.v("MainActivity", e.toString());
        }

        return resultado;

    }

}
