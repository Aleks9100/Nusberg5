package com.example.nusberg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context;
    LayoutInflater layoutInflater;
    List<User> users = new ArrayList<>();
    FrameLayout UserPanel;
    TextView TVName,TVState,TVAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddUserInList();
        Init();
    }

    private void AddUserInList() {
        users.add(new User("dasd", "fas", 13,0));
        users.add(new User("dasd", "fas", 13,1));
        users.add(new User("dasd", "fas", 13,2));
    }

    private void Init() {
        UserPanel = findViewById(R.id.userPanel);
        TVName = findViewById(R.id.TVName);
        TVState = findViewById(R.id.TVState);
        TVAge = findViewById(R.id.TVAge);

        recyclerView = findViewById(R.id.RV);
        context = this;
        layoutInflater = LayoutInflater.from(context);
        UserListAdapter userListAdapter = new UserListAdapter();
        recyclerView.setAdapter(userListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userListAdapter.notifyDataSetChanged();
    }

    public void BackToList(View view) {
        UserVisiblity(false);
    }

    private void UserVisiblity(boolean b) {
        if(b) UserPanel.setVisibility(View.VISIBLE);
        else  UserPanel.setVisibility(View.GONE);
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
         FrameLayout StateRound=findViewById(R.id.StateRound);
         switch (user.getStateSignal())
         {
             case 0 :StateRound.setBackgroundResource(R.drawable.back_offline);
             break;
             case 1 :StateRound.setBackgroundResource(R.drawable.back_online);
             break;
             case 2 :StateRound.setBackgroundResource(R.drawable.back_departed);
             break;
         }
         holder.view.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v) {
                 Toast.makeText(context,users.get(position).getName(),Toast.LENGTH_SHORT).show();
                 InitPanel(users.get(position));
                 UserVisiblity(true);
             }

             private void InitPanel(User user) {
                 TVName.setText(user.getName());
                 TVState.setText(user.getState());
                 TVAge.setText(user.getAge());
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = (TextView)itemView.findViewById(R.id.TVName);
            stateView = itemView.findViewById(R.id.TVState);
            this.view=itemView;
            };
        }
    }
