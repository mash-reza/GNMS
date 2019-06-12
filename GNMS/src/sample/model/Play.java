package sample.model;


public class Play {

    private String start_hour;
    private String finish_hour;
    private String date_played;
    private long income;
    private int uid = -1;
    private int did;

    public Play(String start_hour, String finish_hour, long income, String date_played, int did) {
        this.start_hour = start_hour;
        this.finish_hour = finish_hour;
        this.date_played = date_played;
        this.income = income;
        this.did = did;
    }

    public Play(String start_hour, String finish_hour, long income, String date_played, int uid, int did) {
        this.start_hour = start_hour;
        this.finish_hour = finish_hour;
        this.date_played = date_played;
        this.income = income;
        this.uid = uid;
        this.did = did;
    }

    public String getStart_hour() {
        return start_hour;
    }

    public String getFinish_hour() {
        return finish_hour;
    }

    public String getDate_played() {
        return date_played;
    }

    public long getIncome() {
        return income;
    }

    public int getUid() {
        return uid;
    }

    public int getDid() {
        return did;
    }
}
