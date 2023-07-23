package gsh.gsh.models;

import java.time.LocalDateTime;

public class VisualObj {
    private String productId;
    private String url;
    private String title;
    private int updateScore;
    private int updateScoreLastDay;
    private LocalDateTime startAt;

    public VisualObj() {
    }

    public VisualObj(String productId, String url, String title, int updateScore, int updateScoreLastDay, LocalDateTime startAt) {
        this.productId = productId;
        this.url = url;
        this.title = title;
        this.updateScore = updateScore;
        this.updateScoreLastDay = updateScoreLastDay;
        this.startAt = startAt;
    }

    public VisualObj(String url, String title, int updateScore, int updateScoreLastDay, LocalDateTime startAt) {
        this.url = url;
        this.title = title;
        this.updateScore = updateScore;
        this.updateScoreLastDay = updateScoreLastDay;
        this.startAt = startAt;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUpdateScore() {
        return updateScore;
    }

    public void setUpdateScore(int updateScore) {
        this.updateScore = updateScore;
    }

    public int getUpdateScoreLastDay() {
        return updateScoreLastDay;
    }

    public void setUpdateScoreLastDay(int updateScoreLastDay) {
        this.updateScoreLastDay = updateScoreLastDay;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }
}
