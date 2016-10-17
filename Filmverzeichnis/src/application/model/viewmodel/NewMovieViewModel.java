package application.model.viewmodel;

import application.model.dto.Movie;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NewMovieViewModel {

   StringProperty title = new SimpleStringProperty();
   StringProperty genre = new SimpleStringProperty();
   IntegerProperty year = new SimpleIntegerProperty();

   public void addMovie() {
      Movie movie = new Movie();
      movie.setName(title);
      movie.setGenre(genre);
      movie.setReleaseYear(year);
      //TODO Fachkonzept einbinden
   }


   public StringProperty getTitle() {
      return title;
   }


   public void setTitle(StringProperty title) {
      this.title = title;
   }


   public StringProperty getGenre() {
      return genre;
   }


   public void setGenre(StringProperty genre) {
      this.genre = genre;
   }


   public IntegerProperty getYear() {
      return year;
   }


   public void setYear(IntegerProperty year) {
      this.year = year;
   }

}


/**
 * $ID: NewMovieViewModel.java,v $
 */
