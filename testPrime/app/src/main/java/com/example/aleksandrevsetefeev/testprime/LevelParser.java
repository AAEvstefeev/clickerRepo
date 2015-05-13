package com.example.aleksandrevsetefeev.testprime;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class LevelParser {
    public static LevelParser instance;
    protected static Gson gson;

    private LevelParser() {
        gson = new Gson();
        instance = this;
    }

    public static ArrayList<Level> parseLevel(String json) {
        if (instance == null) {
            new LevelParser();
        }


        Type listType = new TypeToken<ArrayList<Level>>() {
        }.getType();
        ArrayList<Level> levelList = gson.fromJson(json, listType);
        return levelList;
    }

}
