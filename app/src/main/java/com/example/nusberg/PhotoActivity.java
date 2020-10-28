package com.example.nusberg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.nusberg.UserStaticInfo.POSITION;
import static com.example.nusberg.UserStaticInfo.photos;

public class PhotoActivity extends AppCompatActivity {

    TextView progressTextView;
    ViewPager viewPager;
    LayoutInflater inflater;
    PhotosAdapter photosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        int position=getIntent().getIntExtra(POSITION,0);
        Init(position);
        setCurrentItem();
    }
    private void Init(int position) {
        progressTextView = findViewById(R.id.progress);
        viewPager = findViewById(R.id.PhotosViewPager);
        photosAdapter = new PhotosAdapter();
        ViewPager viewPager = findViewById(R.id.PhotosViewPager);
        inflater= LayoutInflater.from(this);
        viewPager.setAdapter(photosAdapter);
        viewPager.setCurrentItem(position);
        viewPager.setPageTransformer(false,new FadePageTransformer());
    }
     public  class FadePageTransformer implements ViewPager.PageTransformer
     {

         @Override
         public void transformPage(@NonNull View page, float position) {
             if(position == 0.0F){setCurrentItem();}
         }
     }

    private void setCurrentItem() {
        String text = viewPager.getCurrentItem()+1+"/"+photosAdapter.getCount();
        progressTextView.setText(text);
    }

    public class PhotosAdapter extends PagerAdapter
    {
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View itemView=inflater.inflate(R.layout.item_full_photo,container,false);
            ImageView imageView = itemView.findViewById(R.id.image);
            imageView.setImageBitmap(photos.get(position));
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return photos.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view.equals(object);
        }
    }
}
