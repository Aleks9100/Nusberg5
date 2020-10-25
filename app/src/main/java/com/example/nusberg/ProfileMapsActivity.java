package com.example.nusberg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.nusberg.Transform.getRoundedMapBitMap;
import static com.example.nusberg.UserStaticInfo.ActiveUser;
import static com.example.nusberg.UserStaticInfo.POSITION;
import static com.example.nusberg.UserStaticInfo.POSITION_LATITUDE;
import static com.example.nusberg.UserStaticInfo.POSITION_LONGITUDE;
import static com.example.nusberg.UserStaticInfo.USERS_PROFILE_INFO;
import static com.example.nusberg.UserStaticInfo.profileId;

public class ProfileMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    View PathView;
    Location lastLocation;
    PolylineOptions rectOptions= new PolylineOptions();
    Polyline polygon;
    FirebaseDatabase database;
    private GoogleMap mMap;
    private TextView LogitudeTextView,LatitudeTextView,NameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Init();
        NameTextView.setText(ActiveUser.getName());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try{setMapLocationchange();}
        catch (Exception e){}
    }

    private void Init() {
        PathView = findViewById(R.id.PathView);
        NameTextView = findViewById(R.id.NameTextView);
        LogitudeTextView=findViewById(R.id.LogitudeTextView);
        LatitudeTextView=findViewById(R.id.LatitudeTextView);

        NameTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams params=PathView.getLayoutParams();
                params.height=NameTextView.getHeight();
                params.width=NameTextView.getWidth();
                PathView.setLayoutParams(params);
            }
        });
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
                    else
                        rectOptions.add(new LatLng(location.getLatitude(),location.getLongitude()));
                    lastLocation=location;
                    Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.ashe);
                    mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).icon(BitmapDescriptorFactory.fromBitmap(getRoundedMapBitMap(bitmap))));
                }
            }
        });
    }
}
