package com.zapzap.leonardo.zapzap.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zapzap.leonardo.zapzap.R;
import com.zapzap.leonardo.zapzap.model.Conversa;

import java.util.ArrayList;

public class ConversaAdapter extends ArrayAdapter<Conversa>{

    private ArrayList<Conversa> conversas;
    private Context context;

    public ConversaAdapter(Context context, ArrayList<Conversa> objects) {
        super(context,0, objects);
        this.conversas = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;
        //verifica se a lista esta vazia
        if (conversas != null) {

            //inicializar objeto montagem view
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //montar view
            view = layoutInflater.inflate(R.layout.lista_contato, parent, false);

            //recupera elemento para exibição
            TextView nome = view.findViewById(R.id.tv_titulo);
            TextView ultimaMensagem = view.findViewById(R.id.tv_subtitulo);

            Conversa conversa = conversas.get(position);

            nome.setText(conversa.getNome());
            ultimaMensagem.setText(conversa.getMensagem());


        }

        return view;
    }


}
