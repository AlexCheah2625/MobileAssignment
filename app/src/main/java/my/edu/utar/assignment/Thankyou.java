package my.edu.utar.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Thankyou extends AppCompatActivity {
    //Global Variables
    private TextView tv;
    private int points;
    private String name;
    private LeaderboardManager leaderboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyoupage);

        points = getIntent().getExtras().getInt("score");
        name = getIntent().getExtras().getString("username");

        tv=findViewById(R.id.pointstxttq);
        tv.setText("Points Earned: "+ String.valueOf(points));

        leaderboardManager = new LeaderboardManager();

        // Load leaderboard data from the SharedPreference
        leaderboardManager.loadLeaderboardData(getSharedPreferences("Points", MODE_PRIVATE));

        // Save the user's score to the SharedPreference
        leaderboardManager.updateLeaderboard(points, name);

        // Sort the leaderboard data based on scores from teh SharedPreference
        leaderboardManager.sortLeaderboardData();

        // Save the updated leaderboard data to SharedPreferences
        leaderboardManager.saveLeaderboardData(getSharedPreferences("Points", MODE_PRIVATE));

        // Display the leaderboard
        displayLeaderboard();
    }


    public void home(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void displayLeaderboard() {
        // Display the leaderboard in your TableLayout
        TableLayout tableLayout = findViewById(R.id.leaderboard);
        int maxRow = LeaderboardManager.maxRow;

        for (int i = 0; i < maxRow; i++) {
            TableRow ranking = new TableRow(this);

            TextView rankingTextView = new TextView(this);
            TextView rankingName = new TextView(this);
            TextView rankingPoint = new TextView(this);

            //Display and format the text that is being displayed
            rankingTextView.setText(String.valueOf(i + 1));
            rankingTextView.setTextColor(getResources().getColor(R.color.white));
            rankingTextView.setTextSize(18);
            rankingTextView.setTypeface(ResourcesCompat.getFont(this, R.font.baloo_bhaijaan));

            rankingName.setText(LeaderboardManager.getRankingNameList()[i]);
            rankingName.setTextColor(getResources().getColor(R.color.white));
            rankingName.setTextSize(18);
            rankingName.setTypeface(ResourcesCompat.getFont(this, R.font.baloo_bhaijaan));

            rankingPoint.setText(String.valueOf(LeaderboardManager.getRankingPointList()[i]));
            rankingPoint.setTextColor(getResources().getColor(R.color.white));
            rankingPoint.setTextSize(18);
            rankingPoint.setTypeface(ResourcesCompat.getFont(this, R.font.baloo_bhaijaan));

            ranking.addView(rankingTextView);
            ranking.addView(rankingName);
            ranking.addView(rankingPoint);

            tableLayout.addView(ranking);
        }
    }
}
