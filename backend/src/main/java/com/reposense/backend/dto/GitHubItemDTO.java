package com.reposense.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitHubItemDTO {

    private String name;

    private String description;

    private String language;

    @JsonProperty("stargazers_count")
    private Integer stars;

    private OwnerDTO owner;
}