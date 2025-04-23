package com.example.restaurantms.services;

import com.example.restaurantms.entities.Restaurant;
import com.example.restaurantms.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RestaurantServiceImpl implements IRestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, FileStorageService fileStorageService) {
        this.restaurantRepository = restaurantRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(int id, Restaurant newRestaurant) {
        if (restaurantRepository.findById(id).isPresent()) {
            Restaurant existingRestaurant = restaurantRepository.findById(id).get();
            existingRestaurant.setNomRestaurant(newRestaurant.getNomRestaurant());
            existingRestaurant.setMenu(newRestaurant.getMenu());
            existingRestaurant.setImageUrl(newRestaurant.getImageUrl());
            existingRestaurant.setSpecialite(newRestaurant.getSpecialite());

            return restaurantRepository.save(existingRestaurant);
        } else
            return null;
    }


    public Restaurant getRestaurantId(int id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @Override
    public String deleteRestaurant(int id) {
        if (restaurantRepository.findById(id).isPresent()) {
            restaurantRepository.deleteById(id);
            return "restaurant supprimé";
        } else
            return "restaurant non supprimé";
    }
    
    @Override
    public Restaurant handleImageFileUpload(MultipartFile fileImage, int id) {
        try {
            if (fileImage == null || fileImage.isEmpty()) {
                return null;
            }
            
            Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
            if (restaurant == null) {
                return null;
            }
            
            String fileName = fileStorageService.storeFile(fileImage);
            restaurant.setImageUrl(fileName);
            return restaurantRepository.save(restaurant);
        } catch (Exception e) {
            System.err.println("Error uploading image for restaurant: " + e.getMessage());
            return null;
        }
    }
}

