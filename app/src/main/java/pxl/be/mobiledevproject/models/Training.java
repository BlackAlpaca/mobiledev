package pxl.be.mobiledevproject.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class Training {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private LocalDateTime localDateTime;

    //TODO: List<String>??
    private String necessities;

    private String location;

    private String title;

    private boolean isAdult;

    public Training(
            LocalDateTime localDateTime,
            String necessities,
            String location,
            String title,
            boolean isAdult) {
        this.localDateTime = localDateTime;
        this.necessities = necessities;
        this.location = location;
        this.title = title;
        this.isAdult = isAdult;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getNecessities() {
        return necessities;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAdult() {
        return isAdult;
    }
}
