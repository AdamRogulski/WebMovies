package xd.webmovies.media.movie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyMovieRepository extends JpaRepository<MyMovie,Long> {
    List<MyMovie> findTop8ByOrderByCreationTimeDesc();
    List<MyMovie> findAllByMovie_Id(Long id);
}
