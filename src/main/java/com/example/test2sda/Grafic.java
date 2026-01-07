package com.example.test2sda;


import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Grafic extends Application {

    private Stage mainStage;
    private ListView<Carte> listView;
    private ChoiceBox<Student> listaStudenti;
    private Text nrZile = new Text("Nr Zile:");
    private TextField nrZiletxt = new TextField();
    private Button btnImprumuta = new Button("Imprumuta");
    private Button btnReturneaza = new Button("Returneaza");
    private TextField returneazaTxt=new TextField();
    private Button btnImprumuturiMaiMariDe = new Button("Imprumuturi mai mari de...");
    private Slider slider;
    private Label sliderValueLabel;
    private Button btnInfoAutor = new Button("Info Autor");
    private Button btnSituatieImprumut = new Button("Situatie imprumut");
    private Button btnFisaStudentului = new Button("Fisa Studentului");
    private TextArea textArea = new TextArea();
    private Text listaC = new Text("Lista Carti:");
    private Text listaSt = new Text("Lista Studenti:");
    private Carte carteSelectata;
    private Student studentSelectat;
    private Button btnPrelungeste = new Button("Prelungeste");

    private List<Carte> carti = new ArrayList<>();
    private List<Student> studenti = new ArrayList<>();


    private File citireFisier(boolean citire) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Deschide fisier");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter
                ("Text Files", "*.txt"));
        File selectedFile = citire ? fileChooser.showOpenDialog(mainStage) :
                fileChooser.showSaveDialog(mainStage);
        return selectedFile;
    }

    private ListView<Carte> getListView() throws FileNotFoundException {
        File fisier = citireFisier(true);
        if (fisier == null)
            return null;
        ListView<Carte> listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setPrefWidth(200);
        listView.setPrefHeight(200);

        Scanner sc = new Scanner(fisier);
        while (sc.hasNextLine()) {
            String linie = sc.nextLine();
            if (linie.isEmpty())
                continue;
            String parts[] = linie.split(",");
            String titlu = parts[0];
            String autor = parts[1];
            int an = Integer.parseInt(parts[2]);
            String gen = parts[3];


            Carte carte = new Carte(titlu, autor, an, gen);
            carti.add(carte);
        }
        ObservableList<Carte> data = FXCollections.observableArrayList(carti);
        data.sorted();
        listView.setItems(data);
        listView.getSelectionModel().selectedItemProperty().addListener((observableValue, carte, t1) -> {
            if (t1 != null) {
                carteSelectata = t1;
                textArea.appendText(carteSelectata.toString());
            }
        });
        textArea.clear();
        return listView;
    }

    private ChoiceBox<Student> getChoiceBox() throws FileNotFoundException {
        ChoiceBox<Student> listaStudenti = new ChoiceBox<>();
        File fisier = new File("studenti.txt");
        if (fisier == null)
            return null;
        Scanner sc = new Scanner(fisier);
        while (sc.hasNextLine()) {
            String linie = sc.nextLine();
            if (linie.isEmpty())
                continue;
            String parts[] = linie.split(",");
            String nume = parts[0];
            Double medie = Double.parseDouble(parts[1]);
            String localitate = parts[2];

            Student student = new Student(nume, medie, localitate);
            studenti.add(student);


        }
        ObservableList<Student> data = FXCollections.observableArrayList(studenti);
        data.sorted();
        listaStudenti.setItems(data);
        listaStudenti.getSelectionModel().selectedItemProperty().addListener((observableValue, student, t1) -> {
            if (t1 != null) {
                studentSelectat = t1;
                textArea.appendText(studentSelectat.toString());

            }
        });
        textArea.clear();
        return listaStudenti;

    }

    private Slider getSlider(){
        slider=new Slider(1,365,1);
        slider.setSnapToTicks(true);
        slider.setBlockIncrement(1);
        sliderValueLabel = new Label("Zile: 1");
        slider.valueProperty().addListener((observableValue, number, t1) -> {
            textArea.clear();
            sliderValueLabel.setText("Zile: " + t1.intValue());
            int valoare= t1.intValue();
            for (Carte carte:carti){
                if(valoare==carte.getZile())
                    textArea.appendText(carte.toString());
            }
        });
        return slider;

    }

    private VBox getPanou() throws FileNotFoundException {
        nrZiletxt.setMaxWidth(50);
        listView = getListView();
        listaStudenti = getChoiceBox();
        btnInfoAutor.setOnAction(actionEvent -> {
            textArea.clear();
            textArea.appendText("\nMarjina Daniela");
        });
        btnImprumuta.setOnAction(actionEvent -> {
            if (studentSelectat == null) {
                textArea.appendText("\nSelectati un student!");
                return;
            }
            ObservableList<Carte> cartiSelectate =
                    listView.getSelectionModel().getSelectedItems();

            if (cartiSelectate.isEmpty()) {
                textArea.appendText("Selectati cel putin o carte!\n");
                return;
            }
            else {

                if (nrZiletxt.getText().isEmpty()) {
                    textArea.appendText("\nIntrdouceti un nr de zile!");
                    return;
                }

                int zile = Integer.parseInt(nrZiletxt.getText().trim());

                for (Carte carte : cartiSelectate) {
                    if (!carte.isImprumutata()) {
                        carte.setImprumutata(true);
                        carte.setZile(zile);
                        carte.setStudent(studentSelectat);
                        textArea.appendText(carte.toString());
                    }
                }

            }

            listView.refresh();
        });
//        btnReturneaza.setOnAction(actionEvent -> {
//            if (carteSelectata == null) {
//                textArea.appendText("\nSelectati o carte!");
//                return;
//            }
//            else {
//                if (carteSelectata.isImprumutata()) {
//                    carteSelectata.setImprumutata(false);
//                    carteSelectata.setZile(0);
//                    carteSelectata.setStudent(null);
//                    textArea.appendText(carteSelectata.toString());
//                } else {
//                    textArea.appendText("\nNu e imprumutata!");
//                }
//            }
//
//        });
        btnReturneaza.setOnAction(actionEvent -> {
            if(returneazaTxt.getText().isEmpty())
                textArea.appendText("\nIntrdouceti o carte");
            else{
                String sir=returneazaTxt.getText().trim();
                for(Carte carte:carti){
                    if(carte.getTitlu().toLowerCase().contains(sir.toLowerCase()))
                        textArea.appendText(carte.toString());
                }
            }
        });
        btnPrelungeste.setOnAction(actionEvent -> {
            if (carteSelectata == null) {
                textArea.appendText("\nSelectati o carte!");
                return;
            } else {
                if (nrZiletxt.getText().isEmpty()) {
                    textArea.appendText("\nIntrdouceti un nr de zile!");
                    return;
                }
                int zile = Integer.parseInt(nrZiletxt.getText().trim());
                if (carteSelectata.isImprumutata()) {
                    carteSelectata.setZile(carteSelectata.getZile() + zile);
                    textArea.appendText(carteSelectata.toString());
                    nrZiletxt.clear();
                } else {
                    textArea.appendText("\nNu e imprumutata!");
                }
            }

        });
        btnImprumuturiMaiMariDe.setOnAction(actionEvent -> {
            if (nrZiletxt.getText().isEmpty()) {
                textArea.appendText("\nIntrdouceti un nr de zile!");
                return;
            }
            int zile = Integer.parseInt(nrZiletxt.getText().trim());
            boolean gasit = false;
            for (Carte carte : carti) {
                if (carte.getZile() > zile) {
                    gasit = true;
                    textArea.appendText(carte.toString());
                }
            }
            if (!gasit)
                textArea.appendText("\nNu sunt carti imprumutate pe mai multe zile!");

        });
        btnFisaStudentului.setOnAction(actionEvent -> {
            if (studentSelectat == null) {
                textArea.appendText("\nSelectati un student!");
                return;
            } else {
                boolean gasit = false;
                for (Carte carte : carti) {
                    if (carte.getStudent() == studentSelectat) {
                        gasit = true;
                        textArea.appendText(carte.toString());
                    }
                }
                if (!gasit)
                    textArea.appendText("\nNu sunt carti imprumutate de acest student!");

            }
        });

        btnSituatieImprumut.setOnAction(actionEvent -> {
            if (carteSelectata == null) {
                textArea.appendText("\nSelectati o carte!");
                return;
            }
            else if (!carteSelectata.isImprumutata()) {
                textArea.appendText("\nNu a fost imprumutata!");
                return;
            }
            else {
                boolean gasit = false;
                for (Student student : studenti) {
                    if (carteSelectata.getStudent() == student) {
                        gasit = true;
                        textArea.appendText(carteSelectata.toString());
                    }
                }
                if (!gasit)
                    textArea.appendText("\nNu e imprumutata!");

            }
        });

        slider=getSlider();
        VBox col1 = new VBox(10, listaC, listView, btnSituatieImprumut);
        HBox nrZileBox = new HBox(10, nrZile, nrZiletxt);
        VBox col2 = new VBox(10, nrZileBox, btnImprumuta, returneazaTxt, btnReturneaza, btnImprumuturiMaiMariDe, sliderValueLabel,slider,btnInfoAutor);
        VBox col3 = new VBox(10, listaSt, listaStudenti, btnPrelungeste, btnFisaStudentului);
        HBox parteaSus = new HBox(10, col1, col2, col3);
        VBox panou = new VBox(10, parteaSus, textArea);
        return panou;

    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        Scene scene = new Scene(getPanou(), 600, 500);
        stage.setScene(scene);
        stage.setTitle("Test");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
