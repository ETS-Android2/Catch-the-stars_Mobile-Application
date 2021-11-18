package com.example.catch_the_ball;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LeaderBoard extends AppCompatActivity {

    public ArrayList<String> keepUser;
    public ArrayList<Integer> keepScore;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        LinearLayout main = (LinearLayout) findViewById(R.id.mainLinear);

        readFile();

        for (int i = 0; i < keepUser.size(); i++) {
            LinearLayout newLine = new LinearLayout(this);
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1);

            for(int j =0; j < 2; j++){
                TextView string = new TextView(this);
                string.setGravity(Gravity.CENTER);
                string.setLayoutParams(lparams);
                string.setTextAppearance(R.style.TextAppearance_AppCompat_Large);
                if(j == 0){
                    string.setText(keepUser.get(i));
                }else{
                    string.setText(String.valueOf(keepScore.get(i)));
                }
                newLine.addView(string);
            }
            main.addView(newLine);
            TextView horizon_line = new TextView(this);
            horizon_line.setBackground(getResources().getDrawable(R.drawable.horizon_line));
            main.addView(horizon_line);
        }
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

            for(String i : keepUser)
                System.out.println(i);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}