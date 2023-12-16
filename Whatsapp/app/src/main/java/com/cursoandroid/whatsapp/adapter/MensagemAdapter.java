package com.cursoandroid.whatsapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cursoandroid.whatsapp.R;
import com.cursoandroid.whatsapp.helper.Preferencias;
import com.cursoandroid.whatsapp.model.Mensagem;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MensagemAdapter extends ArrayAdapter<Mensagem> {

    private ArrayList<Mensagem> mensagens;
    private Context context;

    private String idUsuarioLogado;

    public MensagemAdapter(@NonNull Context context, @NonNull ArrayList<Mensagem> objects) {
        super(context, 0, objects);

        this.mensagens = objects;
        this.context = context;

        Preferencias preferencias = new Preferencias(context);
        idUsuarioLogado = preferencias.getIdentificadorUsuarioLogado();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if (mensagens != null) {

            Mensagem mensagem = mensagens.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (mensagem.getIdUsuario().equals(idUsuarioLogado)) {
                view = inflater.inflate(R.layout.lista_mensagem_usuario_logado, parent, false);
            } else {
                view = inflater.inflate(R.layout.lista_mensagem_contato, parent, false);
            }

            TextView textoMensagem = (TextView) view.findViewById(R.id.tv_mensagem);
            textoMensagem.setText(mensagem.getMensagem());

        }

        return view;
    }
}
