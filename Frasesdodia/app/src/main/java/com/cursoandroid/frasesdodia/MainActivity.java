package com.cursoandroid.frasesdodia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button botaoNovaFrase;
    private TextView textoNovaFrase;

    private String[] frases = {
            "Se você traçar metas absurdamente altas e falhar, seu fracasso será muito maior que o sucesso de todos",
            "O sucesso normalmente vem para quem está ocupado demais para procurar por ele",
            "Se você não está disposto  a arricar, esteja disposto a uma vida comum"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoNovaFrase = (TextView) findViewById(R.id.textoNovaFraseId);
        botaoNovaFrase = (Button) findViewById(R.id.botaoNovaFraseId);

        botaoNovaFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt( frases.length );

                textoNovaFrase.setText( frases[numeroRandomico] );
            }
        });

    }
}
