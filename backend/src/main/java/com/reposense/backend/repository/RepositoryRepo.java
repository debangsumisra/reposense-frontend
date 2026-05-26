package com.reposense.backend.repository;

import com.reposense.backend.model.RepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositoryRepo
        extends JpaRepository<RepositoryEntity, Long> {

    List<RepositoryEntity>
    findByRepoNameContainingIgnoreCase(String keyword);

 boolean existsByRepoNameAndOwner(
        String repoName,
        String owner
);
RepositoryEntity findByRepoNameAndOwner(
        String repoName,
        String owner
);
    List<RepositoryEntity>
    findTop10ByOrderByStarsDesc();

    List<RepositoryEntity>
    findTop10ByOrderByScoreDesc();
    @Query(value = """
SELECT *
FROM repositories
WHERE search_vector @@ plainto_tsquery(:keyword)
ORDER BY ts_rank(
    search_vector,
    plainto_tsquery(:keyword)
) DESC
""",
nativeQuery = true)
List<RepositoryEntity> searchRepositories(
        @Param("keyword") String keyword
);
}