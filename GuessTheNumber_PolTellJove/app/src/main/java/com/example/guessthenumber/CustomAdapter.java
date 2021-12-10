package com.example.guessthenumber;

import java.util.ArrayList;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    ArrayList<Player> playerList;

    public CustomAdapter(ArrayList<Player> playerList) {

        this.playerList = playerList;

    }

    //Creates ViewHolders
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list,null,false);
        return new ViewHolder(view);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Attributes
        ImageView image;
        TextView name;
        TextView attempts;
        TextView time;

        //Puts the attributes in the player's ranking
        public ViewHolder(View itemView) {

            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.txtName);
            attempts = itemView.findViewById(R.id.txtAttempts);
            time = itemView.findViewById(R.id.txtTime);

        }

        //Introduces data in each attribute
        public void setData(Player player) {

            image.setImageURI(Uri.parse(player.getImagePath()));
            name.setText(player.get_name());
            attempts.setText(String.valueOf(player.getAttempts()));
            time.setText(String.valueOf(player.getTime()));

        }

    }

    //Introduces data in the player
    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {

        holder.setData(playerList.get(position));

    }

    //Shows the playerList's size
    @Override
    public int getItemCount() {

        return playerList.size();

    }

}
