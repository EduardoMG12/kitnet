package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.RentalRequestDTO;
import com.kitnet.kitnet.dto.RentalResponseDTO;
import com.kitnet.kitnet.dto.RentalUpdateDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping
    public ResponseEntity<RentalResponseDTO> createRental(@Valid @RequestBody RentalRequestDTO rentalDto,
                                                          @AuthenticationPrincipal User authenticatedUser) {
        RentalResponseDTO newRental = rentalService.createRental(rentalDto, authenticatedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRental);
    }

    @GetMapping
    public List<RentalResponseDTO> getAllRentals(@AuthenticationPrincipal User authenticatedUser) {
        return rentalService.findAllRentals(authenticatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> getRentalById(@PathVariable UUID id,
                                                           @AuthenticationPrincipal User authenticatedUser) {
        RentalResponseDTO rental = rentalService.findRentalById(id, authenticatedUser);
        return ResponseEntity.ok(rental);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> updateRental(@PathVariable UUID id,
                                                          @Valid @RequestBody RentalUpdateDTO updateDto,
                                                          @AuthenticationPrincipal User authenticatedUser) {
        RentalResponseDTO updatedRental = rentalService.updateRental(id, updateDto, authenticatedUser);
        return ResponseEntity.ok(updatedRental);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRental(@PathVariable UUID id,
                             @AuthenticationPrincipal User authenticatedUser) {
        rentalService.deleteRental(id, authenticatedUser);
    }
}