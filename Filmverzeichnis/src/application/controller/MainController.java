package application.controller;

import java.io.IOException;
import java.net.URL;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXTabPane;

import application.model.dto.Actor;
import application.model.dto.Movie;
import application.model.viewmodel.MainViewModel;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class MainController {

	private MainViewModel viewModel;
	private ObservableList<Actor> movieActors;
	private ObservableList<Movie> actorMovies = FXCollections.observableArrayList();
	FXMLLoader loader;
	URL movieUrl;
	URL actorUrl;
	String buttonText;

	@FXML
	private TableView<Movie> movieTable;
	@FXML
	private TableColumn<Movie, String> movieName;
	@FXML
	private TableColumn<Movie, String> movieYear;
	@FXML
	private TableColumn<Movie, String> movieGenre;
	@FXML
	private TableView<Actor> actorTable;
	@FXML
	private TableColumn<Actor, String> actorName;
	@FXML
	private TableColumn<Actor, String> actorBirth;
	@FXML
	private TableColumn<Actor, String> actorSex;
	@FXML
	private TableView<Actor> movieActorsTable;
	@FXML
	private TableColumn<Actor, String> movieActorsName;
	@FXML
	private TableColumn<Actor, String> movieActorsBirth;
	@FXML
	private TableColumn<Actor, String> movieActorsSex;
	@FXML
	private TableView<Movie> actorMoviesTable;
	@FXML
	private TableColumn<Movie, String> actorMoviesName;
	@FXML
	private TableColumn<Movie, String> actorMoviesYear;
	@FXML
	private TableColumn<Movie, String> actorMoviesGenre;
	@FXML
	private Label lblTitle;
	@FXML
	private Label lblActor;
	@FXML
	private JFXDrawersStack movieStack;
	@FXML
	private JFXDrawer movieTopDrawer;
	@FXML
	private JFXDrawer actorTopDrawer;
	@FXML
	private Pane addActorPane;
	@FXML
	private Pane moviePane;
	@FXML
	private JFXDrawersStack actorStack;
	@FXML
	private Pane actorPane;
	@FXML
	private JFXButton btnMovie;
	@FXML
	private JFXButton btnAddMovie;
	@FXML
	private JFXButton btnActor;
	@FXML
	private JFXButton btnAddActor;
	@FXML
	private JFXTabPane tabPane;
	@FXML
	private Tab tabMovie;
	@FXML
	private Tab tabActor;

	@FXML
	public void initialize() throws IOException {
		btnAddActor.setDisable(true);
		btnAddMovie.setDisable(true);
		movieUrl = getClass().getResource("../view/fxml/Movie.fxml");
		actorUrl = getClass().getResource("../view/fxml/Actor.fxml");
		MainObservable.setMainObservable(this);
		viewModel = new MainViewModel();
		prepareTable();
		bindMovieTableToContent();
		bindActorTableToContent();
		bindMovieActorsTableToContent();
		bindActorsMovieTableToContent();
		tabChangeResets();
		setDrawerDirection();
	}

	@FXML
	public void newMovieToggle() throws IOException {
		buttonText = btnMovie.getText();
		prepareMovieFxml();
		prepareDrawer(movieTopDrawer, moviePane);
		toggle(movieStack, movieTopDrawer);
	}

	@FXML
	public void addMoviewToggle() throws IOException {
		buttonText = btnAddMovie.getText();
		prepareMovieFxml();
		prepareDrawer(actorTopDrawer, moviePane);
		toggle(actorStack, actorTopDrawer);
	}

	private void prepareMovieFxml() throws IOException {
		loader = new FXMLLoader(movieUrl);
		loader.setController(new MovieController());
		moviePane = loader.load();
	}

	@FXML
	public void newActorToggle() throws IOException {
		buttonText = btnActor.getText();
		prepareActorFxml();
		prepareDrawer(actorTopDrawer, actorPane);
		toggle(actorStack, actorTopDrawer);
	}

	@FXML
	public void addActorToggle() throws IOException {
		buttonText = btnAddActor.getText();
		prepareActorFxml();
		prepareDrawer(movieTopDrawer, actorPane);
		toggle(movieStack, movieTopDrawer);
	}

	private void prepareActorFxml() throws IOException {
		loader = new FXMLLoader(actorUrl);
		loader.setController(new ActorController());
		actorPane = loader.load();
	}

	public void toggle(JFXDrawersStack stack, JFXDrawer drawer) {
		stack.toggle(drawer);
	}

	public void prepareTable() {
		movieTable.setItems(null);
		movieTable.setItems(FXCollections.observableList(viewModel.getDaoMovieXml().getAll()));
		movieName.setCellValueFactory(cellData -> cellData.getValue().getName());
		movieYear.setCellValueFactory(cellData -> cellData.getValue().getReleaseYear());
		movieGenre.setCellValueFactory(cellData -> cellData.getValue().getGenre());

		actorTable.setItems(null);
		actorTable.setItems(FXCollections.observableList(viewModel.getDaoActorXml().getAll()));
		actorName.setCellValueFactory(cellData -> cellData.getValue().getName());
		actorBirth.setCellValueFactory(cellData -> cellData.getValue().getBirthDate());
		actorSex.setCellValueFactory(cellData -> cellData.getValue().getSex());

		actorMoviesTable.setItems(null);
		if (actorTable.getSelectionModel().getSelectedItem() == null) {
			actorMovies = FXCollections.observableArrayList();
		} else {
			actorMovies.setAll(actorTable.getSelectionModel().getSelectedItem().getMovies());
		}
		actorMoviesTable.setItems(actorMovies);
		actorMoviesName.setCellValueFactory(cellData -> cellData.getValue().getName());
		actorMoviesYear.setCellValueFactory(cellData -> cellData.getValue().getReleaseYear());
		actorMoviesGenre.setCellValueFactory(cellData -> cellData.getValue().getGenre());

		movieActorsTable.setItems(null);
		if (movieTable.getSelectionModel().getSelectedItem() == null) {
			movieActors = FXCollections.observableArrayList();
		} else {
			movieActors.setAll(movieTable.getSelectionModel().getSelectedItem().getActors());
		}
		movieActorsTable.setItems(movieActors);
		movieActorsName.setCellValueFactory(cellData -> cellData.getValue().getName());
		movieActorsBirth.setCellValueFactory(cellData -> cellData.getValue().getBirthDate());
		movieActorsSex.setCellValueFactory(cellData -> cellData.getValue().getSex());
	}

	private void bindActorTableToContent() {
		actorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				this.btnAddMovie.setDisable(false);
				btnActor.setText("Edit Actor");
				lblActor.textProperty().bind(newSelection.getName());
				actorMovies.clear();
				if (newSelection.getMovies() != null) {
					actorMovies.addAll(newSelection.getMovies());
					changeAcorMoviesTableContent();
				}
			} else {
				this.btnAddMovie.setDisable(true);
				btnActor.setText("New Actor");
			}
		});
	}

	private void bindMovieTableToContent() {
		movieTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			this.btnAddActor.setDisable(false);
			if (newSelection != null) {
				btnMovie.setText("Edit Movie");
				lblTitle.textProperty().bind(newSelection.getName());
				movieActors.clear();
				if (newSelection.getActors() != null) {
					movieActors.addAll(newSelection.getActors());
					changeMovieActorsTableContent();
				}
			} else {
				this.btnAddActor.setDisable(true);
				btnMovie.setText("New Movie");
			}
		});
	}

	private void bindMovieActorsTableToContent() {
		movieActorsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				this.btnAddActor.setText("Edit Actor");
			} else {
				this.btnAddActor.setText("Add Actor");
			}
		});
	}

	private void bindActorsMovieTableToContent() {
		actorMoviesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				this.btnAddMovie.setText("Edit Movie");
			} else {
				this.btnAddMovie.setText("Add Movie");
			}
		});
	}

	private void changeAcorMoviesTableContent() {
		actorMoviesTable.setItems(actorMovies);
		actorMoviesName.setCellValueFactory(cellData -> cellData.getValue().getName());
		actorMoviesYear.setCellValueFactory(cellData -> cellData.getValue().getReleaseYear());
		actorMoviesGenre.setCellValueFactory(cellData -> cellData.getValue().getGenre());
	}

	private void changeMovieActorsTableContent() {
		movieActorsTable.setItems(movieActors);
		movieActorsName.setCellValueFactory(cellData -> cellData.getValue().getName());
		movieActorsBirth.setCellValueFactory(cellData -> cellData.getValue().getBirthDate());
		movieActorsSex.setCellValueFactory(cellData -> cellData.getValue().getSex());
	}

	private void tabChangeResets() {
		tabPane.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Tab>) (tab, oldTab, newTab) -> {
			movieTable.getSelectionModel().clearSelection();
			actorTable.getSelectionModel().clearSelection();
			movieActorsTable.getSelectionModel().clearSelection();
			actorMoviesTable.getSelectionModel().clearSelection();
			movieActorsTable.setItems(null);
			actorMoviesTable.setItems(null);
		});
	}

	private void prepareDrawer(JFXDrawer drawer, Pane pane) {
		drawer.setSidePane(pane);
		drawer.setDefaultDrawerSize(400);
	}

	private void setDrawerDirection() {
		movieTopDrawer.setDirection(JFXDrawer.DrawerDirection.TOP);
		actorTopDrawer.setDirection(JFXDrawer.DrawerDirection.TOP);
	}

	public JFXDrawersStack getMovieStack() {
		return movieStack;
	}

	public JFXDrawer getMovieTopDrawer() {
		return movieTopDrawer;
	}

	public JFXDrawer getActorTopDrawer() {
		return actorTopDrawer;
	}

	public JFXDrawersStack getActorStack() {
		return actorStack;
	}

	public MainViewModel getViewModel() {
		return viewModel;
	}

	public TableView<Movie> getMovieTable() {
		return movieTable;
	}

	public TableView<Actor> getActorTable() {
		return actorTable;
	}

	public TableView<Actor> getMovieActorsTable() {
		return movieActorsTable;
	}

	public TableView<Movie> getActorMoviesTable() {
		return actorMoviesTable;
	}

	public String getButtonText() {
		return buttonText;
	}

}
