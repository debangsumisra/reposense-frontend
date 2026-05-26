package com.reposense.backend.dto;

import lombok.Data;

@Data
public class GitHubRepoDTO {

    private String name;
    private String description;
    private String language;
}