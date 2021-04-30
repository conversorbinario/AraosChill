package com.example.proxecto;
//Vale, ahora Amplituda

import processing.core.PApplet;
import cassette.audiofiles.*;

public class Sketch extends PApplet {
    private RealDoubleFFT transformer;
    SoundFile in;
    public void settings() {
        size(640, 860);
        background(34);
        stroke(120);
        strokeWeight(2);
    }

    public void setup() { }

    public void draw() {
        if (mousePressed) {
            ellipse(mouseX, mouseY, 50, 50);
        }
    }
}
