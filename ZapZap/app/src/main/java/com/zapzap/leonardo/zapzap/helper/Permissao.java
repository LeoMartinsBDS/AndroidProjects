package com.zapzap.leonardo.zapzap.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validaPermissoes(int requestCode, Activity activity, String [] permissoes){

        if(Build.VERSION.SDK_INT >= 23){

            List<String> listaPermissoes = new ArrayList<String>();

            /*PERCORRE AS PERMISSÕES PASSADAS, VERIFICANDO UMA A UMA
            * SE JÁ TEM A PERMISSÃO LIBERADA*/
            for(String permissao: permissoes){

                boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;

                if(!validaPermissao){
                    listaPermissoes.add(permissao);
                }

            }

            if(listaPermissoes.isEmpty()){
                return true;
            }

            //convertendo array de string
            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            //solicitar permissao
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);

        }

        return true;
    }

}
