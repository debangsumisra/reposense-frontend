package com.reposense.backend.service;

import com.reposense.backend.model.RepositoryEntity;
import com.reposense.backend.repository.RepositoryRepo;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class RepositoryService {

    private final RepositoryRepo repositoryRepo;
    private final GitHubService githubService;

    public RepositoryService(
            RepositoryRepo repositoryRepo,
            GitHubService githubService) {

        this.repositoryRepo = repositoryRepo;
        this.githubService = githubService;
    }

    public Page<RepositoryEntity> getRepositories(
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return repositoryRepo.findAll(pageable);
    }

    public List<RepositoryEntity> getTopRepositories() {

        return repositoryRepo
                .findTop10ByOrderByStarsDesc();
    }

    public List<RepositoryEntity>
    getTopRankedRepositories() {

        return repositoryRepo
                .findTop10ByOrderByScoreDesc();
    }

    public RepositoryEntity saveRepository(
            RepositoryEntity repositoryEntity) {

        return repositoryRepo.save(repositoryEntity);
    }

    public List<RepositoryEntity>
    searchRepositories(String keyword) {

        System.out.println("DATABASE SEARCH");

        List<RepositoryEntity> repositories =
                repositoryRepo.searchRepositories(keyword);

        // If DB has no results
        if (repositories.isEmpty()) {

            System.out.println(
                    "NO RESULTS IN DB -> FETCHING FROM GITHUB"
            );

            // Fetch from GitHub API and save
            githubService.searchRepositories(keyword);

            // Search DB again after saving
            repositories =
                    repositoryRepo.searchRepositories(keyword);
        }

        return repositories;
    }
}
