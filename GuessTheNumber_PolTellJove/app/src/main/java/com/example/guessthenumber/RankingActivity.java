package com.example.guessthenumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

public class RankingActivity extends AppCompatActivity {

    public static ArrayList players = new ArrayList<Player>();
    RecyclerView recyclerView;

    //Configuration of player's ranking
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        recyclerView = findViewById(R.id.playersRanking);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        CustomAdapter adapter = new CustomAdapter(players);
        recyclerView.setAdapter(adapter);
        sortArrayList();

    }

    //The ranking is ordered by the best scores
    private void sortArrayList(){

        Collections.sort(players, new Comparator<Player>(){

            @Override
            public int compare(Player player1, Player player2) {

                if (player1.getAttempts() != player2.getAttempts()){

                    return player1.getAttempts() - player2.getAttempts();

                } else {

                    return player1.getTime() - player2.getTime();

                }

            }

        });

    }

}