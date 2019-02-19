package com.zapzap.leonardo.zapzap.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.zapzap.leonardo.zapzap.model.Contato;
import com.zapzap.leonardo.zapzap.R;
import com.zapzap.leonardo.zapzap.activity.ConversaActivity;
import com.zapzap.leonardo.zapzap.adapter.ContatoAdapter;
import com.zapzap.leonardo.zapzap.config.ConfiguracaoFirebase;
import com.zapzap.leonardo.zapzap.helper.SharedPreferences;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Contato> contatos;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListenerContatos;

    public ContatosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(valueEventListenerContatos);
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(valueEventListenerContatos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //instancia de objetos
        contatos = new ArrayList<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        //monta lista view e adapter
        listView = (ListView)view.findViewById(R.id.lv_contatos);
        adapter = new ContatoAdapter(getActivity(), contatos);

        listView.setAdapter(adapter);


        //recuperar dados do firebase
        SharedPreferences sharedPreferences = new SharedPreferences(getActivity());
        String identificadorUsuarioLogado =sharedPreferences.getIdentificador();

        databaseReference = ConfiguracaoFirebase.getFirebase()
                            .child("contatos")
                            .child(identificadorUsuarioLogado);

        //lister para recuperar contatos
        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Limpar Lista
                contatos.clear();

                //Listar contatos
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato);
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
                Contato contato = contatos.get(position);

                //enviando dados para activity
                intent.putExtra("nome", contato.getNome());
                intent.putExtra("email", contato.getEmail());

                startActivity(intent);

            }
        });

        return view;
    }

}
