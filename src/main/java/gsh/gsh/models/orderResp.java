package gsh.gsh.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;

@Entity
public class orderResp {

    @Id
    private String id;
    private String title;
    private int updated;
    private String url;

    public orderResp() {
    }

    public orderResp(String id, String title,int updated , String url) {
        this.id      = id;
        this.title   = title;
        this.url     = url;
        this.updated = updated;
    }

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
