package com.zapzap.leonardo.zapzap.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.zapzap.leonardo.zapzap.R;
import com.zapzap.leonardo.zapzap.adapter.TabAdapter;
import com.zapzap.leonardo.zapzap.config.ConfiguracaoFirebase;
import com.zapzap.leonardo.zapzap.helper.Base64Custom;
import com.zapzap.leonardo.zapzap.helper.SharedPreferences;
import com.zapzap.leonardo.zapzap.helper.SlidingTabLayout;
import com.zapzap.leonardo.zapzap.model.Contato;
import com.zapzap.leonardo.zapzap.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private Toolbar toolbar;
    private ListView lvConversas;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private String identificadorContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ZapZap");
        toolbar.setTitle("ZapZap");
        setSupportActionBar(toolbar);

        slidingTabLayout = findViewById(R.id.sl_tabs);
        viewPager = findViewById(R.id.vpPagina);

        //configurar sliding tab
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));

        //Configurar adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);



        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_configuracoes:
                return true;
            case R.id.item_add:
                adicionarUsuario();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void adicionarUsuario(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        alertDialog.setTitle("Novo contato");
        alertDialog.setMessage("E-mail do usuário");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(MainActivity.this);
        alertDialog.setView(editText);

        //config botoes
        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String emailContato = editText.getText().toString();

                if(emailContato.isEmpty()){
                    Toast.makeText(MainActivity.this,"Preencha o e-mail", Toast.LENGTH_LONG).show();
                }
                else{

                    //verificando se o usuario já está cadastrado
                    identificadorContato = Base64Custom.codificarBase64(emailContato);

                    //recuperar instancia firebase
                    databaseReference = ConfiguracaoFirebase.getFirebase().child("usuarios").child(identificadorContato);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.getValue() != null){

                                //Recuperar dados do contato a ser adicionado
                                Usuario usuario = dataSnapshot.getValue(Usuario.class);


                                SharedPreferences sharedPreferences = new SharedPreferences(MainActivity.this);
                                //Recuperar identificador usuario logado
                                String identificadorUsuarioLogado = sharedPreferences.getIdentificador();
                                firebaseAuth.getCurrentUser().getEmail();

                                databaseReference = ConfiguracaoFirebase.getFirebase();

                                databaseReference = databaseReference.child("contatos")
                                        .child(identificadorUsuarioLogado)
                                        .child(identificadorContato);

                                Contato contato = new Contato();
                                contato.setIdentificadorUsuario(identificadorContato);
                                contato.setEmail(usuario.getEmail());
                                contato.setNome(usuario.getNome());

                                databaseReference.setValue(contato);

                            }
                            else{
                                Toast.makeText(MainActivity.this,"O usuário informado não possui cadastro.", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.create();
        alertDialog.show();

    }

    private void deslogarUsuario(){

        firebaseAuth.signOut();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
