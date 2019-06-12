package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;


public class SettingPage {
    static int price = 4500;
    static int discountPrice = 4000;
    static int ticketMinutes = 240;

    @FXML
    TextField priceTF;
    @FXML
    TextField discountPriceTF;
    @FXML
    Spinner<Integer> ticketMinutesSP;
    @FXML
    Button saveBTN;

    @FXML
    public void initialize() {
        priceTF.setText(String.valueOf(price));
        discountPriceTF.setText(String.valueOf(discountPrice));
        ticketMinutesSP.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, ticketMinutes / 60, 1));
        saveBTN.setOnAction(event -> {
            price = Integer.valueOf(priceTF.getText());
            discountPrice = Integer.valueOf(discountPriceTF.getText());
            ticketMinutes = ticketMinutesSP.getValue() * 60;
            System.out.println(price + "\n" + discountPrice + "\n" + ticketMinutesSP.getValue());
        });
    }

}
