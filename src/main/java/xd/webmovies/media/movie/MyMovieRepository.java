package xd.webmovies.media.movie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyMovieRepository extends JpaRepository<MyMovie,Long> {
}
