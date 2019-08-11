package xd.webmovies.media.movie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    boolean existsByTitle(String title);
}
