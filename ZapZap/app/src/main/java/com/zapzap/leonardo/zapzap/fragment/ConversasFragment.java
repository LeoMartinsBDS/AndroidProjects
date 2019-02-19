package com.zapzap.leonardo.zapzap.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.zapzap.leonardo.zapzap.R;
import com.zapzap.leonardo.zapzap.activity.ConversaActivity;
import com.zapzap.leonardo.zapzap.adapter.ConversaAdapter;
import com.zapzap.leonardo.zapzap.config.ConfiguracaoFirebase;
import com.zapzap.leonardo.zapzap.helper.Base64Custom;
import com.zapzap.leonardo.zapzap.helper.SharedPreferences;
import com.zapzap.leonardo.zapzap.model.Conversa;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Conversa> adapter;
    private ArrayList<Conversa> conversas;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListenerConversas;


    public ConversasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(valueEventListenerConversas);
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(valueEventListenerConversas);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        conversas = new ArrayList<>();
        listView = view.findViewById(R.id.lv_conversas);
        adapter = new ConversaAdapter(getActivity(), conversas);

        listView.setAdapter(adapter);

        //recuperar dados do firebase
        SharedPreferences sharedPreferences = new SharedPreferences(getActivity());
        String identificadorUsuarioLogado = sharedPreferences.getIdentificador();

        databaseReference = ConfiguracaoFirebase.getFirebase()
                .child("conversas")
                .child(identificadorUsuarioLogado);

        //lister para recuperar conversas
        valueEventListenerConversas = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Limpar Lista
                conversas.clear();

                //Listar contatos
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Conversa conversa = dados.getValue(Conversa.class);
                    conversas.add(conversa);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                //recupera dados a serem passados
                Conversa conversa = conversas.get(position);

                //enviando dados para activity
                intent.putExtra("nome", conversa.getNome());

                String email = Base64Custom.decodificarBase64(conversa.getIdUsuario());
                Log.d("EMAIL", email);

                intent.putExtra("email", email);

                startActivity(intent);

            }
        });


        return view;
    }

}
