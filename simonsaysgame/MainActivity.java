package e.johaneriksson.simonsaysgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    int record = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main);
        TextView recordText = findViewById(R.id.recordText);
        recordText.setText("RECORD: ");
        record = getValue();
        if (record > 0)
            recordText.setText("RECORD: " + record);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                int result = data.getIntExtra("result", 0);
                TextView recordText = findViewById(R.id.recordText);
                recordText.setText("RECORD: " + result);
                record = result;
                setValue(record);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("no result");
            }
        }
    }  // https://stackoverflow.com/questions/10407159/how-to-manage-startactivityforresult-on-android

    public void startGameActivity(View v) {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra("record",record);
        startActivityForResult(intent, 1);
    }

    public void startHighScore(View v) {
        Intent intentHighScore = new Intent(getApplicationContext(), HighScore.class);
        startActivity(intentHighScore);
    }

    public void startSettings(View v){
        TextView tv = findViewById(R.id.recordText);
        record = 0;
        tv.setText("RECORD: " + record);
    }


    public int getValue() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getInt("value_key", 0);
    }

    public void setValue(int newValue) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("value_key", newValue);
        editor.commit();
    }

}
