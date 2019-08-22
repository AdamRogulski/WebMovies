package xd.webmovies.media.television;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyTVShowRepository extends JpaRepository<MyTVShow,Long> {
    List<MyTVShow> findTop8ByOrderByCreationTimeDesc();
    List<MyTVShow> findAllByTvShow_Id(Long id);
}
