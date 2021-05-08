package com.example.proxecto;
//Vale, ahora Amplituda

import processing.core.PApplet;
import cassette.audiofiles.*;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
    DisplayMetrics metrics = new DisplayMetrics();
    private int width, height;
    float i =0;
    float x=0, yAnterior=0;
    float y=0;
    PImage img;


    public Sketch(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void settings() {


        fullScreen(P2D);
    }

    public void setup() {
        frameRate(200);

        img = loadImage("bird.jpg");


    }

    public void draw() {

        if (x<width){
            image(img, 0, 0, x++, y++);


        }else if(y<height){
            image(img, 0, 0, x, y++);

        }

        if (y==height){
            noLoop();
        }
    }
}