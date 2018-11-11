package qualserie.com.qualserie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekbar;
    private ImageView imageExibicao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekbar = (SeekBar) findViewById(R.id.seekBarID);
        imageExibicao = (ImageView) findViewById(R.id.imageExibicaoID);


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int valorProgresso = progress;

                System.out.println(progress);

                if(valorProgresso == 1){
                    imageExibicao.setImageResource(R.drawable.pouco);
                }
                if(valorProgresso == 2){
                    imageExibicao.setImageResource(R.drawable.medio);
                }
                if(valorProgresso == 3){
                    imageExibicao.setImageResource(R.drawable.muito);
                }
                if(valorProgresso == 4){
                    imageExibicao.setImageResource(R.drawable.susto);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
