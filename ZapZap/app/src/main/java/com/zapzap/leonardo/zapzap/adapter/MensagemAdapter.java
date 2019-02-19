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
import com.zapzap.leonardo.zapzap.helper.SharedPreferences;
import com.zapzap.leonardo.zapzap.model.Mensagem;

import java.util.ArrayList;

public class MensagemAdapter extends ArrayAdapter<Mensagem> {

    private Context context;
    private ArrayList<Mensagem> mensagens;

    public MensagemAdapter(@NonNull Context c, @NonNull ArrayList<Mensagem> objects) {
        super(c, 0, objects);
        this.context = c;
        this.mensagens = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if( mensagens != null){

            //dados usuario remetente
            SharedPreferences sharedPreferences = new SharedPreferences(context);
            String idUsuarioRemetente = sharedPreferences.getIdentificador();


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            Mensagem mensagem = mensagens.get(position);

            if(idUsuarioRemetente.equals(mensagem.getIdUsuario())){
                view = inflater.inflate(R.layout.item_mensagem_direita, parent, false);
            }
            else{
                view = inflater.inflate(R.layout.item_mensagem_esquerda, parent, false);
            }

            //recupera elemento para exibiçao
            TextView textoMensagem = view.findViewById(R.id.tv_mensagem);
            textoMensagem.setText(mensagem.getMensagem());

        }

        return view;
    };
}
