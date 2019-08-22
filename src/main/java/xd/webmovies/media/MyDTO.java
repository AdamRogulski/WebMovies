package xd.webmovies.media;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class MyDTO {

   private String comment;
   private String status;
   private int rating;
   private String author;
   private String title;
   private LocalDateTime creationTime;

    public MyDTO() {
    }

    public MyDTO(String comment, String status, int rating) {
        this.comment = comment;
        this.status = status;
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonFormat(pattern = "HH:mm dd.MM.yyyy")
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
