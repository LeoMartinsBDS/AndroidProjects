package listadetarefas.com.listadetarefas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText textoTarefa;
    private Button botaoAdicionar;
    private ListView listaTarefas;
    private SQLiteDatabase bancoDados;

    private ArrayAdapter<String> itensAdaptador;
    private ArrayList<String> itens;
    private ArrayList<Integer> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            textoTarefa = findViewById(R.id.textoID);
            botaoAdicionar =  findViewById(R.id.botaoAddID);

            //lista
            listaTarefas =  findViewById(R.id.listviewID);

            //bd
            bancoDados = openOrCreateDatabase("appTarefas", MODE_PRIVATE, null);

            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tarefas (id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR)");

            botaoAdicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String textoDigitado = textoTarefa.getText().toString();

                    if(!textoDigitado.equals("")){
                        salvarTarefa(textoDigitado);
                        Toast.makeText(MainActivity.this, "Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show();
                        loadBD();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Digite uma tarefa!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            listaTarefas.setLongClickable(true);
            listaTarefas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    removerTarefa(ids.get(position));
                    loadBD();
                    return true;
                }
            });

            loadBD();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }

    private void salvarTarefa(String texto){

        try {
            bancoDados.execSQL("INSERT INTO TAREFAS (tarefa) VALUES ('" + texto + "')");
            textoTarefa.setText("");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }

    private void loadBD(){

        try {
            Cursor cursor = bancoDados.rawQuery("SELECT * FROM tarefas ORDER BY id DESC", null);

            //recupera os ids das colunas
            int indiceColunaID = cursor.getColumnIndex("id");
            int indiceColunaTarefa = cursor.getColumnIndex("tarefa");

            //adapter
            itens = new ArrayList<String>();
            ids = new ArrayList<Integer>();
            itensAdaptador = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    itens);

            listaTarefas.setAdapter(itensAdaptador);

            //listar
            cursor.moveToFirst();

            while (cursor != null) {

                itens.add(cursor.getString(indiceColunaTarefa));
                ids.add(Integer.parseInt(cursor.getString(indiceColunaID)));
                cursor.moveToNext();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void removerTarefa(Integer id){

        try{
            bancoDados.execSQL("DELETE FROM tarefas WHERE id ="+id);
            Toast.makeText(MainActivity.this, "Tarefa removida com sucesso!", Toast.LENGTH_SHORT).show();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
