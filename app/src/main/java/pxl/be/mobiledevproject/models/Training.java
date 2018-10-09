package pxl.be.mobiledevproject.models;

import java.time.LocalDateTime;


public class Training {


    public Training(int id, String localDateTime, String necessities, String location, String title, int isAdult) {
        this.id = id;
        this.localDateTime = localDateTime;
        this.necessities = necessities;
        this.location = location;
        this.title = title;

        if (isAdult == 1){
            this.isAdult = true;
        } else {
            this.isAdult = false;
        }
    }

    public static final String TABLE_NAME = "trainings";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NECESSITIES = "necessities";
    public static final String COLUMN_LOCALDATETIME = "localDateTime";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ISADULT = "isAdult";
    public static final String COLUMN_LOCATION = "location";



    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NECESSITIES + " TEXT,"
                    + COLUMN_LOCALDATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_LOCATION + " TEXT,"
                    + COLUMN_ISADULT + " INTEGER,"
                    + ")";


        private int id;
        private String localDateTime;
        private String necessities;
        private String location;
        private String title;
        private boolean isAdult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getNecessities() {
        return necessities;
    }

    public void setNecessities(String necessities) {
        this.necessities = necessities;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }
}
