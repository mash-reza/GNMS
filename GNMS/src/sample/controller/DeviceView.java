package sample.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Calender.JalaliCalendar;
import sample.database.DBHelper;
import sample.model.Customer;
import sample.model.Device;
import sample.model.Play;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DeviceView {
    private int deviceId;
    private ObservableList<Device> deviceList = DBHelper.getInstance().getDevices();

    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;
    private volatile boolean running = true;
    private volatile boolean startClicked = false;
    private volatile boolean stopClicked = false;
    //private volatile boolean resetClicked = false;
    private static DecimalFormat timerDF = new DecimalFormat("00", new DecimalFormatSymbols(Locale.US));
    private static DecimalFormat modelDF = new DecimalFormat("000000", new DecimalFormatSymbols(Locale.US));
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private String startTime = "";
    private String finishTime = "";
    private Date date = new Date();
    JalaliCalendar jalali = new JalaliCalendar();


    //first column
    @FXML
    private Label deviceNameLB;
    @FXML
    private RadioButton accountRB;
    @FXML
    private RadioButton usualRB;
    @FXML
    private TextField idTF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField chargeTF;
    @FXML
    private Button checkBTN;
    @FXML
    private TextField messageTF;
    @FXML
    private CheckBox hasTicketCHB;
    private ToggleGroup toggleGroup = new ToggleGroup();

    //second column
    @FXML
    private Button startBTN;
    @FXML
    private Label startSecond;
    @FXML
    private Label startMinute;
    @FXML
    private Label startHour;
    @FXML
    private Button finishBTN;
    @FXML
    private Label finishSecond;
    @FXML
    private Label finishMinute;
    @FXML
    private Label finishHour;
    @FXML
    private Label elapsedSecond;
    @FXML
    private Label elapsedMinute;
    @FXML
    private Label elapsedHour;
    @FXML
    private Button resetBTN;

    //third column
    @FXML
    private TextField incomeTF;
    @FXML
    private TextField leftTimeOfTicketTF;
    @FXML
    private TextField currentChargeTF;

    DeviceView(int deviceId) {
        this.deviceId = deviceId;
    }


    @FXML
    public void initialize() {
        jalali.setTime(date);
        //first col
        accountRB.setToggleGroup(toggleGroup);
        usualRB.setToggleGroup(toggleGroup);
        usualRB.setSelected(true);
        checkBTN.setOnAction(actionEvent -> customerIdentifier());
        deviceNameLB.setText(stringFormatter(20, deviceList.get(deviceId).getDeviceType()));
        startBTN.setOnAction(actionEvent -> {
            start();
        });
        finishBTN.setOnAction(actionEvent -> {
            stop();
        });
        resetBTN.setOnAction(actionEvent -> {
            reset();
        });
        elapsedSecond.setText("00");
        elapsedMinute.setText("00");
        elapsedHour.setText("00");
    }

    private void customerIdentifier() {
        Customer customer;
        if (accountRB.isSelected()) {
            customer = DBHelper.getInstance().getCustomer(Integer.valueOf(idTF.getText()));
            if (customer.getId() == 0 && customer.getFname().equals("") && customer.getLname().equals("")) {
                messageTF.setText("خطا: مشتری با این شناسه یافت نشد.");
                nameTF.clear();
                chargeTF.clear();
            } else {
                messageTF.clear();
                nameTF.setText(customer.getFname() + " " + customer.getLname());
                chargeTF.setText(String.valueOf(customer.getCharge()));
            }
        } else if (usualRB.isSelected()) {
            idTF.clear();
            nameTF.clear();
            chargeTF.clear();
            messageTF.setText("خطا: جستجوی مشتری عادی امکان پذیر نیست.");
        }
    }

    private void timeCalculator(String id) {
        //String time = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
        if (id.equals("startBTN")) {
            startTime = new SimpleDateFormat("HHmmss").format(new Date().getTime());
            startSecond.setText(timerDF.format(Integer.valueOf(startTime.substring(4, 6))));
            startMinute.setText(timerDF.format(Integer.valueOf(startTime.substring(2, 4))));
            startHour.setText(timerDF.format(Integer.valueOf(startTime.substring(0, 2))));
        } else if (id.equals("finishBTN")) {
            finishTime = new SimpleDateFormat("HHmmss").format(new Date().getTime());
            finishSecond.setText(timerDF.format(Integer.valueOf(finishTime.substring(4, 6))));
            finishMinute.setText(timerDF.format(Integer.valueOf(finishTime.substring(2, 4))));
            finishHour.setText(timerDF.format(Integer.valueOf(finishTime.substring(0, 2))));
        }

    }

    private void start() {
        if (!startClicked) {
            running = true;
            timeCalculator(startBTN.getId());
            Thread thread =
                    new Thread(() -> {
                        while (true) {
                            if (running) {
                                //if(!stopOrResetClicked){
                                try {
                                    Thread.sleep(1);
                                    if (seconds > 59) {
                                        seconds = 0;
                                        minutes++;
                                    }
                                    //else seconds++;
                                    if (minutes > 59) {
                                        seconds = 0;
                                        minutes = 0;
                                        hours++;
                                    }
                                    //if (!stopOrResetClicked)
                                    seconds++;
                                    if ((seconds != 60)) {
                                        Platform.runLater(() -> {
                                            elapsedSecond.setText(timerDF.format(seconds));
                                            elapsedMinute.setText(timerDF.format(minutes));
                                            elapsedHour.setText(timerDF.format(hours));
                                        });
                                    } else {
                                        Platform.runLater(() -> {
                                            elapsedSecond.setText("00");
                                            if (((minutes) + 1) != 60) {
                                                elapsedMinute.setText(timerDF.format((minutes) + 1));
                                                elapsedHour.setText(timerDF.format(hours));
                                            } else {
                                                elapsedMinute.setText("00");
                                                elapsedHour.setText(timerDF.format((hours) + 1));
                                            }
                                        });
                                    }

                                    System.out.println(elapsedHour.getText() + " : " + elapsedMinute.getText() + " : " + elapsedSecond.getText());
                                } catch (InterruptedException e) {
                                    e.getMessage();
                                }//}
                            } else break;
                        }
                    });
            thread.start();
            startClicked = true;
        }
    }

    private void stop() {
        if (!stopClicked) {
            timeCalculator(finishBTN.getId());
            running = false;
            stopClicked = true;
            income();
        }
    }

    private void reset() {
        if (!stopClicked) {
            messageTF.setText("هنوز دکمه توقف فشرده نشده است.");
        } else {
            messageTF.clear();
            running = false;
            stopClicked = false;
            startClicked = false;
            seconds = 0;
            minutes = 0;
            hours = 0;
            elapsedSecond.setText("00");
            elapsedMinute.setText("00");
            elapsedHour.setText("00");
            startSecond.setText("00");
            startMinute.setText("00");
            startHour.setText("00");
            finishSecond.setText("00");
            finishMinute.setText("00");
            finishHour.setText("00");
            usualRB.setSelected(true);
            hasTicketCHB.setSelected(false);
            nameTF.clear();
            chargeTF.clear();
            incomeTF.clear();
            currentChargeTF.clear();
            leftTimeOfTicketTF.clear();
            idTF.clear();

        }
    }

    private void income() {
        if (accountRB.isSelected() && !hasTicketCHB.isSelected()) {
            long income = Long.valueOf(elapsedSecond.getText()) * (SettingPage.price / 3600) +
                    Long.valueOf(elapsedMinute.getText()) * (SettingPage.price / 60) +
                    Long.valueOf(elapsedHour.getText()) * (SettingPage.price);
            incomeTF.setText(String.valueOf(income));
            Play play = new Play(modelDF.format(Long.valueOf(startTime)), modelDF.format(Long.valueOf(finishTime)), income, dateFormat.format(date), Integer.valueOf(idTF.getText()), deviceList.get(deviceId).getDeviceId());
            DBHelper.getInstance().play(play);
            currentChargeTF.setText(String.valueOf(DBHelper.getInstance().updateCharge(play.getUid(), (int) play.getIncome())));
            ticketTime(play.getUid());
        } else if (accountRB.isSelected() && hasTicketCHB.isSelected()) {
            long income = Long.valueOf(elapsedSecond.getText()) * (SettingPage.discountPrice / 3600) +
                    Long.valueOf(elapsedMinute.getText()) * (SettingPage.discountPrice / 60) +
                    Long.valueOf(elapsedHour.getText()) * (SettingPage.discountPrice);
            incomeTF.setText(String.valueOf(income));
            Play play = new Play(modelDF.format(Long.valueOf(startTime)), modelDF.format(Long.valueOf(finishTime)), income, dateFormat.format(date), Integer.valueOf(idTF.getText()), deviceList.get(deviceId).getDeviceId());
            DBHelper.getInstance().play(play);
            currentChargeTF.setText(String.valueOf(DBHelper.getInstance().updateCharge(play.getUid(), (int) play.getIncome())));
            ticketTime(play.getUid());
        } else if (usualRB.isSelected()) {
            long income = Long.valueOf(elapsedSecond.getText()) * (SettingPage.price / 3600) +
                    Long.valueOf(elapsedMinute.getText()) * (SettingPage.price / 60) +
                    Long.valueOf(elapsedHour.getText()) * (SettingPage.price);
            incomeTF.setText(String.valueOf(income));
            Play play = new Play(modelDF.format(Long.valueOf(startTime)), modelDF.format(Long.valueOf(finishTime)), income, dateFormat.format(jalali.get(Calendar.DATE)), deviceList.get(deviceId).getDeviceId());
            DBHelper.getInstance().play(play);
        }
    }

    private String stringFormatter(int maxLengh, String string) {
        int spaces = (maxLengh - string.length()) / 2;
        int lowSpaces;
        int highSpaces;
        if ((maxLengh - string.length()) % 2 == 1) {
            lowSpaces = spaces;
            highSpaces = spaces + 1;
        } else lowSpaces = highSpaces = spaces;
        for (int i = 0; i < lowSpaces; i++) string = " " + string;
        for (int i = 0; i < highSpaces; i++) string = string + " ";
        return string;
    }

    private void ticketTime(int customerId) {
        int thisPlay = (Integer.valueOf(elapsedHour.getText()) * 60)
                + Integer.valueOf(elapsedMinute.getText());
        int accountTime = DBHelper.getInstance().getCustomer(customerId).getTicket_hour();
        //if the result was equal or more than base -> show the leftTime, show message give a ticket, save left to account
        //else show the leftTime, save leftTime to account
        int result = thisPlay + accountTime;
        System.out.println(result);
        if(result >= SettingPage.ticketMinutes){
            int updatedTime = result % SettingPage.ticketMinutes;
            DBHelper.getInstance().updateTicketTime(customerId, updatedTime);
            leftTimeOfTicketTF.setText(((updatedTime) / 60) +
                    " ساعت و " + ((updatedTime) % 60) + " دقیقه");
            Alert alert = new Alert(Alert.AlertType.NONE,"برای کاربر بن تخفیف صادر شود", new ButtonType("بستن", ButtonBar.ButtonData.OK_DONE));
            alert.show();
        }else {
            DBHelper.getInstance().updateTicketTime(customerId, result);
            leftTimeOfTicketTF.setText(((result) / 60) +
                    " ساعت و " + ((result) % 60) + " دقیقه");
        }
    }
}
