package gasolinaoualcool.com.example.leonardo.gasolinaoualcool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText precoAlcool;
    private EditText precoGasolina;
    private Button botaoVerificar;
    private TextView textoResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        precoAlcool = (EditText) findViewById(R.id.precoAlcoolID);
        precoGasolina = (EditText) findViewById(R.id.precoGasolinaID);
        botaoVerificar = (Button) findViewById(R.id.botaoVerificarID);
        textoResultado = (TextView) findViewById(R.id.textResultadoID);


        botaoVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoPrecoAlccol = precoAlcool.getText().toString();
                String textoPrecoGasolina = precoGasolina.getText().toString();

                Double valorAlcool = Double.parseDouble(textoPrecoAlccol);
                Double valorGasolina = Double.parseDouble(textoPrecoGasolina);

                Double resultado = valorAlcool / valorGasolina;

                if(resultado >= 0.7){
                    textoResultado.setText("Compensa utilizar a GASOLINA");
                }
                else{
                    textoResultado.setText("Compensa utilizar o ALCOOL");
                }

            }
        });

    }
}
