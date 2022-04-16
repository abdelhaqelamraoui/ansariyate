package com.example.ansariyat;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button buttonPrevious;
    Button buttonPause;
    Button buttonNext;
    MediaPlayer mediaPlayer;
    HashMap<String,Integer> audioTracks;
    Integer currentAudioId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        buttonPrevious = (Button) findViewById(R.id.button_previous);
        buttonPause = (Button) findViewById(R.id.button_pause);
        buttonNext = (Button) findViewById(R.id.button_next);

        audioTracks = new HashMap<String, Integer>();
        audioTracks.put("أسرار التعبد بالقرآن", R.raw.asrar_taabbod_blikoran);
        audioTracks.put("صفات العبد الرباني", R.raw.sifat_alaabd_arabbani);
        audioTracks.put("سنة الاستدراج", R.raw.sonnato_al_istidraj);
        audioTracks.put("طريق النجاة و أسباب الهلاك", R.raw.tariko_najat_wa_asbabo_lhalak);

        String[] titles = audioTracks.keySet().toArray(new String[0]);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                //context
                this,
                //layout
                R.layout.audios_rows,
                //row (view) which which holds the data to be shown
                R.id.row_text,
                //data (an Array of String)
                titles);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentAudioId = i;
                playAudioById(i);
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer == null || ! mediaPlayer.isPlaying())
                    return;
                playAudioById(--currentAudioId);
            }
        });

        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer == null)
                    return;
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    buttonPause.setText("Play");
                } else {
                    mediaPlayer.start();
                    buttonPause.setText("Pause");
                }
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer == null || ! mediaPlayer.isPlaying())
                    return;
                playAudioById(++currentAudioId);
            }
        });

    }

    private void  playAudioById(int i) {
        if(i < 0 || i > audioTracks.size() - 1)
            return;
        if(mediaPlayer != null)
            mediaPlayer.release();

        String[] titles = audioTracks.keySet().toArray(new String[0]);
        int id = audioTracks.get(titles[i]);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), id);
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
        buttonPause.setText("Pause");

    }
}