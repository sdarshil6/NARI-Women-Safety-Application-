package com.company.currentlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FakeCallActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    ImageView fake_call;
//    AudioManager audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_call);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        mediaPlayer = MediaPlayer.create(FakeCallActivity.this, R.raw.ringtone);

        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.stop();
            }
        });


        fake_call=findViewById(R.id.imageView);

        fake_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                fake_call.setImageResource(R.color.black);
//                audio = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//                audio.setRingerMode(0);
            }
        });
    }

    @Override
    protected void onPause() {

        super.onPause();
        mediaPlayer.stop();
//        audio.setRingerMode(1);

    }
}