package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.database.DBHelper;
import sample.model.AccountingParams;
import sample.model.Device;
import sample.model.IncomeTable;

import java.util.List;

public class Accounting {
    @FXML
    CheckBox historyCHB;
    @FXML
    DatePicker fromDP;
    @FXML
    DatePicker tillDP;
    @FXML
    CheckBox deviceCHB;
    @FXML
    ListView<CheckBox> deviceLV;
    @FXML
    CheckBox customerIdCHB;
    @FXML
    TextField customerIdTF;
    @FXML
    Button calculateBTN;
    @FXML
    TextField totalIncomeTF;
    @FXML
    TableView<IncomeTable> table;
    @FXML
    TableColumn<IncomeTable, String> dateCL;
    @FXML
    TableColumn<IncomeTable, String> deviceCL;
    @FXML
    TableColumn<IncomeTable, String> startTimeCL;
    @FXML
    TableColumn<IncomeTable, String> finishTimeCL;
    @FXML
    TableColumn<IncomeTable, String> customerFNameCL;
    @FXML
    TableColumn<IncomeTable, String> customerLNameCL;
    @FXML
    TableColumn<IncomeTable, Integer> incomeCL;
    @FXML
    RadioButton customerNameRB;
    @FXML
    RadioButton customerIdRB;

    char startTimeTag = '0';
    char finishTimeTag = '0';
    char devicesTag = '0';
    char idTag = '0';
    char nameTag = '0';

    @FXML
    public void initialize() {
        AccountingParams params = new AccountingParams();
        //device
        ObservableList<Device> primaryDeviceList = DBHelper.getInstance().getDevices();
        ObservableList<CheckBox> deviceList = FXCollections.observableArrayList();
        deviceCHB.setOnAction(event -> {
            if (deviceCHB.isSelected()) {
                devicesTag = '1';
                deviceLV.getItems().clear();
                for (Device device : primaryDeviceList) {
                    deviceList.add(new CheckBox(device.getDeviceType()));
                }
                deviceLV.setItems(deviceList);
                for (int i = 0; i < deviceList.size(); i++) {
                    final int index = i;
                    deviceList.get(i).setOnAction(event2 -> {
                        if (deviceList.get(index).isSelected()) {
//                        System.out.println(primaryDeviceList.get(index).getDeviceId());
                            params.getDevices().add(primaryDeviceList.get(index));
                        }
                    });
                }
            }else devicesTag = '0';
        });
        historyCHB.setOnAction(event -> {
            if (historyCHB.isSelected()) {
                fromDP.setOnAction(event1 -> {
                    params.setStartTime(fromDP.getValue().toString());
                    startTimeTag = '1';
                });
                tillDP.setOnAction(event1 -> {
                    params.setFinishTime(tillDP.getValue().toString());
                    finishTimeTag = '1';
                });
            }else {
                startTimeTag = '0';
                finishTimeTag = '0';
            }
        });

        //customer
        ToggleGroup tg = new ToggleGroup();
        customerIdRB.setToggleGroup(tg);
        customerNameRB.setToggleGroup(tg);
        customerIdRB.setSelected(false);
        customerNameRB.setSelected(false);
        customerIdTF.setOnKeyTyped(event1 -> {
            if (customerIdCHB.isSelected()) {
                if (customerIdRB.isSelected()) {
                    idTag = '1';
                    nameTag = '0';
                    params.setCustomerId(Integer.valueOf(customerIdTF.getText()));
                }
                if (customerNameRB.isSelected()) {
                    nameTag = '1';
                    idTag = '0';
                    params.setCustomerName(customerIdTF.getText());
                }
            }else {
                idTag = '0';
                nameTag = '0';
            }
        });

        //calculate
        calculateBTN.setOnAction(event -> {
            StringBuilder tag = new StringBuilder();
            tag.append(startTimeTag).append(finishTimeTag).append(devicesTag).append(idTag).append(nameTag);
            System.out.println(tag);
            List<IncomeTable> comingParams = DBHelper.getInstance().calculateIncome(tag.toString(), params.getStartTime(),
                    params.getFinishTime(),
                    params.getDevices(),
                    params.getCustomerId(),
                    params.getCustomerName());
            ObservableList<IncomeTable> list = FXCollections.observableArrayList();
            list.addAll(comingParams);
            table.setItems(list);
        });


        dateCL.setCellValueFactory(new PropertyValueFactory<>("date"));
        deviceCL.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        startTimeCL.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        finishTimeCL.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
        customerFNameCL.setCellValueFactory(new PropertyValueFactory<>("customerFName"));
        customerLNameCL.setCellValueFactory(new PropertyValueFactory<>("customerLName"));
        incomeCL.setCellValueFactory(new PropertyValueFactory<>("income"));
    }
}
