package com.example.cinema.repos;

import com.example.cinema.domain.AllFilms;
import com.example.cinema.domain.Film;
import org.springframework.data.repository.CrudRepository;

public interface FilmRepo extends CrudRepository<AllFilms, Integer> {
    Film findById(String id);
}
