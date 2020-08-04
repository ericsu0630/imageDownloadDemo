package com.example.downloadimagedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Bitmap image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        DownloadImage task = new DownloadImage();
        try{
            image = task.execute("https://i.pinimg.com/600x315/79/d3/6c/79d36c87c8dcb704ccc07921d8987ee1.jpg").get();
        }catch(Exception e){
            e.printStackTrace();
            Log.i("task failed","dead URL!");
        }
    }

    public void buttonPressed(View view){
        imageView.setImageBitmap(image);
    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url;
            try{
                url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream in = httpURLConnection.getInputStream();
                return BitmapFactory.decodeStream(in);
            }catch(Exception e){
                e.printStackTrace();
                Log.i("connection failed","something went wrong!");
                return null;
            }

        }
    }
}