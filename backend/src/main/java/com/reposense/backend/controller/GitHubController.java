package com.reposense.backend.controller;

import com.reposense.backend.model.RepositoryEntity;
import com.reposense.backend.service.GitHubService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/search")
    public List<RepositoryEntity> searchRepositories(
            @RequestParam String query) {

        return gitHubService.searchRepositories(query);
    }
}