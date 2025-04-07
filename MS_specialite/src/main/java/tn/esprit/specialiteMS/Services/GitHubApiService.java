package tn.esprit.specialiteMS.Services;

import tn.esprit.specialiteMS.entities.GitHubRepository;

import java.util.List;

public interface GitHubApiService {
    List<GitHubRepository> findRepositoriesBySpecialization(String specialization);
    List<GitHubRepository> findTopRepositoriesBySpecialization(String specialization, int limit);
} 