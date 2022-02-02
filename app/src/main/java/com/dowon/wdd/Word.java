package com.dowon.wdd;

import androidx.annotation.Keep;

@Keep
public class Word {
    public String eng;
    public String name;
    public String mean;

    public Word(String eng, String name, String mean){
        this.eng = eng;
        this.name = name;
        this.mean = mean;
    }
}


