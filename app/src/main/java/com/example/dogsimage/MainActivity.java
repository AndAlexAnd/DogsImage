package com.example.dogsimage;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private static final String TAG = "MainActivity";

    private ImageView imageDogView;
    private Button buttonNextDog;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.loadDogImage();

        viewModel.getDogImageMutableLiveData().observe(this, new Observer<DogImage>() {
            @Override
            public void onChanged(DogImage dogImage) {
               try {
                   Glide.with(MainActivity.this)// Указываем в той, в которой находимся

                           .load(dogImage.getMessage())// получаем адрес картинки из нашего класса и сообщением с url
                           .into(imageDogView); // устанавливаем картинку в нужный нам view
                   Log.d(TAG, dogImage.getMessage());
                   Log.d(TAG, dogImage.getStatus());
               } catch (Exception e) {
                   Log.d(TAG, dogImage.getMessage());
                   Log.d(TAG, dogImage.getStatus());
                   Log.d(TAG, e.toString());

               }

            }
        });

    }

    public void initViews(){
        imageDogView = findViewById(R.id.imageDogView);
        buttonNextDog = findViewById(R.id.buttonNextDog);
        progressBar = findViewById(R.id.progressBar);
    }
}
