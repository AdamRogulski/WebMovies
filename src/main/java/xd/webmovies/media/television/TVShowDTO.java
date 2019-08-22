package xd.webmovies.media.television;

import com.fasterxml.jackson.annotation.JsonFormat;
import xd.webmovies.media.MediaDTO;

import java.time.LocalDateTime;

public class TVShowDTO extends MediaDTO {


   private int episodes;

    @JsonFormat(pattern = "HH:mm dd.MM.yyyy")
    private LocalDateTime showAddedTime;

    public TVShowDTO() {
    }

    public TVShowDTO(String title, int year, String description, int episodes, String image) {
        super(image, description, title, year);
        this.episodes = episodes;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public LocalDateTime getShowAddedTime() {
        return showAddedTime;
    }

    public void setShowAddedTime(LocalDateTime showAddedTime) {
        this.showAddedTime = showAddedTime;
    }


}
