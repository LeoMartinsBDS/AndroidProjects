package com.parse.starter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.starter.R;
import com.parse.starter.util.ParseErrors;

public class CadastroActivity extends AppCompatActivity {

    private EditText nomeUsuario;
    private EditText email;
    private EditText senha;
    private Button btnCadastro;
    private TextView facaLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nomeUsuario = (EditText) findViewById(R.id.editTextUsuario);
        email = (EditText) findViewById(R.id.editTextEmail);
        senha = (EditText) findViewById(R.id.editTextSenha);
        btnCadastro = (Button) findViewById(R.id.btnCadastrar);
        facaLogin = (TextView) findViewById(R.id.textFacaLogin);

        facaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirLoginUsuario();
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarUsuario();
            }
        });

    }

    public void cadastrarUsuario(){

        ParseUser usuario = new ParseUser();
        usuario.setUsername(nomeUsuario.getText().toString());
        usuario.setEmail(email.getText().toString());
        usuario.setPassword(senha.getText().toString());

        usuario.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if( e == null){
                    Toast.makeText(CadastroActivity.this, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show();
                    abrirLoginUsuario();
                }
                else{
                    ParseErrors parseErrors = new ParseErrors();
                    String erro = parseErrors.getErro(e.getCode());
                    Toast.makeText(CadastroActivity.this, "Erro: " + erro, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public  void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
