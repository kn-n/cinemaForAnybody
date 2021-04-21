package com.example.cinema.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String username;
    public String filmId;
    public String text;
    public String data;

    public Comments() {
    }

    public Comments(Integer id, String username, String filmId, String text, String data) {
        this.id = id;
        this.username = username;
        this.filmId = filmId;
        this.text = text;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdFilm() {
        return filmId;
    }

    public void setIdFilm(String filmId) {
        this.filmId = filmId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
