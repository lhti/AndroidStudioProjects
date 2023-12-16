package com.cursoandroid.whatsapp.config;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class ConfiguracaoFirebase {

    private static DatabaseReference referenceDatabase;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFirebase() {

        if (referenceDatabase == null) {
            referenceDatabase = FirebaseDatabase.getInstance().getReference();
        }

        return referenceDatabase;
    }

    public static FirebaseAuth getFirebaseAutenticacao() {

        if (autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }

        return autenticacao;
    }
}
