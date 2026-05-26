package com.reposense.backend.controller;

import com.reposense.backend.model.RepositoryEntity;
import com.reposense.backend.service.RepositoryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/repositories")
@CrossOrigin(origins = "http://localhost:5174")
public class RepositoryController {

    private final RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

   @GetMapping
public Page<RepositoryEntity> getRepositories(

        @RequestParam(defaultValue = "0")
        int page,

        @RequestParam(defaultValue = "10")
        int size) {

    return repositoryService
            .getRepositories(page, size);
}
@GetMapping("/top")
public List<RepositoryEntity> getTopRepositories() {

    return repositoryService.getTopRepositories();
}
@GetMapping("/ranked")
public List<RepositoryEntity>
getTopRankedRepositories() {

    return repositoryService
            .getTopRankedRepositories();
}

    @PostMapping
    public RepositoryEntity createRepository(
            @RequestBody RepositoryEntity repositoryEntity) {

        return repositoryService.saveRepository(repositoryEntity);
    }
    @GetMapping("/search")
public List<RepositoryEntity> searchRepositories(
        @RequestParam String keyword) {

    return repositoryService.searchRepositories(keyword);
}
}