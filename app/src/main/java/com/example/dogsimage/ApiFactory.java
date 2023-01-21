package com.example.dogsimage;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static final String BASE_URL = "https://dog.ceo/api/breeds/";
    //тут указываем именно base url, часть которого является постоянной и не меняется, окончание обязательно /
    // А end point указываем в ApiService в GET("тут end point")
    // например base url https://dog.ceo/api/breeds/
    // end point image/random

    private static ApiService apiService;

    public static ApiService getApiService(){ // через Singleton
        if (apiService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) //тут указываем именно base url без end point, часть которого является постоянной и не меняется
                    .addConverterFactory(GsonConverterFactory.create()) // добавляем адаптер для преобразования JSON объектов в экземпляры нашего класса DogImage
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // чтобы возвращал тип Single из RxJava
                    .build();
            apiService = retrofit.create(ApiService.class); // создаем класс для Retrofit
        }
        return apiService;
    }
}
