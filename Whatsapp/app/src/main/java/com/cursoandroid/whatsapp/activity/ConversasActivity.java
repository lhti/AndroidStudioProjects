package com.cursoandroid.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.cursoandroid.whatsapp.R;
import com.cursoandroid.whatsapp.adapter.MensagemAdapter;
import com.cursoandroid.whatsapp.config.ConfiguracaoFirebase;
import com.cursoandroid.whatsapp.helper.Base64Custom;
import com.cursoandroid.whatsapp.helper.Preferencias;
import com.cursoandroid.whatsapp.model.Conversa;
import com.cursoandroid.whatsapp.model.Mensagem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConversasActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editMensagem;
    private ImageButton btMensagem;
    private ListView listView;

    private String idUsuarioDestinatario;
    private String nomeUsuarioDestinatario;

    private String idUsuarioLogado;
    private String nomeUsuarioLogado;

    private DatabaseReference firebase;

    private ArrayList<Mensagem> mensagens;
    private MensagemAdapter adapter;
    private ValueEventListener valueEventListenerMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversas);

        toolbar = (Toolbar) findViewById(R.id.tb_conversa);
        editMensagem = (EditText) findViewById(R.id.edit_mensagem);
        btMensagem = (ImageButton) findViewById(R.id.bt_enviar);
        listView = (ListView) findViewById(R.id.lv_conversa);

        // Dados do remetente: usuário logado
        Preferencias preferencias = new Preferencias(ConversasActivity.this);
        idUsuarioLogado = preferencias.getIdentificadorUsuarioLogado();
        nomeUsuarioLogado = preferencias.getNomeUsuarioLogado();

        // Dados do destinario: contato
        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            nomeUsuarioDestinatario = extra.getString("nome");
            String email = extra.getString("email");
            idUsuarioDestinatario = Base64Custom.codificarBase64(email);
        }

        // Configura toolbar
        toolbar.setTitle(nomeUsuarioDestinatario);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        // Envia mensagem
        btMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoMensagem = editMensagem.getText().toString();

                if (textoMensagem.isEmpty()) {
                    Toast.makeText(ConversasActivity.this, "Digite uma mensagem para enviar", Toast.LENGTH_SHORT).show();
                } else {

                    // salvar mensagem para o remetente
                    Boolean retornoMensagemRemetente = salvarMensagem(idUsuarioLogado, idUsuarioDestinatario, textoMensagem);
                    if (!retornoMensagemRemetente) {
                        Toast.makeText(ConversasActivity.this,
                                         "Problema ao salvar mensagem, tente novamente",
                                         Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        // salvar mensagem para o destinatário
                        Boolean retornoMensagemDestinatario = salvarMensagem(idUsuarioDestinatario, idUsuarioLogado, textoMensagem);
                        if (!retornoMensagemDestinatario) {
                            Toast.makeText(ConversasActivity.this,
                                            "Problema ao enviar mensagem para o destinatário, tente novamente",
                                             Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    // salvar conversa para o remetente
                    Conversa conversa = new Conversa();
                    conversa.setIdUsuario(idUsuarioDestinatario);
                    conversa.setNome(nomeUsuarioDestinatario);
                    conversa.setMensagem(textoMensagem);

                    Boolean retornoConversaRemente = salvarConversa(idUsuarioLogado, idUsuarioDestinatario, conversa);
                    if (!retornoConversaRemente) {
                        Toast.makeText(ConversasActivity.this,
                                        "Problema ao salvar conversa, tente novamente",
                                        Toast.LENGTH_SHORT
                        ).show();
                    } else {

                        // salvar conversa para o destinatário
                        conversa = new Conversa();
                        conversa.setIdUsuario(idUsuarioLogado);
                        conversa.setNome(nomeUsuarioLogado);
                        conversa.setMensagem(textoMensagem);

                        Boolean retornoConversaDestinatario = salvarConversa(idUsuarioDestinatario, idUsuarioLogado, conversa);
                        if (!retornoConversaDestinatario) {
                            Toast.makeText(ConversasActivity.this,
                                            "Problema ao salvar conversa, tente novamente",
                                            Toast.LENGTH_SHORT
                            ).show();
                        }
                    }


                    editMensagem.setText("");
                }

            }
        });

        mensagens = new ArrayList<>();
        adapter = new MensagemAdapter(ConversasActivity.this, mensagens);
        listView.setAdapter(adapter);

        // Cria o listener para mensagens
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebase()
                                                         .child("Mensagens")
                                                         .child(idUsuarioLogado)
                                                         .child(idUsuarioDestinatario);

        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mensagens.clear();

                for (DataSnapshot m : dataSnapshot.getChildren()) {
                    Mensagem mensagem = m.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        firebase.addValueEventListener(valueEventListenerMensagem);

    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerMensagem);
    }

    private Boolean salvarMensagem(String idRemetente, String idDestinatario, String textoMensagem) {

        try {

            Mensagem mensagem = new Mensagem();
            mensagem.setIdUsuario(idUsuarioLogado);
            mensagem.setMensagem(textoMensagem);

            firebase = ConfiguracaoFirebase.getFirebase().child("Mensagens");

            firebase.child(idRemetente)
                    .child(idDestinatario)
                    .push()
                    .setValue(mensagem);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private Boolean salvarConversa(String idRemetente, String idDestinatario, Conversa conversa) {

        try {
            firebase = ConfiguracaoFirebase.getFirebase().child("Conversas");

            firebase.child(idRemetente)
                    .child(idDestinatario)
                    .setValue(conversa);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
