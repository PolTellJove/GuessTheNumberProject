package com.example.guessthenumber;

import java.io.File;
import java.io.FileOutputStream;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String imageName;
    private String imagePath;
    private AlertDialog viewRanking;
    private Button rankingButton;
    private EditText textNumber;
    private EditText textRanking;
    private Chronometer timer;
    private int time;
    private int correctNumber;
    private int introducedNumber;
    private int attempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = findViewById(R.id.timer);
        textNumber = findViewById(R.id.introducedNumber);
        rankingButton = findViewById(R.id.btnViewRanking);
        winningDialog();
        restart();
        final Button validate = findViewById(R.id.btnCheckNumber);

        //When the button is pressed the validation gets activated
        validate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                validation();
                //The text field gets empty again
                textNumber.setText("");

            }

        });

        //Shows the ranking
        rankingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                saveGame("");

            }

        });

    }

    //Tells if the number is correct, is higher or is lower
    private void validation(){

        introducedNumber = Integer.parseInt(textNumber.getText().toString());
        attempts++;

        //If is correct
        if (introducedNumber==correctNumber){

            Toast.makeText(this, "Great! You guessed the number " + correctNumber + " in "+ attempts + " attempts!", Toast.LENGTH_SHORT).show();
            timer.stop();
            time = (int) (SystemClock.elapsedRealtime() - timer.getBase())/1000;
            imageName = createImageName();
            takeImage();
            showRanking();

        }

        //If the correct number is lower
        else if (introducedNumber>correctNumber){

            Toast.makeText(this, "The correct number is lower.", Toast.LENGTH_SHORT).show();

        }

        //If the correct number is higher
        else{

            Toast.makeText(this, "The correct number is higher.", Toast.LENGTH_SHORT).show();

        }

    }

    //Saves the game
    private void saveGame(String name){

        Intent intent = new Intent(this, RankingActivity.class);
        imagePath = getFilesDir().getPath() +"/"+ imageName + ".png";

        if(name != "") {

            RankingActivity.players.add(new Player(imagePath, name, attempts, time));

        }

        startActivity(intent);

    }

    //Shows the ranking
    private void showRanking(){

        viewRanking.show();
        viewRanking.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = textRanking.getText().toString();
                viewRanking.dismiss();
                saveGame(name);
                restart();

            }

        });

    }

    //The dialog shown when you guess a number
    private void winningDialog(){

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("You guessed the number!");
        adb.setMessage("Introduce your name");
        textRanking = new EditText(this);
        adb.setView(textRanking);

        adb.setPositiveButton("GO ON", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

            }

        });

        adb.setNegativeButton("GO BACK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                restart();

            }

        });

        viewRanking = adb.create();

    }

    //Takes an image
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private void takeImage() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        } catch (ActivityNotFoundException e) {

        }

    }

    //Creates a random name for the image
    public String createImageName(){

        Long timeLong = System.currentTimeMillis()/1000;
        String created=timeLong.toString();
        return created;

    }

    //Saves the image
    public void saveImage(Bitmap bitmap) {

        File saveImageFile = new File(getApplicationContext().getFilesDir(), imageName + ".png");
        try {

            FileOutputStream fileOutputStream = new FileOutputStream(saveImageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(), "Image " + imageName + " saved in " + getApplicationContext().getFilesDir(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "ERROR: The image can not be saved!", Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            saveImage(imageBitmap);

        }

    }

    //The attempts are reset and number is randomized to start a new game
    private void restart(){
        time = 0;
        attempts = 0;
        correctNumber = (int)(Math.random()*100)+1;
        textRanking.setText("");
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
    }

}