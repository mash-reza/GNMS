package sample.controller;

import com.mysql.cj.xdevapi.DbDoc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import sample.database.DBHelper;
import sample.model.Customer;

import javax.lang.model.type.IntersectionType;
import java.util.ArrayList;
import java.util.List;

public class CustomerPage {
    @FXML
    private TextField addCustomerNameTF;
    @FXML
    private TextField addCustomerLastNameTF;
    @FXML
    private TextField addCustomerPhoneTF;
    @FXML
    private TextField addCustomerChargeTF;
    @FXML
    private Button addCustomerRegisterBT;


    @FXML
    private CheckBox searchNameCHB;
    @FXML
    private CheckBox searchLastNameCHB;
    @FXML
    private CheckBox searchPhoneCHB;
    @FXML
    private TextField searchNameTF;
    @FXML
    private TextField searchLastNameTF;
    @FXML
    private TextField searchPhoneTF;
    @FXML
    private Button searchBT;

    @FXML
    TableView<Customer> customerTBL;
    @FXML
    TableColumn<Customer, Integer> customerTBLIdCL;
    @FXML
    TableColumn<Customer, String> customerTBLNameCL;
    @FXML
    TableColumn<Customer, String> customerTBLLastNameCL;
    @FXML
    TableColumn<Customer, String> customerTBLPhoneCL;
    @FXML
    TableColumn<Customer, Long> customerTBLChargeCL;
    @FXML
    TableColumn<Customer, Integer> customerTBLDeleteCL;

    @FXML
    TextField edit_id_textfield;
    @FXML
    TextField edit_charge_textfield;
    @FXML
    TextField edit_phone_textfield;
    @FXML
    Button edit_button;

    @FXML
    public void initialize() {
        addCustomerRegisterBT.setOnAction(actionEvent -> addCustomer());
        searchBT.setOnAction(actionEvent -> searchCustomer());
        edit_button.setOnAction(event -> {
            if (!edit_charge_textfield.getText().isEmpty() && !edit_phone_textfield.getText().isEmpty()) {
                DBHelper.getInstance().editCustomer(Integer.valueOf(edit_id_textfield.getText()), 1, edit_phone_textfield.getText(), Integer.valueOf(edit_charge_textfield.getText()));
                new Alert(Alert.AlertType.NONE, "شماره تماس و شارژ مشتری مورد نظر بروزرسانی شد.", ButtonType.CLOSE).show();
            } else if (!edit_phone_textfield.getText().isEmpty()) {
                DBHelper.getInstance().editCustomer(Integer.valueOf(edit_id_textfield.getText()), 2, edit_phone_textfield.getText(), 0);
                new Alert(Alert.AlertType.NONE, "شماره تماس مشتری مورد نظر بروزرسانی شد.", ButtonType.CLOSE).show();
            } else if (!edit_charge_textfield.getText().isEmpty()) {
                DBHelper.getInstance().editCustomer(Integer.valueOf(edit_id_textfield.getText()), 3, null, Integer.valueOf(edit_charge_textfield.getText()));
                new Alert(Alert.AlertType.NONE, "شارژ مشتری مورد نظر بروزرسانی شد.", ButtonType.CLOSE).show();

            } else new Alert(Alert.AlertType.NONE, "پارامتری برای تغییر وارد نشد!", ButtonType.CLOSE).show();
        });
    }


    public void addCustomer() {
        if (addCustomerPhoneTF.getText().isEmpty() && addCustomerChargeTF.getText().isEmpty()) {
            Customer customer = new Customer(
                    addCustomerNameTF.getText(),
                    addCustomerLastNameTF.getText(),
                    0);
            DBHelper.getInstance().addCustomer(customer);
        } else if (addCustomerChargeTF.getText().isEmpty()) {
            Customer customer = new Customer(
                    addCustomerNameTF.getText(),
                    addCustomerLastNameTF.getText(),
                    addCustomerPhoneTF.getText(),
                    0);
            DBHelper.getInstance().addCustomer(customer);
        } else if (addCustomerPhoneTF.getText().isEmpty()) {
            Customer customer = new Customer(
                    addCustomerNameTF.getText(),
                    addCustomerLastNameTF.getText(),
                    Long.valueOf(addCustomerChargeTF.getText()),
                    0);
            DBHelper.getInstance().addCustomer(customer);
        } else {
            Customer customer = new Customer(
                    addCustomerNameTF.getText(),
                    addCustomerLastNameTF.getText(),
                    addCustomerPhoneTF.getText(),
                    Long.valueOf(addCustomerChargeTF.getText()),
                    0);
            DBHelper.getInstance().addCustomer(customer);
        }
    }

