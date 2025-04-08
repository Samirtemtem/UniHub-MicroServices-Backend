package tn.esprit.specialiteMS.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitHubRepository {
    private Long id;
    private String name;
    private String fullName;
    private String description;
    private String htmlUrl;
    private String language;
    private Integer stargazersCount;
    private Integer forksCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 