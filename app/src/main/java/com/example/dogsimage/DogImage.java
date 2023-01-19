package com.example.dogsimage;

public class DogImage {

    String status;
    String message;

    public DogImage(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() { // ALT + INSERT переопределяем toString
        return "DogImage{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