    public void searchCustomer() {
        //List<String> postList = new ArrayList<String>();
        List<Customer> customers = new ArrayList<Customer>();
        customerTBL.getItems().removeAll(customers);
//        if (searchNameCHB.isSelected() && !searchNameTF.getText().isEmpty()
//                && searchLastNameCHB.isSelected() && !searchLastNameTF.getText().isEmpty()
//                && searchPhoneCHB.isSelected() && !searchPhoneTF.getText().isEmpty()) {
//            postList.add("123");
//            postList.add(searchNameTF.getText());
//            postList.add(searchLastNameTF.getText());
//            postList.add(searchPhoneTF.getText());
//            getList = DBHelper.getInstance().getCustomers(postList);
//        } else if (searchNameCHB.isSelected() && !searchNameTF.getText().isEmpty()
//                && searchLastNameCHB.isSelected() && !searchLastNameTF.getText().isEmpty()) {
//            postList.add("12");
//            postList.add(searchNameTF.getText());
//            postList.add(searchLastNameTF.getText());
//            getList = DBHelper.getInstance().getCustomers(postList);
//        } else if (searchLastNameCHB.isSelected() && !searchLastNameTF.getText().isEmpty()
//                && searchPhoneCHB.isSelected() && !searchPhoneTF.getText().isEmpty()) {
//            postList.add("23");
//            postList.add(searchLastNameTF.getText());
//            postList.add(searchPhoneTF.getText());
//            getList = DBHelper.getInstance().getCustomers(postList);
//        } else if (searchNameCHB.isSelected() && !searchNameTF.getText().isEmpty()
//                && searchPhoneCHB.isSelected() && !searchPhoneTF.getText().isEmpty()) {
//            postList.add("13");
//            postList.add(searchNameTF.getText());
//            postList.add(searchPhoneTF.getText());
//            getList = DBHelper.getInstance().getCustomers(postList);
//        } else if (searchNameCHB.isSelected() && !searchNameTF.getText().isEmpty()) {
//            postList.add("1");
//            postList.add(searchNameTF.getText());
//            getList = DBHelper.getInstance().getCustomers(postList);
//        } else if (searchLastNameCHB.isSelected() && !searchLastNameTF.getText().isEmpty()) {
//            postList.add("2");
//            postList.add(searchLastNameTF.getText());
//            getList = DBHelper.getInstance().getCustomers(postList);
//        } else if (searchPhoneCHB.isSelected() && !searchPhoneTF.getText().isEmpty()) {
//            postList.add("3");
//            postList.add(searchPhoneTF.getText());
//            getList = DBHelper.getInstance().getCustomers(postList);
//        }

        if (searchNameCHB.isSelected() && !searchNameTF.getText().isEmpty()
                && searchLastNameCHB.isSelected() && !searchLastNameTF.getText().isEmpty()
                && searchPhoneCHB.isSelected() && !searchPhoneTF.getText().isEmpty()) {
            customers = DBHelper.getInstance().getCustomers(123, searchNameTF.getText(), searchLastNameTF.getText(), searchPhoneTF.getText());
        } else if (searchNameCHB.isSelected() && !searchNameTF.getText().isEmpty()
                && searchLastNameCHB.isSelected() && !searchLastNameTF.getText().isEmpty()) {
            customers = DBHelper.getInstance().getCustomers(12, searchNameTF.getText(), searchLastNameTF.getText(), null);
        } else if (searchLastNameCHB.isSelected() && !searchLastNameTF.getText().isEmpty()
                && searchPhoneCHB.isSelected() && !searchPhoneTF.getText().isEmpty()) {
            customers = DBHelper.getInstance().getCustomers(23, null, searchLastNameTF.getText(), searchPhoneTF.getText());
        } else if (searchNameCHB.isSelected() && !searchNameTF.getText().isEmpty()
                && searchPhoneCHB.isSelected() && !searchPhoneTF.getText().isEmpty()) {
            customers = DBHelper.getInstance().getCustomers(13, searchNameTF.getText(), null, searchPhoneTF.getText());
        } else if (searchNameCHB.isSelected() && !searchNameTF.getText().isEmpty()) {
            customers = DBHelper.getInstance().getCustomers(1, searchNameTF.getText(), null, null);
        } else if (searchLastNameCHB.isSelected() && !searchLastNameTF.getText().isEmpty()) {
            customers = DBHelper.getInstance().getCustomers(2, null, searchLastNameTF.getText(), null);
        } else if (searchPhoneCHB.isSelected() && !searchPhoneTF.getText().isEmpty()) {
            customers = DBHelper.getInstance().getCustomers(3, null, null, searchPhoneTF.getText());
        } else customers = DBHelper.getInstance().getCustomers(0, null, null, null);


        customerTBLIdCL.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerTBLNameCL.setCellValueFactory(new PropertyValueFactory<Customer, String>("fname"));
        customerTBLLastNameCL.setCellValueFactory(new PropertyValueFactory<Customer, String>("lname"));
        customerTBLPhoneCL.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        customerTBLChargeCL.setCellValueFactory(new PropertyValueFactory<Customer, Long>("charge"));
        customerTBLDeleteCL.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        ObservableList<Customer> ob = FXCollections.observableArrayList();
        customerTBL.setItems(ob);
        customerTBL.getItems().addAll(customers);

        List<Customer> finalGetList = customers;

        Callback<TableColumn<Customer, Integer>, TableCell<Customer, Integer>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Customer, Integer> call(final TableColumn<Customer, Integer> param) {
                final TableCell<Customer, Integer> cell = new TableCell<>() {
                    final Button button = new Button("حذف");

                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            button.setOnAction(actionEvent1 -> {
                                System.out.println("delete button works!!!");
                                deleteCustomer(item);
//                                    int tobeDeleted = -1;
//                                    for (int i = 0; i < finalGetList.size(); i++) {
//                                        if (finalGetList.get(i).getFname().equals(customerTBL.getColumns().get(0))
//                                                && finalGetList.get(i).getLname().equals(customerTBL.getColumns().get(1))) {
//                                            tobeDeleted = i;
//                                        }
//                                    }
//                                    System.out.println(tobeDeleted);
//                                    if (tobeDeleted != -1) {
//                                        customerTBL.getItems().remove(tobeDeleted);
//                                    }
//                                    customerTBL.sort();
                            });
                            setGraphic(button);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        customerTBLDeleteCL.setCellFactory(cellFactory);
    }

    private void deleteCustomer(int customerId) {
        DBHelper.getInstance().delCustomer(customerId);
    }

//    private void editCustomer(int id, String newPhone, int newCharge) {
//        Customer customer = new Customer(id, null, null, newPhone, newCharge, 0);
//        DBHelper.getInstance().editCustomer(customer);
//    }
}
