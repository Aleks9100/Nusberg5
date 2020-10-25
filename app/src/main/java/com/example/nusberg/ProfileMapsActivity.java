package com.example.nusberg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.nusberg.UserStaticInfo.POSITION;
import static com.example.nusberg.UserStaticInfo.POSITION_LATITUDE;
import static com.example.nusberg.UserStaticInfo.POSITION_LONGITUDE;
import static com.example.nusberg.UserStaticInfo.USERS_PROFILE_INFO;
import static com.example.nusberg.UserStaticInfo.profileId;

public class ProfileMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    Location lastLocation;
    PolylineOptions rectOptions= new PolylineOptions();
    Polyline polygon;
    FirebaseDatabase database;
    private GoogleMap mMap;
    private TextView LogitudeTextView,LatitudeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try{setMapLocationchange();}
        catch (Exception e){}
    }

    private void Init() {
        LogitudeTextView=findViewById(R.id.LogitudeTextView);
        LatitudeTextView=findViewById(R.id.LatitudeTextView);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(ProfileMapsActivity.this,new String [] {Manifest.permission.ACCESS_FINE_LOCATION},123);
        }
        else
            {
                setMapLocationchange();
            }
    }

    private void setMapLocationchange() {
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if(location!=null)
                {
                    if(database == null)
                        database = FirebaseDatabase.getInstance();
                    String Lat = String.valueOf(location.getLatitude());
                    String Lon=String.valueOf(location.getLongitude());
                    database.getReference(USERS_PROFILE_INFO).child(profileId).child(POSITION_LATITUDE).setValue(Lat);
                    database.getReference(USERS_PROFILE_INFO).child(profileId).child(POSITION_LONGITUDE).setValue(Lon);
                    LatitudeTextView.setText(Lat);
                    LogitudeTextView.setText(Lon);

                    if(lastLocation!=null)
                    {
                        if(polygon!=null)
                            polygon.remove();
                        rectOptions.add(new LatLng(location.getLatitude(),location.getLongitude()));
                        polygon=mMap.addPolyline(rectOptions);
                    }
                }
            }
        });
    }
}
