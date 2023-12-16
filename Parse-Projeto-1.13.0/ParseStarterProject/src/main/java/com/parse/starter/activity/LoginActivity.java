package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.starter.R;


public class LoginActivity extends AppCompatActivity {

    private EditText editLogin;
    private EditText editSenha;
    private Button botaoLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        editLogin = (EditText) findViewById(R.id.edit_login);
        editSenha = (EditText) findViewById(R.id.edit_senha);
        botaoLogar = (Button) findViewById(R.id.btnLogar);

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = editLogin.getText().toString();
                String senha = editSenha.getText().toString();
                verificarLogin(login,senha);
            }
        });

    }

    private void verificarLogin(String login, String senha) {
        ParseUser.logInInBackground(login, senha, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Toast.makeText(LoginActivity.this,"Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    abrirTelaPrincipal();
                } else {
                    Toast.makeText(LoginActivity.this, "Erro ao realizar login - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirCadastroUsuario(View view) {
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    private void verificarUsuarioLogado() {

        if (ParseUser.getCurrentUser() != null) {
            abrirTelaPrincipal();
        }

    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
