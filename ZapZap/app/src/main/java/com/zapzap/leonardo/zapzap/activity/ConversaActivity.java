package com.zapzap.leonardo.zapzap.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.zapzap.leonardo.zapzap.R;
import com.zapzap.leonardo.zapzap.adapter.MensagemAdapter;
import com.zapzap.leonardo.zapzap.config.ConfiguracaoFirebase;
import com.zapzap.leonardo.zapzap.helper.Base64Custom;
import com.zapzap.leonardo.zapzap.helper.SharedPreferences;
import com.zapzap.leonardo.zapzap.model.Conversa;
import com.zapzap.leonardo.zapzap.model.Mensagem;

import java.util.ArrayList;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editMensagem;
    private ImageButton btnEnviar;
    private DatabaseReference databaseReference;
    private ListView listView;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;
    private ValueEventListener valueEventListenerMensagem;

    //destinatario
    private String nomeUsuarioDestinatario;
    private String idUsuarioDestinatario;


    //remetente
    private String idUsuarioRemetente;
    private String nomeUsuarioRemetente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = findViewById(R.id.tb_conversa);
        editMensagem = findViewById(R.id.edit_messages);
        btnEnviar = findViewById(R.id.btnEnviarMensagem);
        listView = findViewById(R.id.lv_conversas);

        //dados usuario logado
        SharedPreferences sharedPreferences = new SharedPreferences(ConversaActivity.this);
        idUsuarioRemetente = sharedPreferences.getIdentificador();
        nomeUsuarioRemetente = sharedPreferences.getNomeUsuario();


        //dados remetente
        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            nomeUsuarioDestinatario = bundle.getString("nome");
            idUsuarioDestinatario = bundle.getString("email");
        }

        idUsuarioDestinatario = Base64Custom.codificarBase64(idUsuarioDestinatario);

        toolbar.setTitle(nomeUsuarioDestinatario);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        //monta listview e adapter
        mensagens = new ArrayList<>();
        adapter = new MensagemAdapter(ConversaActivity.this, mensagens);

        listView.setAdapter(adapter);

        //recuperar mensagens firebase
        databaseReference = ConfiguracaoFirebase.getFirebase()
                            .child("mensagens")
                            .child(idUsuarioRemetente)
                            .child(idUsuarioDestinatario);

        //listener para mensagens
        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mensagens.clear();

                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Mensagem mensagem = dados.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        databaseReference.addValueEventListener(valueEventListenerMensagem);

        //enviar mensagem
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoMensagem = editMensagem.getText().toString();

                if(!textoMensagem.isEmpty()){
                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(idUsuarioRemetente);
                    mensagem.setMensagem(textoMensagem);

                    //salvar para remetente
                    Boolean retornoMensagemRemetente = salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario , mensagem);
                    if(!retornoMensagemRemetente){
                        Toast.makeText(ConversaActivity.this, "Problema ao salvar mensagem, tente novamente", Toast.LENGTH_LONG).show();
                    }
                    else{
                        //salvar para destinatario
                        Boolean retornoMensagemDestinatario = salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente , mensagem);
                        if(!retornoMensagemDestinatario){
                            Toast.makeText(ConversaActivity.this, "Problema ao salvar mensagem, tente novamente", Toast.LENGTH_LONG).show();
                        }
                    }

                    //salvando conversa remetente
                    Conversa conversa = new Conversa();
                    conversa.setIdUsuario(idUsuarioDestinatario);
                    conversa.setMensagem(textoMensagem);
                    conversa.setNome(nomeUsuarioDestinatario);

                    Boolean retornoConversaRemetente = salvarConversa(idUsuarioRemetente, idUsuarioDestinatario,conversa);
                    if(!retornoConversaRemetente){
                        Toast.makeText(ConversaActivity.this, "Problema ao salvar conversa, tente novamente", Toast.LENGTH_LONG).show();
                    }
                    else{
                        //salvar conversa destinatario

                        Conversa conversaDestinatario = new Conversa();
                        conversaDestinatario.setIdUsuario(idUsuarioRemetente);
                        conversaDestinatario.setNome(nomeUsuarioRemetente);
                        conversaDestinatario.setMensagem(textoMensagem);

                        Boolean retornoConversaDestinatario = salvarConversa(idUsuarioDestinatario, idUsuarioRemetente,conversa);
                        if(!retornoConversaDestinatario){
                            Toast.makeText(ConversaActivity.this, "Problema ao salvar conversa para o destinatario, tente novamente", Toast.LENGTH_LONG).show();
                        }
                    }

                    editMensagem.setText("");

                }

            }
        });

    }

    private boolean salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagens){
        try{

            databaseReference = ConfiguracaoFirebase.getFirebase().child("mensagens");

            databaseReference.child(idRemetente)
                    .child(idDestinatario)
                    .push()
                    .setValue(mensagens);

            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    private boolean salvarConversa(String idRemetente, String idDestinatario, Conversa conversa){
        try {
            databaseReference = ConfiguracaoFirebase.getFirebase().child("conversas");
            databaseReference.child(idRemetente)
                                .child(idDestinatario)
                                .setValue(conversa);

            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener(valueEventListenerMensagem);
    }
}

