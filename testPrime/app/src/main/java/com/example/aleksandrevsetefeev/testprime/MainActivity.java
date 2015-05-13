package com.example.aleksandrevsetefeev.testprime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private Context context;
    private int score = 0;
    private int currentLevel = 1;
    private int scorePerClick;
    private int scoreToNextLevel;

    private ArrayList<Level> levelList;
    private String levelJSON;

    private TextView scoreView;
    private TextView currentLevelView;
    private TextView animateTextView;
    private Button clickButton;
    private Button levelMenuButton;
    private RelativeLayout mainLayout;

    private Animation animationFade;

    public static int randomInt(int Min, int Max) {
        return (int) (Math.random() * (Max - Min)) + Min;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
        score = preferenceManager.getInt("score", 0);
        currentLevel = preferenceManager.getInt("currentLevel", 1);

        setContentView(R.layout.activity_main);

        initViews();
        setScore();
        setLevel();

        loadLevel(this);

    }

    protected void initViews() {
        animationFade = AnimationUtils.loadAnimation(this, R.anim.fade);

        clickButton = (Button) findViewById(R.id.click_button);
        levelMenuButton = (Button) findViewById(R.id.level_menu_button);
        scoreView = (TextView) findViewById(R.id.score_textview);
        currentLevelView = (TextView) findViewById(R.id.level_textview);
        animateTextView = (TextView) findViewById(R.id.animateTextView);
        mainLayout = (RelativeLayout) findViewById(R.id.main_layout);

        animateTextView.setVisibility(View.GONE);

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMainButton();
            }
        });
        levelMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLevelActivity();
            }
        });
    }

    protected void clickMainButton() {
        score += scorePerClick;
        setScore();
        if (score >= scoreToNextLevel) {

            if (currentLevel < levelList.size() - 1) {
                currentLevel++;
                setDate();
                setLevel();
                nextLevelAnimation();
            } else {
                openWinDialog();
            }
        }
    }

    protected int getWidthSreen() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) dpWidth;
    }

    protected int getHeightSreen() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        return (int) dpHeight;
    }

    protected void nextLevelAnimation() {

        int leftMargin = randomInt(0, getWidthSreen());
        RelativeLayout.LayoutParams viewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        viewParams.setMargins(leftMargin, getHeightSreen() / 2, 0, 0);

        animateTextView.setLayoutParams(viewParams);

        int scoreOver = levelList.get(currentLevel - 1).getScoreToNextLevel();
        String overScoreText = "+" + Integer.toString(scoreOver);
        animateTextView.setText(overScoreText);

        animateTextView.setVisibility(View.VISIBLE);
        animateTextView.startAnimation(animationFade);
    }

    protected void openWinDialog() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.win))
                .setMessage(getString(R.string.restart_game))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        score = 0;
                        currentLevel = 1;
                        setDate();
                        setScore();
                        setLevel();
                    }

                })

                .show();
    }

    protected void setScore() {
        if (scoreView != null) {
            scoreView.setText(getString(R.string.score) + Integer.toString(score));
        }
    }

    protected void setLevel() {
        if (currentLevelView != null) {
            currentLevelView.setText(getString(R.string.level) + Integer.toString(currentLevel));
        }
    }

    protected void setDate() {
        scorePerClick = levelList.get(currentLevel - 1).getScorePerClick();
        scoreToNextLevel = levelList.get(currentLevel).getScoreToNextLevel();
    }

    public void setLevels(String json) {
        levelJSON = json;
        levelList = LevelParser.parseLevel(json);
        setDate();
    }

    public void showLouder() {
        mainLayout.setVisibility(View.GONE);
    }

    public void hideLouder() {
        mainLayout.setVisibility(View.VISIBLE);
    }

    protected void loadLevel(MainActivity mainActivity) {
        LevelLouder levelLouder = new LevelLouder(mainActivity);
        levelLouder.execute();
    }

    protected void openLevelActivity() {
        Intent levelActivityIntent = new Intent(this, LevelActivity.class);
        levelActivityIntent.putExtra("levelJSON", levelJSON);
        context.startActivity(levelActivityIntent);
    }

    protected void onDestroy() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("score", score);
        editor.putInt("currentLevel", currentLevel);
        editor.commit();

        super.onDestroy();
    }


}
