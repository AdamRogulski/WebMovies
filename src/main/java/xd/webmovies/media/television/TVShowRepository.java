package xd.webmovies.media.television;

import org.springframework.data.jpa.repository.JpaRepository;
import xd.webmovies.media.MediaDTO;

import java.util.List;

public interface TVShowRepository extends JpaRepository<TVShow,Long> {
    boolean existsByTitle(String title);
    List<TVShow> findTop10ByOrderByShowAddedTimeDesc();
    List<TVShow> findAllByTitleContains(String title);
}
