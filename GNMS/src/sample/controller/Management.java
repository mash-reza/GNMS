package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import sample.database.DBHelper;
import sample.model.Device;

import java.io.IOException;
import java.util.List;

public class Management {
    @FXML
    private VBox manageBox = new VBox();
    @FXML
    private ScrollPane managePane = new ScrollPane();
    public ScrollPane getDevice() {
        try {
            List<Device> devices = DBHelper.getInstance().getDevices();
            for (int i = 0; i < devices.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/deviceView.fxml"));
                DeviceView deviceView = new DeviceView(i);
                loader.setController(deviceView);
                manageBox.getChildren().add(loader.load());
            }
            managePane.setContent(manageBox);
            managePane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            managePane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            return managePane;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
