package com.cursoandroid.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button botao;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao = (Button) findViewById(R.id.botaoId);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Criar dialog
                dialog = new AlertDialog.Builder( MainActivity.this );

                //configurar o título
                dialog.setTitle("Título da dialog");

                //configurar a mensagem
                dialog.setMessage("Mensagem da dialog");

                dialog.setCancelable(false);
                dialog.setIcon(android.R.drawable.ic_delete);

                //configurar botão negativo
                dialog.setNegativeButton("Não",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText( MainActivity.this, "Pressionado o botão Não", Toast.LENGTH_SHORT).show();
                            }
                        });

                //configurar botão postivo
                dialog.setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText( MainActivity.this, "Pressionado o botão Sim", Toast.LENGTH_SHORT).show();
                            }
                        });

                dialog.create();
                dialog.show();

            }
        });


    }
}
