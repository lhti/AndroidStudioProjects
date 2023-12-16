package com.cursoandroid.whatsapp.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean verificaPermissoes (int requestCode, Activity activity, String[] permissoes) {

        if (Build.VERSION.SDK_INT >= 23) {

            List<String> listaPermissoesRequisitar = new ArrayList<>();

            /* Percorrer as permissões passadas, verificando uma a uma
             * se já tem a permissão liberada
             */
            for(String permissao : permissoes) {
                Boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if (!validaPermissao) listaPermissoesRequisitar.add(permissao);
            }

            /* Caso a lista esteja vazia, não é necessário solicitar permissão */
            if (listaPermissoesRequisitar.isEmpty()) return true;

            String[] novasPermissoes = new String[listaPermissoesRequisitar.size()];
            listaPermissoesRequisitar.toArray(novasPermissoes);

            // Solicita permissão
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);

        }

        return true;
    }

}
