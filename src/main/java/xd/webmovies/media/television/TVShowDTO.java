package xd.webmovies.media.television;

import xd.webmovies.media.MediaDTO;

public class TVShowDTO extends MediaDTO {


   private int episodes;

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }
}
