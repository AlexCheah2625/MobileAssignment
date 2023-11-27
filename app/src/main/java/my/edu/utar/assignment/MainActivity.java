package my.edu.utar.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    //View the Leaderboard page
    public void Leaderboard(View view) {
        Intent intent = new Intent(this, Leaderboard.class);
        startActivity(intent);
    }

    //Start Game
    public void Play(View view) {
        Intent intent = new Intent (this, GameView.class);
        startActivity(intent);
    }
    //View Rule Page
    public void Rule(View view) {
        Intent intent = new Intent (this, Rule.class);
        startActivity(intent);
    }
    //Exit Game
    public void Exit(View view) {
        System.exit(0);
    }
}