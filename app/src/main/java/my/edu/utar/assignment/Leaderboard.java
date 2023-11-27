package my.edu.utar.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Leaderboard extends AppCompatActivity {

    private LeaderboardManager leaderboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        leaderboardManager = new LeaderboardManager();

        // Load leaderboard data from the SharedPreference
        leaderboardManager.loadLeaderboardData(getSharedPreferences("Points", MODE_PRIVATE));

        // Sort the leaderboard data based on scores from the SharedPreference
        leaderboardManager.sortLeaderboardData();

        // Save the updated leaderboard data to SharedPreferences
        leaderboardManager.saveLeaderboardData(getSharedPreferences("Points", MODE_PRIVATE));
        displayLeaderboard();
    }

    private void displayLeaderboard() {
        TableLayout tableLayout = findViewById(R.id.leaderboardtable);
        int maxRow = LeaderboardManager.maxRow;

        for (int i = 0; i < maxRow; i++) {
            TableRow ranking = new TableRow(this);

            TextView rankingTextView = new TextView(this);
            TextView rankingName = new TextView(this);
            TextView rankingPoint = new TextView(this);

            // Display and format the data that is going to be display
            rankingTextView.setText(String.valueOf(i + 1));
            rankingTextView.setTextColor(Color.parseColor("#001244"));
            rankingTextView.setPadding(40,0,0,0);
            rankingTextView.setTextSize(18);
            rankingTextView.setTypeface(ResourcesCompat.getFont(this, R.font.baloo_bhaijaan));

            rankingName.setText(LeaderboardManager.getRankingNameList()[i]);
            rankingName.setTextColor(Color.parseColor("#001244"));
            rankingName.setPadding(30,0,0,0);
            rankingName.setTextSize(18);
            rankingName.setTypeface(ResourcesCompat.getFont(this, R.font.baloo_bhaijaan));

            rankingPoint.setText(String.valueOf(LeaderboardManager.getRankingPointList()[i]));
            rankingPoint.setTextColor(Color.parseColor("#001244"));
            rankingPoint.setTextSize(18);
            rankingPoint.setTypeface(ResourcesCompat.getFont(this, R.font.baloo_bhaijaan));

            ranking.addView(rankingTextView);
            ranking.addView(rankingName);
            ranking.addView(rankingPoint);

            tableLayout.addView(ranking);
        }
    }


}