package com.example.proyectofinal.networkSync;

import android.net.Uri;

import com.example.proyectofinal.database.models.Carousel;

public class CarouselUpload {
    public Uri uri;
    public Carousel carousel;

    public CarouselUpload(Uri uri, Carousel carousel) {
        this.uri = uri;
        this.carousel = carousel;
    }
}