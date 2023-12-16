package com.cursoandroid.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context contexto;
    private SharedPreferences sharedPreferences;
    private final String NOME_ARQUIVO = "whatsapp.preferences";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";
    private final String CHAVE_NOME = "nomeUsuarioLogado";

    private String identificadorUsuarioLogado;
    private String nomeUsuarioLogado;

    public Preferencias ( Context contextoParametro ) {

        contexto = contextoParametro;
        sharedPreferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = sharedPreferences.edit();

    }

    public void salvarUsuarioPreferencias ( String identificadorUsuario, String nomeUsuario ) {

        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
        editor.putString(CHAVE_NOME, nomeUsuario);
        editor.commit();

    }

    public String getIdentificadorUsuarioLogado() {
        return sharedPreferences.getString(CHAVE_IDENTIFICADOR, null);
    }

    public String getNomeUsuarioLogado() {
        return sharedPreferences.getString(CHAVE_NOME, null);
    }

}
