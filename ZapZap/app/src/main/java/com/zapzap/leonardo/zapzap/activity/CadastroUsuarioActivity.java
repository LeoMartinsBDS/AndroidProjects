package com.zapzap.leonardo.zapzap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.zapzap.leonardo.zapzap.R;
import com.zapzap.leonardo.zapzap.config.ConfiguracaoFirebase;
import com.zapzap.leonardo.zapzap.helper.Base64Custom;
import com.zapzap.leonardo.zapzap.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button botaoCadastrar;
    private Usuario usuario;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome = findViewById(R.id.editCadastroNome);
        email = findViewById(R.id.editCadastroEmail);
        senha = findViewById(R.id.editCadastroSenha);
        botaoCadastrar = findViewById(R.id.btnCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario = new Usuario();

                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());

                if(validateFields()){
                    cadastrarUsuario();
                }

            }
        });

    }

    private void cadastrarUsuario(){

        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
        firebaseAuth.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = task.getResult().getUser();

                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());

                    usuario.setId(identificadorUsuario);
                    usuario.salvar();

                    Toast.makeText(CadastroUsuarioActivity.this, "Sucesso ao criar usuário!", Toast.LENGTH_LONG).show();

                    com.zapzap.leonardo.zapzap.helper.SharedPreferences sharedPreferences = new com.zapzap.leonardo.zapzap.helper.SharedPreferences(CadastroUsuarioActivity.this);
                    sharedPreferences.salvarDados(identificadorUsuario, usuario.getNome());

                    abrirLoginUsuario();
                }
                else{
                    String erroExcecaoSenha = "";
                    String erroExcecaoEmail = "";
                    String erroExcecao = "";

                    Toast.makeText(CadastroUsuarioActivity.this, "Erro ao criar usuário!", Toast.LENGTH_LONG).show();
                    try{
                        throw task.getException();
                    }
                    catch(FirebaseAuthWeakPasswordException e){
                        erroExcecaoSenha = "Digite uma senha mais forte, com carecteres e números!";
                    }
                    catch(FirebaseAuthInvalidCredentialsException e){
                        erroExcecaoEmail = "E-mail digitado invalido!";
                    }
                    catch(FirebaseAuthUserCollisionException e){
                        erroExcecaoEmail = "E-mail já está sendo utilizado!";
                    }catch (Exception e) {
                        erroExcecao = "Erro ao efetuar cadastro!";
                        e.printStackTrace();
                    }

                    if(!erroExcecaoSenha.equals(""))
                        senha.setError(erroExcecao);

                    if(!erroExcecaoEmail.equals(""))
                        email.setError(erroExcecaoEmail);

                    if(!erroExcecao.equals(""))
                        Toast.makeText(CadastroUsuarioActivity.this,"erroExcecao", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private boolean validateFields(){

        boolean valid = true;

        if(usuario.getEmail().equals("")){
            email.setError("E-mail não pode ser nulo!");
            valid = false;
        }
        if(usuario.getSenha().equals("")){
            senha.setError("Senha não pode ser nulo!");
            valid = false;
        }
        if(usuario.getSenha().length() < 6){
            senha.setError("Senha deve ter pelo menos 6 caracteres!");
            valid = false;
        }

        return valid;
    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
