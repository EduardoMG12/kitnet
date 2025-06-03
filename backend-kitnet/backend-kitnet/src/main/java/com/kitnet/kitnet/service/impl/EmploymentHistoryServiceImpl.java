//package com.kitnet.kitnet.service.impl;
//
//import com.kitnet.kitnet.dto.user.EmploymentHistoryDTO;
//import com.kitnet.kitnet.model.EmploymentHistory;
//import com.kitnet.kitnet.model.User;
//import com.kitnet.kitnet.model.enums.VerificationStatus;
//import com.kitnet.kitnet.repository.EmploymentHistoryRepository;
//import com.kitnet.kitnet.repository.UserRepository;
//import com.kitnet.kitnet.service.EmploymentHistoryService;
//import com.kitnet.kitnet.exception.UserNotFoundException;
//import com.kitnet.kitnet.exception.ResourceNotFoundException; // Nova exceção
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class EmploymentHistoryServiceImpl implements EmploymentHistoryService {
//
//    @Autowired
//    private EmploymentHistoryRepository employmentHistoryRepository;
//
//    @Autowired
//    private UserRepository userRepository; // Necessário para buscar o User
//
//    @Autowired
//    private MessageSource messageSource;
//
//    @Override
//    @Transactional
//    public EmploymentHistory saveOrUpdateEmploymentHistory(User user, EmploymentHistoryDTO dto) {
//        EmploymentHistory employment;
//        if (dto.getId() != null) {
//            // Tenta encontrar o histórico de emprego existente para o usuário
//            employment = employmentHistoryRepository.findByIdAndUser(dto.getId(), user)
//                    .orElse(new EmploymentHistory()); // Cria um novo se não encontrar ou se o ID não pertencer ao usuário
//            // Se o ID do DTO existe mas não pertence ao usuário, isso é um erro de segurança
//            if (employment.getId() != null && !employment.getUser().getId().equals(user.getId())) {
//                throw new ResourceNotFoundException(messageSource.getMessage("error.employment.history.not.found.for.user", null, LocaleContextHolder.getLocale()));
//            }
//        } else {
//            employment = new EmploymentHistory();
//        }
//
//        employment.setUser(user);
//        employment.setCompanyName(dto.getCompanyName());
//        employment.setRole(dto.getRole());
//        employment.setMonthlyGrossSalary(dto.getMonthlyGrossSalary());
//        employment.setStartDate(dto.getStartDate());
//        employment.setEndDate(dto.getEndDate());
//        employment.setIsCurrentJob(dto.getIsCurrentJob());
//        employment.setRecordUploadDate(LocalDate.now());
//        employment.setVerificationStatus(VerificationStatus.NOT_SUBMITTED); // Resetar status para nova revisão
//        employment.setRejectionReason(null);
//
//        return employmentHistoryRepository.save(employment);
//    }
//
//    @Override
//    public List<EmploymentHistory> getUserEmploymentHistory(UUID userId) throws UserNotFoundException {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.user.not.found", null, LocaleContextHolder.getLocale())));
//        return employmentHistoryRepository.findByUser(user);
//    }
//
//    @Override
//    @Transactional
//    public EmploymentHistory updateEmploymentHistoryStatus(UUID employmentId, VerificationStatus status, String rejectionReason) throws ResourceNotFoundException {
//        EmploymentHistory employment = employmentHistoryRepository.findById(employmentId)
//                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("error.employment.history.not.found", null, LocaleContextHolder.getLocale())));
//
//        employment.setVerificationStatus(status);
//        employment.setRejectionReason(rejectionReason);
//
//        return employmentHistoryRepository.save(employment);
//    }
//}
