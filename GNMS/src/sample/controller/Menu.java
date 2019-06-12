package sample.controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Menu {
    private VBox vBox = new VBox();
    private boolean managementPageIsClicked = false;
    private Management management = new Management();

    private Parent customerPage = FXMLLoader.load(getClass().getResource("../view/customerPage.fxml"));
    private Parent settingPage = FXMLLoader.load(getClass().getResource("../view/settingPage.fxml"));
    private Parent addDelDevicePage = FXMLLoader.load(getClass().getResource("../view/addDelDevice.fxml"));
    private Parent accountingPage = FXMLLoader.load(getClass().getResource("../view/accountingPage.fxml"));

    public Menu() throws IOException {

        //Menu
        MenuBar menuBar = new MenuBar();
        javafx.scene.control.Menu menu1 = new javafx.scene.control.Menu("مشتری");
        javafx.scene.control.Menu menu2 = new javafx.scene.control.Menu("مدیریت");
        javafx.scene.control.Menu menu3 = new javafx.scene.control.Menu("حسابرسی");
        javafx.scene.control.Menu menu4 = new javafx.scene.control.Menu("تنظیمات");
        MenuItem menuItem11 = new MenuItem("مدیریت مشتری ها");
        MenuItem menuItem21 = new MenuItem("مدیریت دستگاه ها");
        MenuItem menuItem22 = new MenuItem("درج/ حذف دستگاه ها");
        MenuItem menuItem31 = new MenuItem("محاسبه درآمد");
        MenuItem menuItem41 = new MenuItem("تنظیمات سیستم");


        menu1.getItems().addAll(menuItem11);
        menu2.getItems().addAll(menuItem21, menuItem22);
        menu3.getItems().addAll(menuItem31);
        menu4.getItems().addAll(menuItem41);
        menuBar.getMenus().addAll(menu1, menu2, menu3, menu4);
        //Pages

        //Parent management = FXMLLoader.load(getClass().getResource("view/management.fxml"));

        //Adding Pages and Menus to VBox
        vBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        vBox.getChildren().addAll(menuBar, customerPage);
//        vBox.getChildren().add(0,menuBar);
//        vBox.getChildren().add(1,customerPage);
        //Handling Menu Items
        menuItem11.setOnAction(actionEvent -> {
            vBox.getChildren().remove(1);
            vBox.getChildren().add(customerPage);
        });
        menuItem21.setOnAction(actionEvent -> {
//            vBox.getChildren().remove(1);
//            vBox.getChildren().add(management.getDevice());
//            managementPageIsClicked = true;
            vBox.getChildren().remove(1);
            vBox.getChildren().add(management.getDevice());
        });
        menuItem22.setOnAction(actionEvent -> {
            vBox.getChildren().remove(1);
            vBox.getChildren().add(addDelDevicePage);
        });
        menuItem31.setOnAction(actionEvent -> {
            vBox.getChildren().remove(1);
            vBox.getChildren().add(accountingPage);
        });
        menuItem41.setOnAction(actionEvent -> {
            vBox.getChildren().remove(1);
            vBox.getChildren().add(settingPage);
        });
    }

    public VBox getvBox() throws IOException {
        return vBox;
    }
}
