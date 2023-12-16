package com.cursoandroid.preferencescorusuario;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButtonSelecionado;
    private Button botaoSalvar;
    private RelativeLayout layout;
    private final static String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupId);
        botaoSalvar = (Button) findViewById(R.id.botaoSalvarId);
        layout = (RelativeLayout) findViewById(R.id.layoutId);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idRadionButtonEscolhido = radioGroup.getCheckedRadioButtonId();

                if (idRadionButtonEscolhido>0){
                    SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    radioButtonSelecionado = (RadioButton) findViewById(idRadionButtonEscolhido);
                    String corEscolhida = radioButtonSelecionado.getText().toString();
                    editor.putString("corEscolhida", corEscolhida);
                    editor.commit();

                    setBackground(corEscolhida);

                }
            }
        });

        //Recuperar a cor escolhida
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
        if (sharedPreferences.contains("corEscolhida")) {
            String corRecuperada = sharedPreferences.getString("corEscolhida", "Laranja");
            setBackground(corRecuperada);
        }

    }

    private void setBackground (String cor) {
        if (cor.equals("Azul")) {
            layout.setBackgroundColor (Color.parseColor("#495b7c"));
        } else if (cor.equals("Laranja")) {
            layout.setBackgroundColor (Color.parseColor("#ffce26"));
        } else if (cor.equals("Verde")) {
            layout.setBackgroundColor (Color.parseColor("#009670"));
        }
    }
}
