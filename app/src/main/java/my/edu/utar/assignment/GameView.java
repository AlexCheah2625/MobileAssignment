package my.edu.utar.assignment;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class GameView extends AppCompatActivity {

    //Global Variables
    private ImageView[][] imageViews = new ImageView[5][3];
    private int[][] moonPositions = new int[5][3];
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 0;
    private int difficultyLevel = 0;
    private int changePositionInterval = 3000;
    private Handler handler = new Handler();
    private Random random = new Random();
    private boolean gameRunning = true;
    private TextView timeTextView;
    private int score = 0;
    private TextView scoreTextView;
    private TextView lvl_tv;
    private int numberOfMoons = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);
        // Initialize ImageView and TextView
        imageViews[0][0] = findViewById(R.id.img11);
        imageViews[0][1] = findViewById(R.id.img12);
        imageViews[0][2] = findViewById(R.id.img13);
        imageViews[1][0] = findViewById(R.id.img21);
        imageViews[1][1] = findViewById(R.id.img22);
        imageViews[1][2] = findViewById(R.id.img23);
        imageViews[2][0] = findViewById(R.id.img31);
        imageViews[2][1] = findViewById(R.id.img32);
        imageViews[2][2] = findViewById(R.id.img33);
        imageViews[3][0] = findViewById(R.id.img41);
        imageViews[3][1] = findViewById(R.id.img42);
        imageViews[3][2] = findViewById(R.id.img43);
        imageViews[4][0] = findViewById(R.id.img51);
        imageViews[4][1] = findViewById(R.id.img52);
        imageViews[4][2] = findViewById(R.id.img53);

        timeTextView = findViewById(R.id.timetxt);
        scoreTextView = findViewById(R.id.scoretxt);
        lvl_tv = findViewById(R.id.leveltxt);

        // Start the countdown timer
        startTimer();
        // Start Game
        startChangingPosition();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                difficultyLevel++;
                startNewStage();
            }
        }.start();
    }

    private void updateCountdownText() {
        // Format the time and display the time at the textview
        long minutes = timeLeftInMillis / 60000;
        long seconds = (timeLeftInMillis % 60000) / 1000;
        String timeFormatted = String.format(Locale.getDefault(), "Time: %02d:%02d", minutes, seconds);
        timeTextView.setText(timeFormatted);
    }

    private void startNewStage() {
        lvl_tv.setText("Level " + difficultyLevel);

        gameRunning = false;
        //clear all the image on the screen
        resetImageViews();

        // Before entering a stage delay 5 seconds to display the level the user in and then start the game
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lvl_tv.setText("");
                timeLeftInMillis = 5000;
                startTimer();
                updateDifficulty();
                updateMoonPositions();
            }
        }, 5000);
    }

    private void resetImageViews() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                imageViews[i][j].setImageResource(0);
            }
        }
    }

    private void updateDifficulty() {
        // Increase the frequency of changing the position of the moon based on difficulty level
        switch (difficultyLevel) {
            case 1:
                changePositionInterval = 3000;
                break;
            case 2:
                changePositionInterval = 2000;
                break;
            case 3:
                changePositionInterval = 1500;
                break;
            default:
                changePositionInterval = 1000;
                break;
        }
    }

    private void updateMoonPositions() {
        resetMoonPositions(); // Reset moon positions

        if (difficultyLevel == 1) {
            //Ensure that there is 1 moon in the first level
            placeMoon();
        } else {
            //Increases the number of moon images when the level increases
            int numberOfMoons = difficultyLevel;
            for (int i = 0; i < numberOfMoons; i++) {
                placeMoon();
            }
        }
    }

    private void resetMoonPositions() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                imageViews[i][j].setImageResource(0);
            }
        }
    }

    private void placeMoon() {
        int row, column;
        int attempts = 0;
        while (attempts <= 20) {
            row = random.nextInt(5);
            column = random.nextInt(3);

            if (moonPositions[row][column] == 0) {
                // If the position is not occupied, mark it as occupied by a moon
                moonPositions[row][column] = 1;
                setCorrectImage(row, column, R.drawable.moon);
                break;
            }

            attempts++;
        }
    }

    private void setCorrectImage(int row, int column, int resource) {
        // Set the image to the specified ImageView
        imageViews[row][column].setImageResource(resource);
    }

    //when the moon is clicked it will change its position
    private void startChangingPosition() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changePosition();
                handler.postDelayed(this, changePositionInterval);
            }
        }, changePositionInterval);
    }

    private void changePosition() {
        if (gameRunning) {
            // Change the position of all moons
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 3; j++) {
                    if (moonPositions[i][j] == 1) {
                        setRandomImage(i, j);
                    }
                }
            }
            handler.postDelayed(() -> changePosition(), changePositionInterval);
        }
    }

    private void setRandomImage(int row, int column) {
        int newRandomRow, newRandomColumn;
        int attempts = 0;

        do {
            newRandomRow = random.nextInt(5);
            newRandomColumn = random.nextInt(3);
            attempts++;

            // Limit the number of attempts to avoid infinite loop
            if (attempts > 20) {
                break;
            }
        } while (moonPositions[newRandomRow][newRandomColumn] == 1);

        if (attempts <= 20) {
            // Clear the previous moon position
            imageViews[row][column].setImageResource(0);
            moonPositions[row][column] = 0;
        }
    }

    //To determine which image view was pressed
    public void onImageViewClicked(View view) {
        int clickedRow = -1, clickedColumn = -1;
        outerLoop:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                if (imageViews[i][j] == view) {
                    clickedRow = i;
                    clickedColumn = j;
                    break outerLoop;
                }
            }
        }
        handleImageClick(clickedRow, clickedColumn);
    }

    //The score will be updated when the imageview is pressed
    private void handleImageClick(int clickedRow, int clickedColumn) {
        if (moonPositions[clickedRow][clickedColumn] == 1) {
            // Correct image clicked, update the game
            // For example, increase the score, play a sound, etc.
            imageViews[clickedRow][clickedColumn].setImageResource(0);
            moonPositions[clickedRow][clickedColumn] = 0;

            score += 10;
            updateScoreText();
            placeMoon();
        } else {
            gameOver();
        }
    }

    //Display the updated score
    private void updateScoreText() {
        scoreTextView.setText("Score: " + score);
    }

    private void gameOver() {
        // Check if the game is already over
        gameRunning = false;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        handler.removeCallbacksAndMessages(null);
        //if the user loose the game under level 3 it will prompt the game over dialog
        if (difficultyLevel < 3) {
            showGameOverDialog();
        } else {
            //if the user looses the game more than level 3 it will let the user enter
            //their name to the leaderboard
            thankyou();
        }

    }

    private void showGameOverDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(R.layout.gameover);

        TextView scoreText = dialog.findViewById(R.id.pointstxt);
        Button retryButton = dialog.findViewById(R.id.retrybtn);
        Button homeButton = dialog.findViewById(R.id.homebtn);

        //display the points earned by the user
        scoreText.setText("Points Earned: " + score);

        // if the user press the retry button the game will start at level 1
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficultyLevel = 1;
                score = 0;
                updateDifficulty();
                updateMoonPositions();
                updateScoreText();
                timeLeftInMillis = 5000;
                startTimer();
                startChangingPosition();
                dialog.dismiss();
            }
        });

        //bring the user to the home page
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameView.this, MainActivity.class);
                startActivity(intent);
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }

    public void thankyou() {
        //bring the user to the other page to let them enter their name to the leaderboard
        Intent inputname = new Intent(GameView.this, NameInput.class);
        inputname.putExtra("score", score);
        startActivity(inputname);
    }

}