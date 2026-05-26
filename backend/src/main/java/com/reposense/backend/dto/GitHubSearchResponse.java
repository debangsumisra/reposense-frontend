package com.reposense.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class GitHubSearchResponse {

    private List<GitHubItemDTO> items;
}