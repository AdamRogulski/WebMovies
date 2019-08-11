package xd.webmovies.media.television;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TVShowRepository extends JpaRepository<TVShow,Long> {
    boolean existsByTitle(String title);
}
