package com.example.dogsimage;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("image/random") // может также быть POST, PUT DELETE
    // @GET("image/random") в () указываем "end point" после base url
    Single<DogImage> loadDogImage(); // указываем Single, чтобы работать с RxJava
}
