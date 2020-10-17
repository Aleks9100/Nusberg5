package com.example.nusberg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.nusberg.Transform.StringNoNull;
import static com.example.nusberg.Transform.Vibrate;
import static com.example.nusberg.Transform.md5Custome;
import static com.example.nusberg.UserStaticInfo.AGE;
import static com.example.nusberg.UserStaticInfo.NAME;
import static com.example.nusberg.UserStaticInfo.PASSWORD;
import static com.example.nusberg.UserStaticInfo.PROFILE_ID;
import static com.example.nusberg.UserStaticInfo.STATE;
import static com.example.nusberg.UserStaticInfo.USERS_PROFILE_INFO;
import static com.example.nusberg.UserStaticInfo.USERS_SIGN_IN_INFO;
import static com.example.nusberg.UserStaticInfo.profileId;

public class SignActivity extends AppCompatActivity {
    private EditText LoginET,PasswordET;
    private EditText NewLoginET,NewPasswordET,NewNameET,NewStateET,NewAgeET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        Init();
    }

    private void Init() {
        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.tabSignIn);
        tabSpec.setIndicator("Вход");
        tabHost.addTab(tabSpec);

        tabSpec=tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tabSignUp);
        tabSpec.setIndicator("Регистрация");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);

        LoginET=findViewById(R.id.LoginET);
        PasswordET= findViewById(R.id.PasswordET);
        NewAgeET=findViewById(R.id.NewAgeET);
        NewLoginET=findViewById(R.id.NewLoginET);
        NewNameET=findViewById(R.id.NewNameET);
        NewPasswordET = findViewById(R.id.NewPasswordET);
        NewStateET = findViewById(R.id.NewStateET);
    }

    public void SignIn(View view) {
        if(EditTextNoNullWithAnimation(PasswordET) && EditTextNoNullWithAnimation(LoginET)) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(USERS_SIGN_IN_INFO);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String login = getLogin();
                    Object value = dataSnapshot.child(login).child(PASSWORD).getValue();
                    if (value != null) {
                        if (value.toString().equals(md5Custome(getPassword()))) {
                            goNext(dataSnapshot.child(PROFILE_ID).toString());
                        } else CantSignIn();
                    } else CantSignIn();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
            {
                Vibrate(SignActivity.this);
                Toast.makeText(SignActivity.this,getResources().getText(R.string.NullParametersMessage),Toast.LENGTH_SHORT).show();
            }

    }

    private String getLogin() {
        return LoginET.getText().toString();
    }

    private void goNext(String profileId) {
UserStaticInfo.profileId = profileId;
    }

    private String getPassword() {
        return PasswordET.getText().toString();
    }

    private String getNewLogin() {
        return NewLoginET.getText().toString();
    }

    private String getNewPassword() {
        return NewPasswordET.getText().toString();
    }

    private String getNewState() {
        return NewStateET.getText().toString();
    }

    private String getNewName() {
        return NewNameET.getText().toString();
    }

    private int getNewAge() {
        try {
            return Integer.parseInt(NewAgeET.getText().toString());
        }
        catch (Exception ex)
        {
            return 0;
        }
    }

    private void CantSignIn() {
        Toast.makeText(SignActivity.this,getResources().getText(R.string.CantSignInMessage),Toast.LENGTH_SHORT).show();
    }

    public void SignUp(View view) {
        if(EditTextNoNullWithAnimation(NewLoginET) & EditTextNoNullWithAnimation(NewPasswordET) & EditTextNoNullWithAnimation(NewNameET) & EditTextNoNullWithAnimation(NewStateET))
        {
            final FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference myRef=database.getReference(USERS_SIGN_IN_INFO).child(getNewLogin());
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(PASSWORD).exists())
                    {
                        String id = database.getReference(USERS_PROFILE_INFO).push().getKey();
                        String login = getNewLogin();
                        database.getReference(USERS_SIGN_IN_INFO).child(login).child(PASSWORD).setValue(md5Custome(getNewPassword()));
                        database.getReference(USERS_SIGN_IN_INFO).child(login).child(PROFILE_ID).setValue(id);

                        database.getReference(USERS_PROFILE_INFO).child(id).child(AGE).setValue(getNewAge());
                        database.getReference(USERS_PROFILE_INFO).child(id).child(STATE).setValue(getNewState());
                        database.getReference(USERS_PROFILE_INFO).child(id).child(NAME).setValue(getNewName());
                    }
                    else
                    {
                        Toast.makeText(SignActivity.this,getResources().getText(R.string.UserExistMessage),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else
        {
            Vibrate(SignActivity.this);
            Toast.makeText(SignActivity.this,getResources().getText(R.string.NullParametersMessage),Toast.LENGTH_SHORT).show();
        }
    }

    private boolean EditTextNoNullWithAnimation(EditText animationET) {
        return EditTextNoNullWithAnimation(animationET,animationET.getText().toString());
    }
    private boolean EditTextNoNullWithAnimation(EditText animationET,String value) {
        boolean NoNullText = StringNoNull(value);
        Animation animation = AnimationUtils.loadAnimation(SignActivity.this,R.anim.eror_edit);
        if(!NoNullText)
        {
            animationET.startAnimation(animation);
        }
        return NoNullText;
    }
}
