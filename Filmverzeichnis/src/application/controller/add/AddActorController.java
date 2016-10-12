package application.controller.add;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;

import application.model.viewmodel.AddActorViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class AddActorController {

   AddActorViewModel viewModel;
   private ObservableList<String> allMovies = FXCollections.observableArrayList();
   final ToggleGroup group = new ToggleGroup();

   @FXML
   TextField txtName;
   @FXML
   Button btnAdd;
   @FXML
   JFXComboBox<String> chbMovie;
   @FXML
   AnchorPane addActorPane;
   @FXML
   JFXRadioButton rbtnMale;
   @FXML
   JFXRadioButton rbtnFemale;



   @FXML
   public void initialize(){
      addActorPane.getStylesheets().add(getClass().getResource("../../application.css").toExternalForm());
      viewModel = new AddActorViewModel();

      rbtnMale.fire();
      rbtnMale.setToggleGroup(group);
      rbtnFemale.setToggleGroup(group);

      allMovies.add(viewModel.getTestMovie().getName().get());
      chbMovie.setItems(allMovies);
      txtName.setFocusTraversable(false);
   }

   @FXML
   public void btnOKClicked(){

   }
}
