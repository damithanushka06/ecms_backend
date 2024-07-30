package com.ecms.backend.ecmsapi.service_impl.complain;

import com.ecms.backend.ecmsapi.constant.CommonStatus;
import com.ecms.backend.ecmsapi.constant.ComplainStatusConstants;
import com.ecms.backend.ecmsapi.models.chart.ChartDto;
import com.ecms.backend.ecmsapi.models.complain.ComplainDto;
import com.ecms.backend.ecmsapi.models.complain.ComplainEntity;
import com.ecms.backend.ecmsapi.models.user.User;
import com.ecms.backend.ecmsapi.repository.complain.ComplainRepository;
import com.ecms.backend.ecmsapi.repository.user.UserRepository;
import com.ecms.backend.ecmsapi.service.complain.ComplainService;
import com.ecms.backend.ecmsapi.service_impl.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ComplainServiceImpl implements ComplainService {
    @Autowired
    ComplainRepository complainRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ComplainEntity createComplain(ComplainEntity complainEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        complainEntity.setCreatedBy(currentUserName);
        complainEntity.setCreatedDate(LocalDate.now());
        complainEntity.setStatus(CommonStatus.PENDING.getStatus());
        return complainRepository.save(complainEntity);
    }

    @Override
    public List<ComplainDto> getAllComplainByUser() {
        String currentUserName = getUserName();
        User uerMaster;
        if (currentUserName != null) {
            Optional<User> user = userRepository.findByUsername(currentUserName);
            uerMaster = user.get();
            return complainRepository.findAllComplainByRole(uerMaster.getRoleName(), currentUserName);
        }
        ;
        return null;
    }

    @Override
    public List<ComplainEntity> getAllComplaints() {
        String currentUserName = getUserName();
        if (currentUserName != null) {
            return complainRepository.findAll();
        }
        ;
        return null;
    }

    @Override
    public List<ComplainDto> getAllComplainByUserAndStatus(String status) {
        String currentUserName = getUserName();
        if (currentUserName != null) {
            Optional<User> user = userRepository.findByUsername(currentUserName);
            if (user.isPresent()) {
                return complainRepository.findAllComplainByRoleAndStatus(user.get().getRoleName(), currentUserName, status);
            } else {
                return null;
            }
        }
        ;
        return null;
    }

    private static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @Override
    public ComplainEntity getComplainDetailById(long complainId) {
        return complainRepository.findById(complainId).orElse(null);
    }

    @Override
    public ResponseEntity<?> updateComplain(Long complainId, String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Optional<ComplainEntity> savedComplainEntity = complainRepository.findById(complainId);
        if (savedComplainEntity.isPresent()) {
            ComplainEntity complainEntity = savedComplainEntity.get();
            switch (status) {
                case ComplainStatusConstants.APPROVED:
                    complainEntity.setStatus(ComplainStatusConstants.getStatus(ComplainStatusConstants.APPROVED));
                    break;
                case ComplainStatusConstants.REJECTED:
                    complainEntity.setStatus(ComplainStatusConstants.getStatus(ComplainStatusConstants.REJECTED));
                    break;
                case ComplainStatusConstants.INVESTIGATION:
                    complainEntity.setStatus(ComplainStatusConstants.getStatus(ComplainStatusConstants.INVESTIGATION));
                    break;
                case ComplainStatusConstants.CLOSED:
                    complainEntity.setStatus(ComplainStatusConstants.getStatus(ComplainStatusConstants.CLOSED));
                    break;
            }
            complainEntity.setUpdateBy(currentUserName);
            complainEntity.setUpdatedOn(LocalDate.now());
            complainRepository.save(complainEntity);
        }
        return null;
    }

    @Override
    public List<ChartDto> getPieChartData() {
        return complainRepository.getChartData();
    }

    @Override
    public List<List<Integer>> getBarChartData() {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> wildlifeCounts = getWildlifeCrimeCountsByProvince();
        List<Integer> forestCounts = getForestCrimeCountsByProvince();
        result.add(wildlifeCounts);
        result.add(forestCounts);
        return result;
    }

    public List<Integer> getWildlifeCrimeCountsByProvince() {
        List<Integer> wildlifeCounts = new ArrayList<>();
        List<String> provinces = Arrays.asList(
                "Central", "Southern", "Northern","Western", "Eastern", "North-Western", "North-Central", "Uva", "Sabaragamuwa"
        );

        for (String province : provinces) {
            int count = complainRepository.getCountOfWildlifeByProvince(province);
            wildlifeCounts.add(count);
        }

        return wildlifeCounts;
    }

    public List<Integer> getForestCrimeCountsByProvince() {
        List<Integer> forestCounts = new ArrayList<>();
        List<String> provinces = Arrays.asList(
                "Central", "Southern", "Northern","Western", "Eastern", "North-Western", "North-Central", "Uva", "Sabaragamuwa"
        );

        for (String province : provinces) {
            int count = complainRepository.getCountOfForestByProvince(province);
            forestCounts.add(count);
        }

        return forestCounts;
    }
}
