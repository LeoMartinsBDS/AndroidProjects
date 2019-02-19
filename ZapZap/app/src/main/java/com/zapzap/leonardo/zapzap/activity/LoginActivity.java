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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.zapzap.leonardo.zapzap.R;
import com.zapzap.leonardo.zapzap.config.ConfiguracaoFirebase;
import com.zapzap.leonardo.zapzap.helper.Base64Custom;
import com.zapzap.leonardo.zapzap.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText senha;
    private Button botaoLogar;
    private Usuario usuario;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    private String identificadorUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        login = findViewById(R.id.editLoginEmail);
        senha = findViewById(R.id.editLoginSenha);
        botaoLogar = findViewById(R.id.btnLogar);

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setEmail(login.getText().toString());
                usuario.setSenha(senha.getText().toString());

                if(validateFields())
                    validarLogin();
            }
        });

    }

    public void abrirCadastroUsuario(View view){
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }

    private void validarLogin(){

        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
        firebaseAuth.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){


                    identificadorUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmail());

                    databaseReference = ConfiguracaoFirebase.getFirebase()
                            .child("usuarios")
                            .child(identificadorUsuarioLogado);

                    valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Usuario usuarioRecuperado = dataSnapshot.getValue( Usuario.class);

                            com.zapzap.leonardo.zapzap.helper.SharedPreferences sharedPreferences = new com.zapzap.leonardo.zapzap.helper.SharedPreferences(LoginActivity.this);
                            sharedPreferences.salvarDados(identificadorUsuarioLogado, usuarioRecuperado.getNome());



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };

                    databaseReference.addListenerForSingleValueEvent(valueEventListener);

                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this,"Sucesso ao realizar login!", Toast.LENGTH_LONG).show();
                }
                else{

                    try{
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidCredentialsException invalid){
                        Toast.makeText(LoginActivity.this,"Usuário ou senha estão inválidos", Toast.LENGTH_LONG).show();
                        requireFields();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void verificarUsuarioLogado(){

        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();

        if(firebaseAuth.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    private boolean validateFields(){

        boolean valid = true;

        if(usuario.getEmail().equals("")){
            login.setError("Por favor, informe seu e-mail!");
            valid = false;
        }
        if(usuario.getSenha().equals("")){
            senha.setError("Por favor, informe sua senha!");
            valid = false;
        }


        return valid;
    }

    private void requireFields(){

        login.setError("Por favor, verifique seu e-mail!");
        senha.setError("Por favor, verifique sua senha!");
    }
}
