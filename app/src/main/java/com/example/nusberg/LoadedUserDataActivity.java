package com.example.nusberg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.nusberg.Transform.StringNoNull;
import static com.example.nusberg.UserStaticInfo.ActiveUser;
import static com.example.nusberg.UserStaticInfo.NAME;
import static com.example.nusberg.UserStaticInfo.USERS_PROFILE_INFO;
import static com.example.nusberg.UserStaticInfo.profileId;

public class LoadedUserDataActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaded_user_data);
        Init();
    }

    private void goNext(DataSnapshot dataSnapshot) {
        ActiveUser = new User(dataSnapshot);
        Intent intent = new Intent(LoadedUserDataActivity.this,ProfileMapsActivity.class);
        startActivity(intent);
        finish();
        myRef.removeEventListener(eventListener);
    }
    DatabaseReference myRef;
    ValueEventListener eventListener= new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (StringNoNull(dataSnapshot.child(NAME).getValue().toString()))
                goNext(dataSnapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    private void Init() {
        mPlayer=MediaPlayer.create(this,R.raw.bob);
        mPlayer.setVolume(0.2f,0.2f);
        mPlayer.setLooping(true);
        mPlayer.start();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        myRef=database.getReference(USERS_PROFILE_INFO).child(profileId);
        myRef.addValueEventListener(eventListener);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(mPlayer!=null)
        {
            mPlayer.start();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if(mPlayer.isPlaying())
        {
            mPlayer.pause();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(mPlayer.isPlaying())
        {
            mPlayer.stop();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(mPlayer.isPlaying())
        {
            mPlayer.stop();
        }
    }
}
