package com.example.aleksandrevsetefeev.testprime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;


public class LevelActivity extends Activity {
    ArrayList<Level> levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Intent intent = getIntent();
        String json = intent.getExtras().getString("levelJSON");
        levels = LevelParser.parseLevel(json);
        LevelTableAdapter levelTableAdapter = new LevelTableAdapter(this, levels);

        // настраиваем список
        ListView levelList = (ListView) findViewById(R.id.listLevel);
        levelList.setAdapter(levelTableAdapter);
    }


}
