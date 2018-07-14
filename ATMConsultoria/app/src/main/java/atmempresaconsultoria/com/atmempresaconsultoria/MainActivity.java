package atmempresaconsultoria.com.atmempresaconsultoria;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView botaoEmpresa;
    private ImageView botaoServico;
    private ImageView botaoCliente;
    private ImageView botaoContato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        botaoEmpresa = (ImageView) findViewById(R.id.empresaID);

        botaoEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, empresaActivity.class));
            }
        });

        botaoServico = (ImageView) findViewById(R.id.servicoID);

        botaoServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ServicoActivity.class));
            }
        });

        botaoCliente = (ImageView) findViewById(R.id.clientesID);

        botaoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ClienteActivity.class));
            }
        });

        botaoContato = (ImageView) findViewById(R.id.contatoID);

        botaoContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ContatoActivity.class));
            }
        });
    }
}
