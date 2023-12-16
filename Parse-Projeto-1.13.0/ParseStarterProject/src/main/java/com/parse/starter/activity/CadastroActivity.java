package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.starter.R;
import com.parse.starter.util.ParseErros;

import org.w3c.dom.Text;

public class CadastroActivity extends AppCompatActivity {

    private EditText textoLogin;
    private EditText textoEmail;
    private EditText textoSenha;
    private TextView textoFacaLogin;
    private Button botaoCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        textoLogin = (EditText) findViewById(R.id.edit_cadastro_Login);
        textoEmail = (EditText) findViewById(R.id.edit_cadastro_Email);
        textoSenha = (EditText) findViewById(R.id.edit_cadastro_Senha);

        textoFacaLogin = (TextView) findViewById(R.id.text_faca_login);

        textoFacaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirLoginUsuario();
            }
        });

        botaoCadastrar = (Button) findViewById(R.id.btnCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {

        //Cria o objeto usuario
        ParseUser usuario = new ParseUser();
        usuario.setUsername(textoLogin.getText().toString());
        usuario.setEmail(textoEmail.getText().toString());
        usuario.setPassword(textoSenha.getText().toString());

        usuario.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(CadastroActivity.this, "Cadastro feito com sucesso!", Toast.LENGTH_SHORT).show();
                    abrirLoginUsuario();
                } else {
                    ParseErros parseErros = new ParseErros();
                    String erro = parseErros.getErro(e.getCode());
                    Toast.makeText(CadastroActivity.this, "Erro ao cadastrar usuário - " + erro, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
