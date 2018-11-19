package somdosbichos.com.somdosbichos;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView cao;
    private ImageView gato;
    private ImageView ovelha;
    private ImageView macaco;
    private ImageView leao;
    private ImageView urso;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cao = (ImageView) findViewById(R.id.caoID);
        gato = (ImageView) findViewById(R.id.gatoID);
        macaco = (ImageView) findViewById(R.id.macacoID);
        ovelha = (ImageView) findViewById(R.id.ovelhaID);
        urso = (ImageView) findViewById(R.id.ursoID);
        leao = (ImageView) findViewById(R.id.leaoID);

        cao.setOnClickListener(this);
        gato.setOnClickListener(this);
        macaco.setOnClickListener(this);
        ovelha.setOnClickListener(this);
        urso.setOnClickListener(this);
        leao.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

       switch (v.getId()){
           case R.id.caoID:
               mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.cao);
               tocarSom();
               break;
           case R.id.gatoID:
               mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.gato);
               tocarSom();
               break;
           case R.id.macacoID:
               mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.macaco);
               tocarSom();
               break;
           case R.id.ovelhaID:
               mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.ovelha);
               tocarSom();
               break;
           case R.id.ursoID:
               mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.urso );
               tocarSom();
               break;
           case R.id.leaoID:
               mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.leao);
               tocarSom();
               break;
       }

    }

    public void tocarSom(){
        if(mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {

        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }

        super.onDestroy();
    }

}
