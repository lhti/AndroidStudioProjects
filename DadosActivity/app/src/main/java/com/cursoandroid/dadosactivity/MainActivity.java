package com.cursoandroid.dadosactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class  MainActivity extends Activity {

    private EditText campoTexto;
    private Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campoTexto = (EditText) findViewById(R.id.campoTextoId);
        botao = (Button) findViewById(R.id.botaoId);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SegundaActivity.class);
                intent.putExtra("nome", campoTexto.getText().toString());

                startActivity(intent);

            }
        });

    }
}
