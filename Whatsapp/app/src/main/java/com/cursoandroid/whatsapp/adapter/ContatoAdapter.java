package com.cursoandroid.whatsapp.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.cursoandroid.whatsapp.R;
import com.cursoandroid.whatsapp.model.Contato;

public class ContatoAdapter extends ArrayAdapter<Contato> {

    private ArrayList<Contato> contatos;
    private Context context;

    public ContatoAdapter(@NonNull Context c, @NonNull ArrayList<Contato> objects) {
        super(c, 0, objects);

        this.contatos = objects;
        this.context = c;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if (contatos != null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_padrao, parent, false);

            TextView titulo = (TextView) view.findViewById(R.id.tv_titulo);
            TextView subTitulo = (TextView) view.findViewById(R.id.tv_subtitulo);

            Contato contato = contatos.get(position);
            titulo.setText(contato.getNome());
            subTitulo.setText(contato.getEmail());

        }

        return view;
    }
}
