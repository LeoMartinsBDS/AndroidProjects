package com.zapzap.leonardo.zapzap.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zapzap.leonardo.zapzap.model.Contato;
import com.zapzap.leonardo.zapzap.R;

import java.util.ArrayList;

public class ContatoAdapter extends ArrayAdapter<Contato>{

    private ArrayList<Contato> contatos;
    private Context context;

    public ContatoAdapter(Context context, ArrayList<Contato> objects) {
        super(context,0, objects);
        this.contatos = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        //verifica se a lista esta vazia
        if(contatos!= null){

            //inicializar objeto montagem view
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //montar view
            view = layoutInflater.inflate(R.layout.lista_contato, parent, false);

            //recupera elemento para exibição
            TextView nomeContato = view.findViewById(R.id.tv_titulo);
            TextView emailContato = view.findViewById(R.id.tv_subtitulo);

            Contato contato = contatos.get(position);

            nomeContato.setText(contato.getNome());
            emailContato.setText(contato.getEmail());

        }
        return view;
    }
}
