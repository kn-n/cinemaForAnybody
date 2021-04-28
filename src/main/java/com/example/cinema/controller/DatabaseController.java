package com.example.cinema.controller;

import com.example.cinema.domain.AllFilms;
import com.example.cinema.domain.Film;
import com.example.cinema.domain.Item;
import com.example.cinema.repos.FilmRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


@Controller
public class DatabaseController {
    public static Item item;

    @Autowired
    private FilmRepo filmRepo;

    @GetMapping("/main")
    public String main(Map<String, Object> model) throws IOException {

        String url = "https://imdb-api.com/en/API/Top250Movies/k_66r6c4dn";

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        Gson g = new Gson();
        item = g.fromJson(response.toString(), Item.class);

        List<AllFilms> omae = filmRepo.findAll();

        if (omae.isEmpty()){
            for (Film film:item.items) {
                AllFilms newFilm = new AllFilms();
                newFilm.setStringId(film.id);
                newFilm.setRank(film.rank);
                newFilm.setTitle(film.title);
                newFilm.setFullTitle(film.fullTitle);
                newFilm.setYear(film.year);
                newFilm.setImage(film.image);
                newFilm.setCrew(film.crew);
                newFilm.setImDbRating(film.imDbRating);
                newFilm.setImDbRatingCount(film.imDbRatingCount);
                newFilm.setAverageRating("");
                newFilm.setVoters("");
                filmRepo.save(newFilm);
            }
        }

        List<AllFilms> filmFromDb = filmRepo.findAll();

        filmFromDb.sort(AllFilms::compareTo);

        model.put("films", filmFromDb);

        return "main";
    }
}
