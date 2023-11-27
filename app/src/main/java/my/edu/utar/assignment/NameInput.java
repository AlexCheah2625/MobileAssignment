package my.edu.utar.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NameInput extends AppCompatActivity {

    private TextView score;
    private EditText name;
    private int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namesubmit);

        score = findViewById(R.id.scoretxt);
        name = findViewById(R.id.nameinput);

        //get the score data from the intent and display the score
        points = getIntent().getExtras().getInt("score");
        score.setText(String.valueOf(points));
    }

    //Let the user submit their name along with their score to the next page
    public void Submit(View view) {
        String username = name.getText().toString();
        Intent intentusername = new Intent (this, Thankyou.class);
        intentusername.putExtra("score", points);
        intentusername.putExtra("username", username);
        startActivity(intentusername);
    }
}