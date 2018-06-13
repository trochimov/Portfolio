package e.johaneriksson.simonsaysgame;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    GameState state;
    Boolean success;
    Handler handler = new Handler();
    private int round = 0;
    private int result = 0;
    private int clickCount = 0;
    private int counter = 0;
    private int record;
    private int[] computerArray = new int[0];
    private int[] playerArray = new int[0];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        loadButtons();
        getRecord();
        state = GameState.COMPUTERTURN;
        playGame();
    }

    public void getRecord(){
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            record = extras.getInt("record");
    }


    public void playGame(){

        TextView textStatus = findViewById(R.id.textStatus);

        switch (state){

            case COMPUTERTURN:
                textStatus.setText("LISTEN");
                resetGame();
                handler.postDelayed(this::playRounds, 1000);
                break;

            case PLAYERTURN:
                textStatus.setText("PLAY");
                break;

            case GAMEOVER:
                Intent returnIntent = new Intent();;
                returnIntent.putExtra("result",result);
                if (result > record) {
                    setResult(Activity.RESULT_OK, returnIntent);
                    setToast("NEW RECORD");
                    handler.postDelayed(this::finish,1000);
                }
                else {
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    setToast("GAME OVER");
                    handler.postDelayed(this::finish,1000);
                }
                break;
        }
    }

    public void playRounds(){
        playButton();
        round++;
    }

    public void playButton() {
        final Button btnGreen = findViewById(R.id.btnGreen);
        final Button btnBlue = findViewById(R.id.btnBlue);
        final Button btnOrange = findViewById(R.id.btnOrange);
        final Button btnRed = findViewById(R.id.btnRed);

        computerArray = addNumbersArr(computerArray);
        int random = computerArray[round];

            switch (random) {

                case 1:
                    btnGreen.setPressed(true);
                    btnGreen.performClick();
                    handler.postDelayed(this::unPress,500);
                    break;

                case 2:
                    btnBlue.setPressed(true);
                    btnBlue.performClick();
                    handler.postDelayed(this::unPress,500);
                    break;

                case 3:
                    btnOrange.setPressed(true);
                    btnOrange.performClick();
                    handler.postDelayed(this::unPress,500);
                    break;

                case 4:
                    btnRed.setPressed(true);
                    btnRed.performClick();
                    handler.postDelayed(this::unPress,500);
                    break;

            }

            if (round < counter)
                handler.postDelayed(this::playRounds, 1000);
            else {
                state = GameState.PLAYERTURN;
                playGame();
            }
        }


    public void loadButtons() {

        Button btnGreen = findViewById(R.id.btnGreen);
        Button btnBlue = findViewById(R.id.btnBlue);
        Button btnOrange = findViewById(R.id.btnOrange);
        Button btnRed = findViewById(R.id.btnRed);

        final MediaPlayer mpGreen = MediaPlayer.create(this, R.raw.piano_a);
        final MediaPlayer mpBlue = MediaPlayer.create(this, R.raw.piano_b);
        final MediaPlayer mpOrange = MediaPlayer.create(this, R.raw.piano_c);
        final MediaPlayer mpRed = MediaPlayer.create(this, R.raw.piano_d);


        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpGreen.start();

                if (state == GameState.PLAYERTURN) {
                    playerArray = addNumbersArrPlayer(playerArray, 1);
                    playerPlays();
                }
            }});

        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpBlue.start();

                if (state == GameState.PLAYERTURN) {
                    playerArray = addNumbersArrPlayer(playerArray, 2);
                    playerPlays();
                }
            }});

        btnOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpOrange.start();

                if (state == GameState.PLAYERTURN) {
                    playerArray = addNumbersArrPlayer(playerArray, 3);
                    playerPlays();
                }
            }});

        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpRed.start();

                if (state == GameState.PLAYERTURN) {
                    playerArray = addNumbersArrPlayer(playerArray, 4);
                    playerPlays();
                }
            }});
    }

    public void playerPlays(){
        update();
        compareArrays(computerArray,playerArray);
        clickCount++;
        System.out.println(success);
        System.out.println(Integer.toString(round));
        System.out.println(Integer.toString(clickCount));
        if (clickCount == round && success){
            state = GameState.COMPUTERTURN;
            playGame();
        }
    }

    public void update(){
        TextView textScore = findViewById(R.id.textScore);
        result++;
        String res = Integer.toString(result);
        textScore.setText(res);

    }

    public int[] addNumbersArr(int[] array){
        array = Arrays.copyOf(array, array.length + 1);
        Random rand = new Random();
        int random = rand.nextInt(4) + 1;
        array[array.length-1] = random;
        return array;
    }

    public int[] addNumbersArrPlayer(int[] array, int input){
        array = Arrays.copyOf(array, array.length + 1);
        array[array.length-1] = input;
        return array;
    }

    public void compareArrays(int[] arr1, int[] arr2){
        int[] tempArr1;
        int[] tempArr2;

            for (int j = 0; j < arr2.length; j++) {
                for (int i = 0; i < 1 + j; i++) {
                    int value = -1 + i;
                    tempArr1 = Arrays.copyOf(arr1, 2 + value);
                    tempArr2 = Arrays.copyOf(arr2, 1 + i);

                    System.out.println(Arrays.toString(tempArr1));
                    System.out.println(Arrays.toString(tempArr2));

                    if (Arrays.equals(tempArr1, tempArr2)) {
                        System.out.println("same");
                        success = true;
                    }
                    if (!Arrays.equals(tempArr1, tempArr2)) {
                        System.out.println("not same");
                        success = false;
                        state = GameState.GAMEOVER;
                        playGame();
                    }
                }
            }
        }

    /* arr1 är computerArray som får sin längd baserat på antal rundor
        arr2 är playerArray som vid varje knapptryck ökar längd.
        Översta forsatsen snurrar lika många gånger som playerArrays längd,
        under satsen snurrar lika många gånger som arr2 längden plus 1.
        temp arreyerna kortar av in arrayerna för att sedan jämnföras.

     */

    public void unPress(){
        final Button btnGreen = findViewById(R.id.btnGreen);
        final Button btnBlue = findViewById(R.id.btnBlue);
        final Button btnOrange = findViewById(R.id.btnOrange);
        final Button btnRed = findViewById(R.id.btnRed);

        btnGreen.setPressed(false);
        btnBlue.setPressed(false);
        btnOrange.setPressed(false);
        btnRed.setPressed(false);
    }

    public void resetGame(){
        playerArray = Arrays.copyOf(playerArray, 0);
        computerArray = Arrays.copyOf(computerArray, 0);
        round = 0;
        clickCount = 0;
        counter++;
    }

    public void setToast(String msg){
        Toast toast= Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0, 0);
        toast.show();
    }
}
