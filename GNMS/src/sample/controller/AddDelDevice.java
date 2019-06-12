package sample.controller;

import javafx.collections.ObservableList;
import javafx.css.Size;
import javafx.css.SizeUnits;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sample.database.DBHelper;
import sample.model.Device;

public class AddDelDevice {
//    private ObservableList<Device> deviceList = FXCollections.observableArrayList();


    @FXML
    ListView<Device> deviceLV;
    @FXML
    Button addBTN;
    @FXML
    TextField deviceNameTF;
    //@FXML
    //Button deleteBTN = new Button("حذف دستگاه");



    @FXML
    public void initialize() {
        //deviceLV.setItems(getDevices());
        updateDeviceList();
        //deviceLV.setItems(ob);
        addBTN.setOnAction(event -> {
            DBHelper.getInstance().addDevice(new Device(deviceNameTF.getText()));
            updateDeviceList();
        });
    }

    private void deleteDevice(int id) {
        DBHelper.getInstance().delDevice(id);
    }

    private void updateDeviceList() {
        ObservableList<Device> deviceList = DBHelper.getInstance().getDevices();
        deviceLV.setCellFactory(param -> new ListCell<>() {
            Label deviceNameLB = new Label();
            Button deleteBTN = new Button("حذف دستگاه");
            AnchorPane anchorPane = new AnchorPane(deleteBTN, deviceNameLB);
//            private AnchorPane getAnchorPane() {
//                anchorPane = new AnchorPane(deleteBTN, deviceNameLB);
//                anchorPane.setPrefHeight(25);
//                AnchorPane.setRightAnchor(deleteBTN, 5.0);
//                AnchorPane.setLeftAnchor(deviceNameLB, 7.0);
//                return anchorPane;
//            }

            @Override
            protected void updateItem(Device item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    deviceNameLB.setText(item.getDeviceType());
                    deleteBTN.setOnAction(event -> {
                        deleteDevice(item.getDeviceId());
                        updateDeviceList();
                    });
                    //anchorPane.setPrefHeight(25);
                    AnchorPane.setRightAnchor(deleteBTN, 5.0);
                    AnchorPane.setLeftAnchor(deviceNameLB, 7.0);
                    setGraphic(anchorPane);
                }
            }
        });
        deviceLV.setItems(deviceList);
    }
}
