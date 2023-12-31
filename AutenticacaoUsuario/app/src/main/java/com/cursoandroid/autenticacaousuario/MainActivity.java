package com.cursoandroid.autenticacaousuario;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        //Login do usuário
        firebaseAuth.signInWithEmailAndPassword("luis.aguiar@email.com.br", "123456")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("signIn", "Sucesso ao fazer login do usuário!!");
                        } else {
                            Log.i("signIn", "Erro ao fazer login do usuário!!");
                            Log.i("signIn", task.getException().toString());
                        }

                    }
                });

        firebaseAuth.signOut();

        //Verifica usuário logado
        /*if ( firebaseAuth.getCurrentUser() != null ) {
            Log.i("verificaUsuario", "Usuário está logado!!");
        } else {
            Log.i("verificaUsuario", "Usuário não está logado!!");
        }*/

        //Cadastro
        /*firebaseAuth.createUserWithEmailAndPassword("luis.aguiar@email.com.br", "123456")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful() ) {;
                            Log.i("createUser", "Sucesso ao cadastrar usuário!!");
                        } else {
                            Log.i("createUser", "Erro ao cadastrar usuário!!");
                            Log.i( "createUser", task.getException().toString());
                        }
                    }
                });*/
    }
}
