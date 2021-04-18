package com.example.cinema.controller;

import com.example.cinema.domain.AllFilms;
import com.example.cinema.domain.Film;
import com.example.cinema.domain.Item;
import com.example.cinema.repos.FilmRepo;
import com.example.cinema.repos.MessageRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;



@Controller
public class DatabaseController {
    public static Item item;

    @Autowired
    private FilmRepo filmRepo;

    @GetMapping("/main")
    public String main(Map<String, Object> model) throws IOException {

        String url = "https://imdb-api.com/en/API/Top250Movies/k_a3bczbcf";

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

        for (int i = 1; i<=250; i++) {
            AllFilms aue = new AllFilms();
            if (aue.getStringId() == null) {
                aue.setStringId(item.items.get(i - 1).id);
                aue.setRank(item.items.get(i - 1).rank);
                aue.setTitle(item.items.get(i - 1).title);
                aue.setFullTitle(item.items.get(i - 1).fullTitle);
                aue.setYear(item.items.get(i - 1).year);
                aue.setImage(item.items.get(i - 1).image);
                aue.setCrew(item.items.get(i - 1).crew);
                aue.setImDbRating(item.items.get(i - 1).imDbRating);
                aue.setImDbRatingCount(item.items.get(i - 1).imDbRatingCount);
                filmRepo.save(aue);
            }
            else break;
        }

//        for (int i = 1; i<=250; i++) {
//            AllFilms aue = new AllFilms();
//
//            aue.setStringId(item.items.get(i - 1).id);
//            aue.setRank(item.items.get(i - 1).rank);
//            aue.setTitle(item.items.get(i - 1).title);
//            aue.setFullTitle(item.items.get(i - 1).fullTitle);
//            aue.setYear(item.items.get(i - 1).year);
//            aue.setImage(item.items.get(i - 1).image);
//            aue.setCrew(item.items.get(i - 1).crew);
//            aue.setImDbRating(item.items.get(i - 1).imDbRating);
//            aue.setImDbRatingCount(item.items.get(i - 1).imDbRatingCount);
//            filmRepo.save(aue);
//        }

        model.put("films", item.items);

        return "main";
    }
}
