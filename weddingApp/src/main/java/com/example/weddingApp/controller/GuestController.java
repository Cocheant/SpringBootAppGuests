package com.example.weddingApp.controller;

import com.example.weddingApp.model.FoodRestriction;
import com.example.weddingApp.model.Guest;
import com.example.weddingApp.service.FoodRestrictionService;
import com.example.weddingApp.service.GuestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.common.util.concurrent.RateLimiter;

import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final RateLimiter rateLimiter = RateLimiter.create(0.5);

    @Autowired
    private GuestService guestService;

    @Autowired
    private FoodRestrictionService foodRestrictionService;

    @PostMapping("/check")
    public ResponseEntity<?> checkGuest(@RequestParam String guestName, @RequestParam String email) {

        if (!rateLimiter.tryAcquire()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Too many requests. Please try again later.");
        }

        Optional<Guest> guest = guestService.findGuestByNameAndEmail(guestName, email);

        if (guest.isPresent()) {
            // Guest found, return their data to pre-fill the form
            return ResponseEntity.ok("");
        } else {
            // Guest not found, return a message or 404 response
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Guest with the provided name and email does not exist.");

        }
    }

    @PutMapping("/{id}")
    public Guest updateGuest(@PathVariable Long id, @RequestBody Guest guestDetails) {
        Guest guest = guestService.getGuestById(id)
                .orElseThrow(() -> new RuntimeException("Guest not found"));

        guest.setGuestName(guestDetails.getGuestName());
        guest.setEmail(guestDetails.getEmail());
        guest.setRSVP(guestDetails.getRSVP());
        guest.setFoodRestrictions(guestDetails.getFoodRestrictions());
        return guestService.updateGuest(guest);
    }

    @GetMapping("/food-restrictions")
    public List<FoodRestriction> getAllFoodRestrictions() {
        return foodRestrictionService.getAllFoodRestrictions();
    }

}
