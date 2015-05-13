package com.example.aleksandrevsetefeev.testprime;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class LevelLouder extends AsyncTask<Void, Void, Void> {
    MainActivity mainActivity;

    LevelLouder(MainActivity _mainActivity) {
        mainActivity = _mainActivity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mainActivity.showLouder();
    }

    private String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {


        String json = null;
        String urlString = "http://kuala-lumpur-avenue-4449.pancakeapps.com/level.json";

        try {
            json = readUrl(urlString);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (json == null) {
            json = "[" +
                    "{\"scorePerClick\":1,\"scoreToNextLevel\":0}," +
                    "{\"scorePerClick\":2,\"scoreToNextLevel\":12}," +
                    "{\"scorePerClick\":5,\"scoreToNextLevel\":30}," +
                    "{\"scorePerClick\":7,\"scoreToNextLevel\":70}," +
                    "{\"scorePerClick\":10,\"scoreToNextLevel\":150}," +
                    "{\"scorePerClick\":12,\"scoreToNextLevel\":250}," +
                    "{\"scorePerClick\":15,\"scoreToNextLevel\":350}," +
                    "{\"scorePerClick\":20,\"scoreToNextLevel\":500}," +
                    "{\"scorePerClick\":24,\"scoreToNextLevel\":700}," +
                    "{\"scorePerClick\":27,\"scoreToNextLevel\":1000}," +
                    "{\"scorePerClick\":31,\"scoreToNextLevel\":1500}," +
                    "{\"scorePerClick\":32,\"scoreToNextLevel\":2300}," +
                    "{\"scorePerClick\":35,\"scoreToNextLevel\":3000}" +
                    "]";


        }

        mainActivity.setLevels(json);


        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        mainActivity.hideLouder();
    }

}
