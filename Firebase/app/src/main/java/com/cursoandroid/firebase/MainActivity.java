package com.cursoandroid.firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference firebaseReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarioReferencia = firebaseReferencia.child("usuarios");
    private DatabaseReference produtoReferencia = firebaseReferencia.child("produtos").child("001");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioReferencia.child("002").child("nome").setValue("Maria da Silva");
        /*
        Usuario usuario = new Usuario();
        usuario.setNome("Ana Helena");
        usuario.setSobreNome("Silva");
        usuario.setIdade(25);
        usuario.setSexo("Feminino");
        usuarioReferencia.child("003").setValue(usuario);
        */

        /*Produto produto;

        produto = new Produto();
        produto.setDescricao("iphone");
        produto.setCor("branco");
        produto.setFabricante("Apple");
        produtoReferencia.child("001").setValue(produto);

        produto = new Produto();
        produto.setDescricao("nexus");
        produto.setCor("preto");
        produto.setFabricante("Nexus");
        produtoReferencia.child("002").setValue(produto);*/

        produtoReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("FIREBASE PRODUTO 001", dataSnapshot.getValue().toString() );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        usuarioReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("FIREBASE USUÁRIOS", dataSnapshot.getValue().toString() );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
