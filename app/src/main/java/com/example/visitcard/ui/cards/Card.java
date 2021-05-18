package com.example.visitcard.ui.cards;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Card {
    String name,
            phone,
            mail,
            work;
    Bitmap qr;
    ArrayList<String> socials;

    public Card(String name, String phone, String mail, String work, Bitmap qr, ArrayList<String> socials) {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.work = work;
        this.qr = qr;
        this.socials = socials;
    }

    private List<Card> persons;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public String getWork() {
        return work;
    }

    ArrayList<String> getSocials() {
        return socials;
    }

    public Bitmap getImage() {
        return qr;
    }

}
