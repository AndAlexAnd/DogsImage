package com.example.dogsimage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://dog.ceo/api/breeds/image/random";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDogImage();
    }

    private void loadDogImage() {
        new Thread(new Runnable() { // создаем фоновый поток для работы с сетью
            @Override
            public void run() {
                try {
                    URL url = new URL(BASE_URL); // создаем адрес
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); // открываем интернет соединение
                    InputStream inputStream = urlConnection.getInputStream(); // Создаем поток ввода. Теперь можем читать данные побайтово.
                    //Байт - в символ, Символы - в строки
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream); // теперь можем считывать символы
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader); // теперь можем считывать строки


                    StringBuilder data = new StringBuilder();
                    String result;
                    do {
                        result = bufferedReader.readLine();
                        if (result != null) {
                            data.append(result);
                        }
                    } while (result != null);

                    JSONObject jsonObject = new JSONObject(data.toString());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    DogImage dogImage = new DogImage(status, message);

                    Log.d("Main", dogImage.toString());

                } catch (Exception e) {
                    Log.d("MainActivity", e.toString());

                }
            }
        }).start(); // test

    }
}
