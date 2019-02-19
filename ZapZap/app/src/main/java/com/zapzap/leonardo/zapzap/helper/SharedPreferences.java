package com.zapzap.leonardo.zapzap.helper;

import android.content.Context;
import android.util.Log;

public class SharedPreferences {

    private Context contexto;
    private android.content.SharedPreferences preferences;
    private final String NOME_ARQUIVO = "zapPreference";
    private final int MODE = 0;
    private android.content.SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";
    private final String CHAVE_NOME = "nomeUsuarioLogado";


    public SharedPreferences(Context contextoParametro){

        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();

    }

    public void cleanShared(Context context){

        contexto = context;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        preferences.edit().clear().commit();

    }

    public void salvarDados(String identificadorUsuario, String nomeUsuario){

        try {
            editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
            editor.putString(CHAVE_NOME, nomeUsuario);
            editor.commit();
        }
        catch (Exception ex){
            Log.d("ERRORSHARED", ex.toString());
        }

    }


    public String getIdentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }

    public String getNomeUsuario(){
        return preferences.getString(CHAVE_NOME, null);
    }



}
