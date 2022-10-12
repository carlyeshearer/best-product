// **********************************************************************************
// Title: BestProduct
// Author: Carly Shearer
// Course Section: CMIS202-ONL1 (Seidel) Fall 2022
// File: BestProduct.java
// Description: Program allows users to input items and compare them on multiple
// criteria to determine what products best fit their needs.
// **********************************************************************************
package com.example.bestproduct;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.image.*;
import java.nio.charset.*;
import java.nio.file.Path;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.io.IOException;
public class BestProduct extends Application {
    //data fields
    public static ArrayList<Item> items = new ArrayList<Item>();

    //GUI components
    @Override
    public void start(Stage stage) throws IOException {
        Pane mainPane = new Pane();
        Scene scene = new Scene(mainPane, 480, 125);
        Label welcome = new Label("Welcome to Best Product, the program that helps you decide what is best to buy!\n" +
                "Please choose an option.");

        Button add = new Button("Add an Item");
        Button remove = new Button("Remove an Item");
        Button view = new Button("View my Lists");
        Button help = new Button("Help");
        Button exit = new Button("Save and Exit");

        HBox titles = new HBox();
        titles.setSpacing(20);
        titles.setAlignment(Pos.CENTER);
        titles.getChildren().addAll(welcome);

        HBox buttons = new HBox();
        buttons.setPadding(new Insets(20));
        buttons.setSpacing(5);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(add, remove, view, help, exit);

        VBox box = new VBox();
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(titles, buttons);
        mainPane.getChildren().addAll(box);

        //action events
        EventHandler<ActionEvent> addEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                addItem(scene, stage);
            }
        };

        EventHandler<ActionEvent> viewEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                viewItem(scene, stage);
            }
        };

        EventHandler<ActionEvent> removeEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                removeItem(scene, stage);
            }
        };

        EventHandler<ActionEvent> exitEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                saveFile(items);
                items.forEach(item -> {
                    System.out.println(item);
                });
                System.exit(0);
            }
        };

        //set actions
        add.setOnAction(addEvent);
        remove.setOnAction(removeEvent);
        exit.setOnAction(exitEvent);
        view.setOnAction(viewEvent);

        stage.setTitle("Item Sorter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        openFile(items);
        launch();
    }

    public static void addItem(Scene scene, Stage stage) {
        //GUI components
        Pane pane = new Pane();
        ScrollPane sPane = new ScrollPane(pane);
        Scene addScene = new Scene(sPane, 400, 500);

        //label and buttons
        Label labR = new Label("Have you purchased this product?");
        Label labR2 = new Label("Please enter your rating: ");
        Label labN = new Label("Name:");
        Label labP = new Label("Price: $");
        Label labQ = new Label("Quality:");
        Label labA = new Label("Is this product available?");
        Button enter = new Button("Enter");

        //user input
        TextField inName = new TextField();
        TextField inPrice = new TextField();
        TextField inRate = new TextField();

        //create buttons
        RadioButton yes1 = new RadioButton("Yes");
        RadioButton no1 = new RadioButton("No");
        RadioButton yes2 = new RadioButton("Yes");
        RadioButton no2 = new RadioButton("No");
        ToggleGroup group1 = new ToggleGroup();
        yes1.setToggleGroup(group1);
        no1.setToggleGroup(group1);
        ToggleGroup group2 = new ToggleGroup();
        yes2.setToggleGroup(group2);
        no2.setToggleGroup(group2);

        //category dropdwon
        final ComboBox qualities = new ComboBox();
        qualities.getItems().addAll(
                "5 Stars",
                "4 Stars",
                "3 Stars",
                "2 Stars",
                "1 Star"
        );

        //rows and columns (GUI)
        HBox nameBox = new HBox();
        nameBox.setPadding(new Insets(20));
        nameBox.setAlignment(Pos.CENTER_LEFT);
        nameBox.setSpacing(10);
        nameBox.getChildren().addAll(labN, inName);

        HBox priceBox = new HBox();
        priceBox.setPadding(new Insets(20));
        priceBox.setAlignment(Pos.CENTER_LEFT);
        priceBox.setSpacing(10);
        priceBox.getChildren().addAll(labP, inPrice);

        HBox qualityBox = new HBox();
        qualityBox.setPadding(new Insets(20));
        qualityBox.setAlignment(Pos.CENTER_LEFT);
        qualityBox.setSpacing(10);
        qualityBox.getChildren().addAll(labQ, qualities);

        HBox rateBox = new HBox();
        rateBox.setPadding(new Insets(20));
        rateBox.setAlignment(Pos.CENTER_LEFT);
        rateBox.setSpacing(10);
        rateBox.getChildren().addAll(labR, yes1, no1);

        HBox availBox = new HBox();
        availBox.setPadding(new Insets(20));
        availBox.setAlignment(Pos.CENTER_LEFT);
        availBox.setSpacing(10);
        availBox.getChildren().addAll(labA, yes2, no2);

        HBox rateBox2 = new HBox();
        rateBox2.setPadding(new Insets(20));
        rateBox2.setAlignment(Pos.CENTER_LEFT);
        rateBox2.setSpacing(10);
        rateBox2.getChildren().addAll(labR2, inRate);

        HBox enterBox = new HBox();
        enterBox.setPadding(new Insets(20));
        enterBox.setAlignment(Pos.CENTER_LEFT);
        enterBox.getChildren().addAll(enter);

        VBox columns = new VBox();
        columns.setPadding(new Insets(10));
        columns.setAlignment(Pos.CENTER_LEFT);
        columns.getChildren().addAll(rateBox, nameBox, priceBox, qualityBox, availBox, enterBox);
        pane.getChildren().addAll(columns);

        qualities.setOnAction((event) -> {
            //get category choice
            int selectedIndex = qualities.getSelectionModel().getSelectedIndex();
            Object selectedItem = qualities.getSelectionModel().getSelectedItem();
            String quality = (String)qualities.getValue();
            items.get(items.size() - 1).setQuality(quality);
        });

        //determine whether purchased
        yes1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                items.add(new PurchasedItem());
                columns.getChildren().removeAll(rateBox, nameBox, priceBox, qualityBox, availBox, enterBox);
                columns.getChildren().addAll(rateBox, rateBox2, nameBox, priceBox, qualityBox, availBox, enterBox);
            }
        });

        no1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                items.add(new Item());
            }
        });

        //get availability from buttons
        yes2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                items.get(items.size() - 1).setAvailable(true);
                System.out.println("yes button");
            }
        });

        no2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                items.get(items.size() - 1).setAvailable(false);
                System.out.println(items.get(items.size() - 1).isAvailable());
            }
        });

        //submit all information and update item
        EventHandler<ActionEvent> enterEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                String name = inName.getText();
                String price = inPrice.getText();
                String rating = inRate.getText();

                if(items.get(items.size() - 1) instanceof PurchasedItem){
                    PurchasedItem item = (PurchasedItem)items.get(items.size() - 1);
                    item.setName(name);
                    item.setRating(rating);

                    try{ //parse user input as double
                        items.get(items.size() - 1).setPrice(Double.parseDouble(price));
                    }
                    catch(NumberFormatException n){ //set to default of 0 if non-number is entered
                        items.get(items.size() - 1).setPrice(0);
                    }
                }
                else{
                    Item item = items.get(items.size() - 1);

                    item.setName(name);

                    try{ //parse user input as double
                        items.get(items.size() - 1).setPrice(Double.parseDouble(price));
                    }
                    catch(NumberFormatException n){ //set to default of 0 if non-number is entered
                        items.get(items.size() - 1).setPrice(0);
                    }
                }

                stage.setScene(scene);
            }
        };

        enter.setOnAction(enterEvent);
        stage.setScene(addScene);
        stage.show();
    }//add a recipe manually to array

    public static void removeItem(Scene scene, Stage stage){
        //GUI components
        Pane pane = new Pane();
        Scene removeScene = new Scene(pane, 400, 200);
        VBox box = new VBox();
        Label label = new Label("Please select the item to remove.");
        Button exit = new Button("Exit");
        box.setSpacing(20);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER_LEFT);
        box.getChildren().addAll(label, exit);
        pane.getChildren().addAll(box);

        if(items.size() > 0) { //if there are items to remove
            final ComboBox itemChoice = new ComboBox(); //choice dropdwon
            for(Item i : items){
                itemChoice.getItems().addAll(i.getName());
            }

            itemChoice.setOnAction((event) -> {
                //get selected item name
                int selectedIndex = itemChoice.getSelectionModel().getSelectedIndex();
                Object selectedItem = itemChoice.getSelectionModel().getSelectedItem();
                String itemName = (String)itemChoice.getValue();
                removeName(itemName); //remove from items using removeName() method
            });

            box.getChildren().remove(exit);
            exit.setText("Remove");
            box.getChildren().addAll(itemChoice, exit);
        }
        else{
            label.setText("No items to remove."); //if no items in items
        }

        //action events
        EventHandler<ActionEvent> exitEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                stage.setScene(scene);
            }
        };

        exit.setOnAction(exitEvent);
        stage.setScene(removeScene);
        stage.show();
    }   //remove an item from array

    public static void removeName(String name){
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getName().equals(name)){
                items.remove(i);
            }
        }
    }//remove item from items

    public static void viewItem(Scene scene, Stage stage){
        //GUI components
        Pane pane = new Pane();
        ScrollPane sPane = new ScrollPane(pane);
        Scene viewScene = new Scene(sPane, 600, 400);
        VBox box = new VBox();
        Label title = new Label("View Item Lists By:");
        Label body = new Label("");
        Button exit = new Button("Exit");
        box.setPadding(new Insets(20));
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER_LEFT);
        box.getChildren().addAll(title, exit);
        pane.getChildren().addAll(box);

        if(items.size() > 0){ //if there are items to view
            //create buttons
            RadioButton nameB = new RadioButton("Name");
            RadioButton priceB = new RadioButton("Price");
            RadioButton qualityB = new RadioButton("Quality");
            RadioButton availB = new RadioButton("Availability");

            ToggleGroup group = new ToggleGroup();
            nameB.setToggleGroup(group);
            priceB.setToggleGroup(group);
            qualityB.setToggleGroup(group);
            availB.setToggleGroup(group);

            //button action events
            nameB.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    try{
                        for(Item i : items){
                            body.setText(getMessage(items, "name"));
                        }
                    }
                    catch(ConcurrentModificationException c){
                        //System.out.println(c);
                    }
                }
            });

            priceB.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    try{
                        for(Item i : items){
                            body.setText(getMessage(items, "price"));
                        }
                    }
                    catch(ConcurrentModificationException c){
                        //System.out.println(c);
                    }
                }
            });

            qualityB.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    try{
                        for(Item i : items){
                            body.setText(getMessage(items, "quality"));
                        }
                    }
                    catch(ConcurrentModificationException c){
                        //System.out.println(c);
                    }
                }
            });

            availB.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    try{
                        for(Item i : items){
                            body.setText(getMessage(items, "availability"));
                        }
                    }
                    catch(ConcurrentModificationException c){
                        //System.out.println(c);
                    }
                }
            });

            HBox buttonBox = new HBox();
            buttonBox.setPadding(new Insets(20));
            buttonBox.setAlignment(Pos.CENTER_LEFT);
            buttonBox.setSpacing(10);
            buttonBox.getChildren().addAll(nameB, priceB, qualityB, availB);

            box.getChildren().remove(exit);
            box.getChildren().addAll(buttonBox, body, exit);
        }
        else {// if no items created
            title.setText("No items to view.");
        }

        //action events
        EventHandler<ActionEvent> exitEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                stage.setScene(scene);
            }
        };

        exit.setOnAction(exitEvent);
        stage.setScene(viewScene);
        stage.show();
    } //view list of items

    public static String getMessage(ArrayList<?> generic, String criteria){
        String message = "";

        switch(criteria){
            case "name":
                sortByName();
                break;
            case "price":
                sortByPrice();
                break;
            case "quality":
                sortByQuality();
                break;
            case "availability":
                sortByAvailability();
                break;
        }

        for(Object o : generic){
            message += o.toString() + "\n";
        }

        return message;
    }//get viewRecipe message

    public static void sortByName(){
        items.sort(Comparator.comparing(Item::getName));
    }

    /*
    The Quick Sort sorting method was chosen because it has the most consistently efficient performance. It
    has a best and average case of O(nlogn) and a worst case of O(n^2), similar to Heap Sort. I chose to
    implement a Quick Sort instead of a Heap Sort because the price values of the items in the ArrayList are
    random.
     */
    public static void quickSort(ArrayList<Item> list) { //recursive call
        quickSort(list, 0, list.size() - 1);
    }

    private static void quickSort(ArrayList<Item> list, int first, int last) { //recursive call
        if (last > first) {
            int pivotIndex = partition(list, first, last);
            quickSort(list, first, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, last);
        }
    }

    private static int partition(ArrayList<Item> list, int first, int last) { //part the list
        Item pivot = list.get(first); //first element is pivot
        int low = first + 1; //index for forward search
        int high = last; //index for backward search

        while (high > low) {
            //search forward from left
            while (low <= high && list.get(low).getPrice() <= pivot.getPrice()){
                low++;
            }

            //search backward from right
            while (low <= high && list.get(high).getPrice() > pivot.getPrice()){
                high--;
            }

            //swap elements
            if (high > low) {
                Collections.swap(list, high, low);
            }
        }

        while (high > first && list.get(high).getPrice() >= pivot.getPrice()){
            high--;
        }

        //swap pivot
        if (pivot.getPrice() > list.get(high).getPrice()) {
            Collections.swap(list, 0, high);
            return high;
        }
        else {
            return first;
        }
    }

    public static void sortByPrice(){
        quickSort(items);
    }

    public static void sortByQuality(){
        items.sort(Comparator.comparing(Item::getQuality));
    }

    public static void sortByAvailability(){
        items.sort(Comparator.comparing(Item::isAvailable));
    }

    public static void openFile(ArrayList<Item> items){
        String fileName = "Items.bin"; //file to be opened
        File file = new File(fileName);

        if(!file.exists()){ //if file does not exist, don't try to access it
            return;
        }

        try{
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);

            Object o;

            while(true){
                try{
                    o = ois.readObject(); //read objects from input stream
                    items.add((Item)o); //add object cast as item to items
                }
                catch(EOFException ex){
                    break;
                }
            }
            ois.close();
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }//open items from previous save

    public static void saveFile(ArrayList<Item> items){
        String fileName = "Items.bin"; //file to save to
        try{
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for(Item i : items){ //for each recipe
                oos.writeObject(i); //write to output stream
            }

            oos.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }//save items
}