package gsh.gsh.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class product {
    @Id
    private String id;
    private String title;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private String url;
    private int updateScore;
    private int updateScoreLastDay;
    private LocalDateTime lastDay;
    public product() {
    }

    public product(String id, String title, LocalDateTime updatedAt, LocalDateTime createdAt, String url, int updateScore, int updateScoreLastDay, LocalDateTime lastDay) {
        this.id = id;
        this.title = title;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.url = url;
        this.updateScore = updateScore;
        this.updateScoreLastDay = updateScoreLastDay;
        this.lastDay = lastDay;
    }

    public product(String id, String title, LocalDateTime updatedAt, LocalDateTime createdAt, String url, int updateScore) {
        this.id          = id;
        this.title       = title;
        this.updatedAt   = updatedAt;
        this.createdAt   = createdAt;
        this.url         = url;
        this.updateScore = updateScore;
    }

    public int getUpdateScoreLastDay() {
        return updateScoreLastDay;
    }

    public void setUpdateScoreLastDay(int updateScoreLastDay) {
        this.updateScoreLastDay = updateScoreLastDay;
    }

    public LocalDateTime getLastDay() {
        return lastDay;
    }

    public void setLastDay(LocalDateTime lastDay) {
        this.lastDay = lastDay;
    }

    public int getUpdateScore() {
        return updateScore;
    }

    public void setUpdateScore(int updateScore) {
        this.updateScore = updateScore;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
