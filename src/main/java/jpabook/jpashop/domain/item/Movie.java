package jpabook.jpashop.domain.item;

import jpabook.jpashop.controller.BookForm;
import jpabook.jpashop.controller.MovieForm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.ui.Model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
@Getter
@Setter
public class Movie extends Item{
    private dtype type = dtype.M;

    private String director;
    private String actor;

    public static Movie createMovie(MovieForm form) {
        Movie movie = new Movie();
        movie.setName(form.getName());
        movie.setPrice(form.getPrice());
        movie.setStockQuantity(form.getStockQuantity());
        movie.setActor(form.getActor());
        movie.setDirector(form.getDirector());
        return movie;
    }
}
