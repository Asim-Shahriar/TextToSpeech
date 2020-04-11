package com.example.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private Button sayAudio;
    private SeekBar seekPitch,seekSpeed;
    private TextToSpeech mTTS;
    private TextView tv_audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input=findViewById(R.id.edit_text);
        sayAudio=findViewById(R.id.btn_audio);
        seekPitch=findViewById(R.id.seek_bar_pitch);
        seekSpeed=findViewById(R.id.seek_bar_speed);
        tv_audio=findViewById(R.id.tv);

        mTTS=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
              if(status==TextToSpeech.SUCCESS){
                  int language=mTTS.setLanguage(Locale.ENGLISH);
                  if(language==TextToSpeech.LANG_MISSING_DATA || language==TextToSpeech.LANG_NOT_SUPPORTED){
                      Log.i("TTS", "Language not supported");
                  }else{
                      Log.i("TTS", "Language  supported");
                  }
              }else{
                  Toast.makeText(getApplicationContext(),"TTS Initialization failed!",Toast.LENGTH_SHORT).show();
              }
            }
        });

        sayAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTTS!=null){
          mTTS.stop();
          mTTS.shutdown();

        }
    }

    private void speak() {
        String text=input.getText().toString().trim();
        float pitch=seekPitch.getProgress()/50;
        if(pitch<0.1f)
            pitch=0.1f;
        float speed=seekSpeed.getProgress()/50;
        if(speed<0.1f)
            speed=0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text,TextToSpeech.QUEUE_FLUSH,null);

    }

}
