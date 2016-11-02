package application.controller;

import com.jfoenix.controls.JFXTextField;

import application.model.viewmodel.impl.MovieViewModel;
import application.model.viewmodel.interfaces.IFachkonzept;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MovieController {

   IFachkonzept viewModel;
   boolean yearValid;
   boolean txtValid;

   @FXML
   Button btnAdd;
   @FXML
   AnchorPane newMoviePane;
   @FXML
   JFXTextField txtTitle;
   @FXML
   JFXTextField txtGenre;
   @FXML
   JFXTextField txtYear;

   @FXML
   public void initialize() {
      newMoviePane.getStylesheets()
         .add(getClass().getResource("../view/application.css").toExternalForm());
      viewModel = new MovieViewModel();
   }

   @FXML
   public void btnCancleClicked() {
      removeArguments();
      resetPrompt();
      MainObservable.toggleMovie();
   }

   @FXML
   public void btnOkClicked() {
      checkValidYear();
      checkValidTitleAndGenre();
      persistMovie();
   }

   private void checkValidYear() {
      yearValid = false;
      try {
         Integer.parseInt(txtYear.getText());
         txtYear.setPromptText("Year");
         yearValid = true;
      } catch (NumberFormatException e) {
         txtYear.clear();
         txtYear.setPromptText("Bitte Jahreszahl eingeben!");
      }
   }

   private void checkValidTitleAndGenre() {
      txtValid = false;
      try {
         if (txtTitle.getText().isEmpty() || txtGenre.getText().isEmpty())
            throw new NullPointerException();
         txtTitle.setPromptText("Title");
         txtGenre.setPromptText("Genre");
         txtValid = true;
      } catch (NullPointerException e) {
         txtTitle.setPromptText(txtTitle.getText().isEmpty() ? "Title - Pflichtfeld" : "Title");
         txtGenre.setPromptText(txtGenre.getText().isEmpty() ? "Genre - Pflichtfeld" : "Genre");
      }
   }

   private void persistMovie() {
      if (yearValid && txtValid) {
         viewModel.bindAttributes(txtTitle.textProperty(), txtGenre.textProperty(),
            txtYear.textProperty());
         viewModel.persist();
         MainObservable.refreshMainView();
         removeArguments();
         MainObservable.toggleMovie();
      }
   }

   private void resetPrompt() {
      txtTitle.setPromptText("Title");
      txtGenre.setPromptText("Genre");
      txtYear.setPromptText("Year");
   }

   private void removeArguments() {
      txtTitle.clear();
      txtGenre.clear();
      txtYear.clear();
   }

}