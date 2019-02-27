package com.parse.starter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.starter.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editUsuario;
    private EditText editSenha;
    private Button botaoLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsuario = (EditText) findViewById(R.id.editTextLoginUsuario);
        editSenha = (EditText) findViewById(R.id.editTextLoginSenha);
        botaoLogar = (Button) findViewById(R.id.btnLogar);

        //verificar se o usuario esta logado
        verificarUsuarioLogado();
        //ParseUser.logOut();

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = editUsuario.getText().toString();
                String senha = editSenha.getText().toString();

                verificarLogin(usuario, senha);
            }
        });
    }

    public  void abrirCadastroUsuario(View view){
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    private void verificarLogin(String usuario, String senha){
        ParseUser.logInInBackground(usuario, senha, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null){
                    Toast.makeText(LoginActivity.this, "Sucesso ao logar!", Toast.LENGTH_SHORT).show();
                    abrirAreaPrincipal();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Erro ao logar! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verificarUsuarioLogado(){
        if(ParseUser.getCurrentUser() != null){
            abrirAreaPrincipal();
        }
    }

    private void abrirAreaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
