package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.RentalRequestDTO;
import com.kitnet.kitnet.dto.RentalResponseDTO;
import com.kitnet.kitnet.dto.RentalUpdateDTO;
import com.kitnet.kitnet.model.User;

import java.util.List;
import java.util.UUID;

public interface RentalService {
    RentalResponseDTO createRental(RentalRequestDTO rentalDto, User authenticatedUser);
    List<RentalResponseDTO> findAllRentals(User authenticatedUser);
    RentalResponseDTO findRentalById(UUID id, User authenticatedUser);
    RentalResponseDTO updateRental(UUID id, RentalUpdateDTO updateDto, User authenticatedUser);
    void deleteRental(UUID id, User authenticatedUser);

    RentalResponseDTO proposePriceChange(UUID rentalId, Double newPrice, User requestingUser);
    RentalResponseDTO approvePriceChange(UUID rentalId, User approvingUser);
    RentalResponseDTO rejectPriceChange(UUID rentalId, User rejectingUser);
    RentalResponseDTO terminateContract(UUID rentalId, User requestingUser);
    RentalResponseDTO breakContract(UUID rentalId, User requestingUser);
}