//package com.kitnet.kitnet.service;
//
//import com.kitnet.kitnet.dto.user.EmploymentHistoryDTO;
//import com.kitnet.kitnet.model.EmploymentHistory;
//import com.kitnet.kitnet.model.User;
//import com.kitnet.kitnet.model.enums.VerificationStatus;
//import com.kitnet.kitnet.exception.UserNotFoundException; // Reutilizando UserNotFoundException
//import com.kitnet.kitnet.exception.ResourceNotFoundException; // Uma nova exceção para recursos não encontrados
//
//import java.util.List;
//import java.util.UUID;
//
//public interface EmploymentHistoryService {
//    EmploymentHistory saveOrUpdateEmploymentHistory(User user, EmploymentHistoryDTO dto);
//    List<EmploymentHistory> getUserEmploymentHistory(UUID userId) throws UserNotFoundException;
//    EmploymentHistory updateEmploymentHistoryStatus(UUID employmentId, VerificationStatus status, String rejectionReason) throws ResourceNotFoundException;
//}
