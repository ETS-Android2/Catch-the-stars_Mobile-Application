package com.example.catch_the_ball;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;

public class resultActivity extends AppCompatActivity {

    private int score;
    private String name;
    public ArrayList<String> keepUser;
    public ArrayList<Integer> keepScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_result);

        TextView scoreLabel=(TextView)findViewById(R.id.scoreLabel);

        score = getIntent().getIntExtra("SCORE",0);
        scoreLabel.setText(score+"");
        readFile();

        final Button enterName = (Button) findViewById(R.id.entername);
        enterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterName.setEnabled(false);

                writeFile();
                readFile();

            }
        });


    }

    public void readFile(){
        String filename = "score_record.txt";
        ArrayList<String> keepData = new ArrayList<>();
        keepUser = new ArrayList<>();
        keepScore = new ArrayList<>();

        try {

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(openFileInput(filename)));
            String inputString;

            while((inputString = inputReader.readLine()) != null){
                keepData.add(inputString);
            }

            for (int i = 0; i < keepData.size() ; i++) {
                keepUser.add(keepData.get(i).split(":")[0]);
                keepScore.add(Integer.parseInt(keepData.get(i).split(":")[1]));
                System.out.println(keepUser.get(i)+" "+keepScore.get(i));
            }

            int a;
            String b;

            //sort descending loop
            for (int i = 0; i < keepScore.size(); ++i)
            {
                for (int j = i + 1; j < keepScore.size(); ++j)
                {
                    if (keepScore.get(i) < keepScore.get(j))
                    {
                        a = keepScore.get(i);
                        keepScore.set(i, keepScore.get(j));
                        keepScore.set(j, a);

                        b = keepUser.get(i);
                        keepUser.set(i, keepUser.get(j));
                        keepUser.set(j, b);
                    }
                }
            }

            while(keepUser.size() > 3){
                keepUser.remove(3);
                keepScore.remove(3);
            }

            for(String i : keepUser)
                System.out.println(i);

            if(keepUser.size() == 1){

                TextView top1 = (TextView) findViewById(R.id.userTop1);
                TextView top1score = (TextView) findViewById(R.id.userTop1Score);
                top1.setText(keepUser.get(0));
                top1score.setText(String.valueOf(keepScore.get(0)));

            }else if(keepUser.size() == 2){

                TextView top1 = (TextView) findViewById(R.id.userTop1);
                TextView top1score = (TextView) findViewById(R.id.userTop1Score);
                top1.setText(keepUser.get(0));
                top1score.setText(String.valueOf(keepScore.get(0)));

                TextView top2 = (TextView) findViewById(R.id.userTop2);
                TextView top2score = (TextView) findViewById(R.id.userTop2Score);
                top2.setText(keepUser.get(1));
                top2score.setText(String.valueOf(keepScore.get(1)));

            }else if(keepUser.size() == 3){

                TextView top1 = (TextView) findViewById(R.id.userTop1);
                TextView top1score = (TextView) findViewById(R.id.userTop1Score);
                top1.setText(keepUser.get(0));
                top1score.setText(String.valueOf(keepScore.get(0)));

                TextView top2 = (TextView) findViewById(R.id.userTop2);
                TextView top2score = (TextView) findViewById(R.id.userTop2Score);
                top2.setText(keepUser.get(1));
                top2score.setText(String.valueOf(keepScore.get(1)));

                TextView top3 = (TextView) findViewById(R.id.userTop3);
                TextView top3score = (TextView) findViewById(R.id.userTop3Score);
                top3.setText(keepUser.get(2));
                top3score.setText(String.valueOf(keepScore.get(2)));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void writeFile(){
        EditText username = (EditText) findViewById(R.id.enterName);
        name = username.getText().toString();

        FileOutputStream outputStream;
        String filename = "score_record.txt";
        String userwithscore = name + ":" + score + "\n";

        try {
            outputStream = openFileOutput(filename, MODE_APPEND);
            outputStream.write(userwithscore.getBytes());
            outputStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void tryAgain(View view) {
        Intent intent=new Intent(getApplicationContext(),startActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
