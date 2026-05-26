package com.reposense.backend.service;

import com.reposense.backend.dto.GitHubItemDTO;
import com.reposense.backend.dto.GitHubSearchResponse;
import com.reposense.backend.model.RepositoryEntity;
import com.reposense.backend.repository.RepositoryRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubService {

    private final RestTemplate restTemplate;
    private final RepositoryRepo repositoryRepo;

    public GitHubService(
            RestTemplate restTemplate,
            RepositoryRepo repositoryRepo) {

        this.restTemplate = restTemplate;
        this.repositoryRepo = repositoryRepo;
    }

  

   public List<RepositoryEntity> searchRepositories(String query) {

    String url =
            "https://api.github.com/search/repositories?q=" + query;

    GitHubSearchResponse response =
            restTemplate.getForObject(
                    url,
                    GitHubSearchResponse.class
            );

    List<RepositoryEntity> repositories =
            new ArrayList<>();

    if (response != null && response.getItems() != null) {

        for (GitHubItemDTO item : response.getItems()) {

            boolean exists =
                    repositoryRepo.existsByRepoNameAndOwner(
                            item.getName(),
                            item.getOwner().getLogin()
                    );

            RepositoryEntity repository;

            if (!exists) {

                repository =
                        RepositoryEntity.builder()
                                .repoName(item.getName())
                                .owner(item.getOwner().getLogin())
                                .description(item.getDescription())
                                .stars(item.getStars())
                                .language(item.getLanguage())
                                .score(calculateScore(
                                        item.getStars(),
                                        item.getLanguage()
                                ))
                                .build();

                repositoryRepo.save(repository);

            } else {

                repository =
                        repositoryRepo
                                .findByRepoNameAndOwner(
                                        item.getName(),
                                        item.getOwner().getLogin()
                                );
            }

            repositories.add(repository);
        }
    }

    return repositories;
}
    private Double calculateScore(
        Integer stars,
        String language) {

    double score = 0;

    if (stars != null) {
        score += stars * 0.7;
    }

    if ("Java".equalsIgnoreCase(language)) {
        score += 1000;
    }

    return score;
}
}