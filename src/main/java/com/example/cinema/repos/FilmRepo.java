package com.example.cinema.repos;

import com.example.cinema.domain.AllFilms;
import org.springframework.data.repository.CrudRepository;

public interface FilmRepo extends CrudRepository<AllFilms, Integer> {
}
