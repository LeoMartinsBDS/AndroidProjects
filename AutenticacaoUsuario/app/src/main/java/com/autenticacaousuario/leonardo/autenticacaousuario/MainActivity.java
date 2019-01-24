package com.autenticacaousuario.leonardo.autenticacaousuario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();


        //verificando se está logado
        if(firebaseAuth.getCurrentUser() != null){
            Log.i("verificarusuario", "Usuário está logado");
        }
        else{
            Log.i("verificarusuario", "Usuário não está logado");
        }

        //deslogar usuario
        firebaseAuth.signOut();

        //login do usuario
        /*
        firebaseAuth.signInWithEmailAndPassword("leonardo.oliveira@gmail.com","123")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("Loginuser", "Sucesso ao logar usuario");
                        }
                        else{
                            Log.i("Loginuser", "Erro ao logar usuario");
                        }

                    }
                });
        */


        //cadastro do usuario
        /*
        firebaseAuth.createUserWithEmailAndPassword("leonardo.oliveira@gmail.com", "123")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Log.i("Createuser", "Sucesso ao cadastrar usuario");
                        }
                        else{
                            Log.i("Createuser", "Erro ao cadastrar usuario");
                        }

                    }
                });
                */
    }
}
