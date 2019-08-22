package xd.webmovies.media.movie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    boolean existsByTitle(String title);
    List<Movie> findTop10ByOrderByMovieAddedTimeDesc();
    List<Movie> findAllByTitleContains(String title);
}
