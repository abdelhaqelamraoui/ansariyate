package com.example.ansariyat;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ImageButton buttonPrevious;
    ImageButton buttonPause;
    ImageButton buttonNext;
    LinearLayout linearLayout;
    LinearLayout lastSelectedItem;
    MediaPlayer mediaPlayer;
    HashMap<String,Integer> audioTracks;
    Integer currentAudioId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED); // set screen orientation to portrait
        listView = (ListView) findViewById(R.id.listView);
        buttonPrevious = findViewById(R.id.button_previous);
        buttonPause = (ImageButton) findViewById(R.id.button_pause);
        buttonNext = (ImageButton) findViewById(R.id.button_next);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        buttonPrevious.setImageResource(android.R.drawable.ic_media_previous);
        buttonPause.setImageResource(android.R.drawable.ic_media_play);
        buttonNext.setImageResource(android.R.drawable.ic_media_next);


        audioTracks = new HashMap<>();
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
                if(mediaPlayer == null || ! mediaPlayer.isPlaying() || currentAudioId == 0)
                    return;
                playAudioById(--currentAudioId);
            }
        });

        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer == null) {
                    playAudioById(0);
                    return;
                }
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    buttonPause.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    mediaPlayer.start();
                    buttonPause.setImageResource(android.R.drawable.ic_media_pause);
                }
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer == null || ! mediaPlayer.isPlaying() || currentAudioId == audioTracks.size() - 1)
                    return;
                playAudioById(++currentAudioId);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.suggest:
                Snackbar.make(linearLayout, "Suggest", Snackbar.LENGTH_LONG).show();

                return true;
            case R.id.about:
                Snackbar.make(linearLayout, "About", Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.rateUs:
                Snackbar.make(linearLayout, "RateUs", Snackbar.LENGTH_SHORT).show();
                return true;
        }
        return true;
    }



    private void  playAudioById(int i) {
        if(i < 0 || i > audioTracks.size() - 1)
            return;
        if(mediaPlayer != null)
            mediaPlayer.release();

        if(lastSelectedItem != null)
            lastSelectedItem.setBackgroundColor(getResources().getColor(R.color.listItem));
        LinearLayout ll = (LinearLayout) listView.getChildAt(i);
        ll.setBackgroundColor(getResources().getColor(R.color.selecteditem));
        lastSelectedItem = ll;

        String[] titles = audioTracks.keySet().toArray(new String[0]);
        int id = audioTracks.get(titles[i]);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), id);
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
        buttonPause.setImageResource(android.R.drawable.ic_media_pause);

    }
}