package com.example.ansariyat;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    MediaPlayer mediaPlayer;
    HashMap<String,Integer> audioTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        audioTracks = new HashMap<String, Integer>();
        audioTracks.put("أسرار التعبد بالقرآن", R.raw.asrar_taabbod_blikoran);
        audioTracks.put("صفات العبد الرباني", R.raw.sifat_alaabd_arabbani);
        audioTracks.put("سنة الاستدراج", R.raw.sonnato_al_istidraj);
        audioTracks.put("طريق النجاة و أسباب الهلاك", R.raw.tariko_najat_wa_asbabo_lhalak);

        String[] titles = audioTracks.keySet().toArray(new String[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mediaPlayer != null) {
                    mediaPlayer.release();
                }
                int id = audioTracks.get(titles[i]);
                mediaPlayer = MediaPlayer.create(getApplicationContext(), id);
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        });
    }
}