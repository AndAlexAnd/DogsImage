package com.example.dogsimage;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String BASE_URL = "https://dog.ceo/api/breeds/image/random";
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String TAG = "MainViewModel";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<DogImage> dogImageMutableLiveData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) { // Переопределяем
        super(application);

    }

    public LiveData<DogImage> getDogImageMutableLiveData() {
        return dogImageMutableLiveData;
    }

    public void loadDogImage() {
        Disposable disposable = loadDogImageRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DogImage>() {
                    @Override
                    public void accept(DogImage image) throws Throwable {
                        dogImageMutableLiveData.setValue(image);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, "Error: " + throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    private Single<DogImage> loadDogImageRx() {
        return Single.fromCallable(new Callable<DogImage>() {
            @Override
            public DogImage call() throws Exception {

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
                String status = jsonObject.getString(KEY_STATUS);
                String message = jsonObject.getString(KEY_MESSAGE);
                return new DogImage(status, message);
                //Log.d(TAG, dogImage.toString());


            }
        });
    }
}
