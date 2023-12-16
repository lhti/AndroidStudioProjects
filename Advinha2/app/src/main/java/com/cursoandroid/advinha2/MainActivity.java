package com.cursoandroid.advinha2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button botaoJogar;
    private TextView textoResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoResultado = (TextView) findViewById(R.id.resultadoId);
        //textoResultado.setText("Texto Alterado");

        botaoJogar = (Button) findViewById(R.id.botaoJogarId);
        botaoJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random randomico = new Random();

                int numeroAleatorio = randomico.nextInt(10);

                textoResultado.setText("O número sorteado é " + numeroAleatorio);
            }
        });
    }
}
