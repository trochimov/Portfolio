package e.johaneriksson.simonsaysgame;
// ref http://abhiandroid.com/ui/arrayadapter-tutorial-example.html

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HighScore extends AppCompatActivity {
    ListView simpleList;
    String highScoreList[] = {"1.","2.","3.","4.","5.","6.","7.","8.","9.","10."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        simpleList = findViewById(R.id.highscore_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.high_score_text_view, highScoreList);
        simpleList.setAdapter(arrayAdapter);
    }

}

