package com.example.vartalap;

import static android.media.CamcorderProfile.get;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdpter extends RecyclerView.Adapter<UserAdpter.viewholder> {


    Context mainActivity;
    ArrayList<Users> usersArrayList;
    public UserAdpter(MainActivity mainActivity, ArrayList<Users> usersArrayList) {
        this.mainActivity=mainActivity;
        this.usersArrayList=usersArrayList;
    }

    @NonNull
    @Override
    public UserAdpter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.user_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdpter.viewholder holder, int position) {

        Users users = usersArrayList.get(position);

        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(users.getUserId())){
            holder.itemView.setVisibility(View.GONE);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(0, 0);
            holder.itemView.setLayoutParams(layoutParams);

        }

        holder.username.setText(users.userName);
        holder.userstatus.setText(users.status);
        Picasso.get().load(users.profilepic).into(holder.userimg);

        holder.userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the custom dialog layout
                View dialogView = LayoutInflater.from(mainActivity).inflate(R.layout.user_photo_img, null);

                // Initialize views
                ImageView imageProfile = dialogView.findViewById(R.id.userBigImg);
//                TextView textName = dialogView.findViewById(R.id.text_name);
//                TextView textStatus = dialogView.findViewById(R.id.text_status);

                // Populate views with user information
                // Replace these with actual data from Firebase
                // For example:
                // String userName = userList.get(holder.getAdapterPosition()).getName();
                // String userStatus = userList.get(holder.getAdapterPosition()).getStatus();
//                 Bitmap userProfilePicture =  usersArrayList.get(holder.getAdapterPosition()).getProfilepic();

                String userProfileUrl = usersArrayList.get(holder.getAdapterPosition()).getProfilepic();
                Picasso.get().load(userProfileUrl).into(imageProfile);
                // Set user information to views
//                 imageProfile.setImageBitmap(userProfilePicture);
                // textName.setText(userName);
                // textStatus.setText(userStatus);

                // Create and show the dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, chatwindo.class);
                intent.putExtra("nameeee",users.getUserName());
                intent.putExtra("reciverImg",users.getProfilepic());
                intent.putExtra("uid",users.getUserId());
                mainActivity.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public void setFilteredList(ArrayList<Users> filteredList) {
        this.usersArrayList = filteredList;
        notifyDataSetChanged(); // Refresh the RecyclerView to show the filtered data
    }

    public class viewholder extends RecyclerView.ViewHolder {
        CircleImageView userimg;
        TextView username;
        TextView userstatus;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            userimg = itemView.findViewById(R.id.userimg);
            username = itemView.findViewById(R.id.username);
            userstatus = itemView.findViewById(R.id.userstatus);
        }
    }
}
