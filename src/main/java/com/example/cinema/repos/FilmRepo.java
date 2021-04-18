package com.example.cinema.repos;

import com.example.cinema.domain.AllFilms;
import com.example.cinema.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FilmRepo extends JpaRepository<AllFilms, Long> {
    AllFilms findById(Integer id);
}
