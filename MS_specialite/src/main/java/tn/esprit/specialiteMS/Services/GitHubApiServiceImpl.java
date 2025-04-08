package tn.esprit.specialiteMS.Services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tn.esprit.specialiteMS.entities.GitHubRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubApiServiceImpl implements GitHubApiService {

    private final WebClient webClient;
    private static final String GITHUB_API_BASE_URL = "https://api.github.com";
    
    @Value("${github.api.token:}")
    private String githubToken;

    public GitHubApiServiceImpl() {
        this.webClient = WebClient.builder()
                .baseUrl(GITHUB_API_BASE_URL)
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3+json")
                .build();
    }

    @Override
    @Cacheable(value = "githubRepositories", key = "#specialization")
    public List<GitHubRepository> findRepositoriesBySpecialization(String specialization) {
        // Search for repositories related to the specialization
        JsonNode responseNode = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/repositories")
                        .queryParam("q", specialization + " in:name,description,readme topic:education sort:stars")
                        .queryParam("per_page", 100)
                        .build())
                .headers(headers -> {
                    if (githubToken != null && !githubToken.isEmpty()) {
                        headers.set(HttpHeaders.AUTHORIZATION, "token " + githubToken);
                    }
                })
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        List<GitHubRepository> repositories = new ArrayList<>();
        
        if (responseNode != null && responseNode.has("items")) {
            JsonNode items = responseNode.get("items");
            for (JsonNode item : items) {
                GitHubRepository repo = mapToGitHubRepository(item);
                repositories.add(repo);
            }
        }
        
        return repositories;
    }

    @Override
    @Cacheable(value = "githubTopRepositories", key = "#specialization + '-' + #limit")
    public List<GitHubRepository> findTopRepositoriesBySpecialization(String specialization, int limit) {
        List<GitHubRepository> allRepositories = findRepositoriesBySpecialization(specialization);
        return allRepositories.stream()
                .sorted(Comparator.comparing(GitHubRepository::getStargazersCount).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    private GitHubRepository mapToGitHubRepository(JsonNode node) {
        GitHubRepository repository = new GitHubRepository();
        repository.setId(node.get("id").asLong());
        repository.setName(node.get("name").asText());
        repository.setFullName(node.get("full_name").asText());
        
        if (node.has("description") && !node.get("description").isNull()) {
            repository.setDescription(node.get("description").asText());
        }
        
        repository.setHtmlUrl(node.get("html_url").asText());
        
        if (node.has("language") && !node.get("language").isNull()) {
            repository.setLanguage(node.get("language").asText());
        }
        
        repository.setStargazersCount(node.get("stargazers_count").asInt());
        repository.setForksCount(node.get("forks_count").asInt());
        
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        repository.setCreatedAt(LocalDateTime.parse(node.get("created_at").asText(), formatter));
        repository.setUpdatedAt(LocalDateTime.parse(node.get("updated_at").asText(), formatter));
        
        return repository;
    }
} 