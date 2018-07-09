package frasedodia.com.example.leonardo.frasedodia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textNovaFrase;
    private Button botaoNovaFrase;

    private String[] frases = {"Teste", "RIP Jajá", "Hexa 2022", "Nothing is true, everything is permitted!", "We work in the dark to serve the light", "Requiescat in Pace"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textNovaFrase = (TextView) findViewById(R.id.textoNovaFraseID);
        botaoNovaFrase = (Button) findViewById(R.id.botaoNovaFraseID);

        botaoNovaFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random randomico = new Random();

                int numeroAleatoro = randomico.nextInt(frases.length);

                textNovaFrase.setText(frases[numeroAleatoro]);
            }
        });
    }
}
