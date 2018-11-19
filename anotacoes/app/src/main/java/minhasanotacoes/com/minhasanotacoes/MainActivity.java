package minhasanotacoes.com.minhasanotacoes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText texto;
    private ImageView botaoSalvar;
    private static final String NOME_ARQUIVO = "anotacao.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (EditText) findViewById(R.id.textoID);
        botaoSalvar = (ImageView) findViewById(R.id.botaoSalvarID);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoDigitado = texto.getText().toString();
                gravarNoArquivo(textoDigitado);
                Toast.makeText(MainActivity.this, "Anotação Salva com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

        if(lerArquivo() != null){
            texto.setText(lerArquivo());
        }

    }

    private void gravarNoArquivo(String texto){

        try{

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter( openFileOutput(NOME_ARQUIVO, Context.MODE_PRIVATE));
            outputStreamWriter.write(texto);
            outputStreamWriter.append("\n\r");
            outputStreamWriter.close();

        }
        catch (IOException ex){
            Log.v("MainActivity", ex.toString());
        }
    }

    private String lerArquivo(){
        StringBuilder buf = new StringBuilder();

        try{

            InputStream arquivo = openFileInput(NOME_ARQUIVO);
            if(arquivo != null){
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


                String linhaArquivo = "";
                while( (linhaArquivo = bufferedReader.readLine()) != null){
                    buf.append(linhaArquivo);
                    buf.append('\n');
                }

                arquivo.close();

            }

        }
        catch(IOException ex){
            Log.v("MainActivity", ex.toString());
        }

        return buf.toString();
    }
}
