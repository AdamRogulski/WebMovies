package xd.webmovies.user.authority;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {
    Authority findByType(AuthorityType type);
}
