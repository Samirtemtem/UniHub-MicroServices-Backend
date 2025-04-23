package tn.esprit.specialiteMS.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.specialiteMS.Services.GitHubApiService;
import tn.esprit.specialiteMS.Services.SpecialiteService;
import tn.esprit.specialiteMS.entities.GitHubRepository;
import tn.esprit.specialiteMS.entities.Specialite;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/github")
public class GitHubApiController {

    private final GitHubApiService gitHubApiService;
    private final SpecialiteService specialiteService;

    @Autowired
    public GitHubApiController(GitHubApiService gitHubApiService, SpecialiteService specialiteService) {
        this.gitHubApiService = gitHubApiService;
        this.specialiteService = specialiteService;
    }

    @GetMapping("/repositories/{specialization}")
    public List<GitHubRepository> getRepositoriesBySpecialization(@PathVariable String specialization) {
        return gitHubApiService.findRepositoriesBySpecialization(specialization);
    }

    @GetMapping("/repositories/{specialization}/top/{limit}")
    public List<GitHubRepository> getTopRepositoriesBySpecialization(
            @PathVariable String specialization,
            @PathVariable int limit) {
        return gitHubApiService.findTopRepositoriesBySpecialization(specialization, limit);
    }

    @GetMapping("/repositories/specialite/{specialiteId}")
    public List<GitHubRepository> getRepositoriesBySpecialiteId(@PathVariable long specialiteId) {
        Specialite specialite = specialiteService.getById(specialiteId);
        if (specialite == null) {
            throw new RuntimeException("Specialite not found with id: " + specialiteId);
        }
        return gitHubApiService.findRepositoriesBySpecialization(specialite.getNom());
    }

    @GetMapping("/repositories/specialite/{specialiteId}/top/{limit}")
    public List<GitHubRepository> getTopRepositoriesBySpecialiteId(
            @PathVariable long specialiteId,
            @PathVariable int limit) {
        Specialite specialite = specialiteService.getById(specialiteId);
        if (specialite == null) {
            throw new RuntimeException("Specialite not found with id: " + specialiteId);
        }
        return gitHubApiService.findTopRepositoriesBySpecialization(specialite.getNom(), limit);
    }
} 