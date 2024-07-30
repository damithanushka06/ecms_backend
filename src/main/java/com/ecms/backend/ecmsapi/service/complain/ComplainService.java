package com.ecms.backend.ecmsapi.service.complain;
import com.ecms.backend.ecmsapi.models.chart.ChartDto;
import com.ecms.backend.ecmsapi.models.complain.ComplainDto;
import com.ecms.backend.ecmsapi.models.complain.ComplainEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ComplainService {

    /**
     * Creates a new complaint based on the provided complaint entity.
     *
     * @param complainEntity The ComplainEntity object containing information for creating a complaint.
     * @return The ComplainEntity object representing the newly created complaint.
     *         This method returns the created ComplainEntity after processing or storing it.
     *         Returns null if there's no specific return value.
     */
    ComplainEntity createComplain(ComplainEntity complainEntity);


    /**
     * Retrieves a list of ComplainDto objects associated with a user.
     *
     * This method serves as a declaration to retrieve complaints related to a specific user.
     * Implementing classes or subclasses should provide their own logic to fetch and return
     * a list of ComplainDto objects associated with the user.
     *
     * @return A list of ComplainDto objects representing complaints related to the user.
     */
    List<ComplainDto> getAllComplainByUser();

    List<ComplainEntity> getAllComplaints();

    List<ComplainDto> getAllComplainByUserAndStatus(String status);

    /**
     * get complain detail by id
     *
     * @param complainId to complain master id
     * @return a ComplainEntity
     */
    ComplainEntity getComplainDetailById(long complainId);

    ResponseEntity<?> updateComplain(Long complainId, String status);

    List<ChartDto> getPieChartData();

    List<List<Integer>> getBarChartData();
}
