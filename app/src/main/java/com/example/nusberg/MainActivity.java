package com.example.nusberg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.nusberg.UserStaticInfo.POSITION;
import static com.example.nusberg.UserStaticInfo.users;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context;
    LayoutInflater layoutInflater;
    FrameLayout UserPanel;
    static TextView TVName,TVState,TVAge;
    static UserListAdapter userListAdapter;
    private int positionActiveUser;

    public static void UpdateListAndUserPanel(User user) {
        userListAdapter.notifyDataSetChanged();
        InitPanel(user);
    }

    private static void InitPanel(User user) {
        TVName.setText(user.getName());
        TVState.setText(user.getState());
        TVAge.setText(String.valueOf(user.getAge()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new UserStaticInfo();
        Init();
    }



    private void Init() {
        UserPanel = findViewById(R.id.userPanel);
        TVName = findViewById(R.id.TVName);
        TVState = findViewById(R.id.TVState);
        TVAge = findViewById(R.id.TVAge);


        recyclerView = findViewById(R.id.RV);
        context = this;
        layoutInflater = LayoutInflater.from(context);
        userListAdapter = new UserListAdapter();
        recyclerView.setAdapter(userListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userListAdapter.notifyDataSetChanged();
    }

    public void GoToUserProfile(int position)
    {
        Intent intent=new Intent(context,UserActivity.class);
        intent.putExtra(POSITION,position);
        startActivity(intent);
    }

    public void BackToList(View view) {
        UserVisiblity(false);
    }

    private void UserVisiblity(boolean b) {
        if(b) UserPanel.setVisibility(View.VISIBLE);
        else  UserPanel.setVisibility(View.GONE);
    }

    public void EditUser(View view) {
        GoToUserProfile(positionActiveUser);
    }

    public class UserListAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.item_user,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
         final User user = users.get(position);
         holder.nameView.setText(user.getName());
         holder.stateView.setText(user.getState());

         switch (user.getStateSignal())
         {
             case 0 :holder.StateRound.setBackgroundResource(R.drawable.back_offline);
             break;
             case 1 :holder.StateRound.setBackgroundResource(R.drawable.back_online);
             break;
             case 2 :holder.StateRound.setBackgroundResource(R.drawable.back_departed);
             break;
         }
         holder.view.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v) {
                 Toast.makeText(context,users.get(position).getName(),Toast.LENGTH_SHORT).show();
                positionActiveUser = position;
                 InitPanel(users.get(position));
                 UserVisiblity(true);
             }


         });

        }


        @Override
        public int getItemCount() {
            return users.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        final TextView nameView,stateView;
        final View view;
        final FrameLayout StateRound;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            StateRound=itemView.findViewById(R.id.StateRound);
            nameView = (TextView)itemView.findViewById(R.id.TVName);
            stateView = itemView.findViewById(R.id.TVState);
            this.view=itemView;
            };
        }
    }
