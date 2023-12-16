package com.cursoandroid.whatsapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cursoandroid.whatsapp.R;
import com.cursoandroid.whatsapp.activity.ConversasActivity;
import com.cursoandroid.whatsapp.adapter.ContatoAdapter;
import com.cursoandroid.whatsapp.adapter.ConversaAdapter;
import com.cursoandroid.whatsapp.config.ConfiguracaoFirebase;
import com.cursoandroid.whatsapp.helper.Base64Custom;
import com.cursoandroid.whatsapp.helper.Preferencias;
import com.cursoandroid.whatsapp.model.Contato;
import com.cursoandroid.whatsapp.model.Conversa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {

    private ListView listView;
    private ConversaAdapter adapter;
    private ArrayList<Conversa> listaConversas;

    private ValueEventListener valueEventListenerConversas;

    private DatabaseReference firebase;

    public ConversasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerConversas);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerConversas);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        listaConversas = new ArrayList<>();

        adapter = new ConversaAdapter(getActivity(), listaConversas);
        listView = (ListView) view.findViewById(R.id.lv_conversas);
        listView.setAdapter(adapter);

        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorUsuarioLogado = preferencias.getIdentificadorUsuarioLogado();

        firebase = ConfiguracaoFirebase.getFirebase()
                                       .child("Conversas")
                                       .child(identificadorUsuarioLogado);

        valueEventListenerConversas = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaConversas.clear();

                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    Conversa conversa = item.getValue(Conversa.class);
                    listaConversas.add(conversa);

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ConversasActivity.class);

                Conversa conversa = listaConversas.get(i);

                //Enviando dados para activity conversas
                intent.putExtra("nome", conversa.getNome());
                intent.putExtra("email", Base64Custom.decodificarBase64(conversa.getIdUsuario()));

                startActivity(intent);
            }
        });

        return view;
    }

}
