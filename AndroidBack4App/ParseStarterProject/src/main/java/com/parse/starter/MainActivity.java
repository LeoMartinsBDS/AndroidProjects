/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    //CADASTRO DE USUARIOS
      /*
      ParseUser user = new ParseUser();
      user.setUsername("LeoMartins");
      user.setPassword("123456");
      user.setEmail("leoteste@teste.com");

    //CADASTRAR
      user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
              if(e == null){
                  Log.i("cadastroUsuario", "Sucesso ao cadastrar usuário");
              }
              else{
                  Log.i("cadastroUsuario", "Não foi possível realizar o login " + e.getMessage());
              }
          }
      });
      */

     //DESLOGAR USUARIO
     /* ParseUser.logOut();

    //VERIFICAR USUARIO LOGADO
      if(ParseUser.getCurrentUser() != null){
            Log.i("usuarioLogado", "O usuário está logado");
      }
    */

     //FAZER LOGIN DO USUÁRIO
      ParseUser.logInInBackground("LeoMartins", "123456", new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
              if(e == null){
                  Log.i("usuarioLogado", "Usuario " + user.getUsername());
              }
              else{
                  Log.i("usuarioLogado", "Erro ao logar usuario " + e.getMessage());
              }
          }
      });

    //INSERÇÃO
    /*
      ParseObject pontuacao = new ParseObject("Pontuacao");
      pontuacao.put("nome", "Mario");
      pontuacao.put("pontos", 60);
      pontuacao.saveInBackground(new SaveCallback() {
          @Override
          public void done(ParseException e) {
              if(e == null){
                  Log.i("salvarPontos", "Dados salvos com sucesso");
              }
              else{
                  Log.i("salvarPontos", "Erro ao salvar dados");
              }
          }
      });
     */

    //ALTERAÇÃO
    /*
      ParseQuery<ParseObject> consulta =  ParseQuery.getQuery("Pontuacao");
      consulta.getInBackground("ZzWp5N7IRx", new GetCallback<ParseObject>() {
          @Override
          public void done(ParseObject object, ParseException e) {
              if(e == null){
                  object.put("pontos", 2000);

                  object.saveInBackground();
              }
              else{
                  Log.i("consultarPontos", "Não foi possível realizar consulta");
              }
          }
      });
    */

      //Listar os dados
      ParseQuery<ParseObject> filtro = ParseQuery.getQuery("Pontuacao");

      //aplicando filtro
      filtro.whereGreaterThan("pontos", 100);

      filtro.findInBackground(new FindCallback<ParseObject>() {
          @Override
          public void done(List<ParseObject> objects, ParseException e) {
              if(e == null){
                    for(ParseObject object : objects){
                        Log.i("consultarPontosFiltro", "Objetos " + object.get("nome") + " pontos: " + object.get("pontos"));
                    }
              }
              else{
                  Log.i("consultarPontosFiltro", "Não foi possível realizar consulta" + e.getMessage());
              }
          }
      });

  }

}
