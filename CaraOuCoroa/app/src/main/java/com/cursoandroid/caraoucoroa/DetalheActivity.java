package com.cursoandroid.caraoucoroa;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DetalheActivity extends AppCompatActivity {

    private ImageView imagem;
    private ImageView botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        imagem = (ImageView) findViewById(R.id.imageId);
        botaoVoltar = (ImageView) findViewById(R.id.botaoVoltarId);

        Bundle extra = getIntent().getExtras();

        if ( extra != null ) {

            String opcaoEscolhida = extra.getString("opcao");
            if (opcaoEscolhida.equals("Cara")) {
                imagem.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.moeda_cara));
            } else {
                imagem.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.moeda_coroa));
            }

        }

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetalheActivity.this, MainActivity.class));
            }
        });

    }
}
