package com.example.nusberg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.nusberg.Transform.parseIntOrDefault;
import static com.example.nusberg.UserStaticInfo.POSITION;
import static com.example.nusberg.UserStaticInfo.users;

public class UserActivity extends AppCompatActivity {

    private User activityUser;
    private EditText NameTextView;
    private EditText StateTextView;
    private EditText AgeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getIntent().getIntExtra(POSITION,0);
        setContentView(R.layout.activity_user);
        activityUser=users.get(position);
        Init();
        setUserInfo();
    }

    private void setUserInfo() {
        NameTextView.setText(activityUser.getName());
        StateTextView.setText(activityUser.getState());
        AgeTextView.setText(String.valueOf(activityUser.getAge()));
    }

    private void Init() {
        NameTextView = findViewById(R.id.NameTextView);
        StateTextView = findViewById(R.id.StateTextView);
        AgeTextView = findViewById(R.id.AgeTextView);
    }

    public void Back(View view) {
        onBackPressed();
    }

    public void Save(View view) {
        activityUser.setName(NameTextView.getText().toString());
        activityUser.setState(StateTextView.getText().toString());
        String age = AgeTextView.getText().toString();
      activityUser.setAge(parseIntOrDefault(age,activityUser.getAge()));

        MainActivity.UpdateListAndUserPanel(activityUser);
        finish();
    }
}
