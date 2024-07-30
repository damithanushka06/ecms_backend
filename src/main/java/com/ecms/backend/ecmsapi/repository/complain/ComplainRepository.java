package com.ecms.backend.ecmsapi.repository.complain;
import com.ecms.backend.ecmsapi.models.chart.ChartDto;
import com.ecms.backend.ecmsapi.models.complain.ComplainDto;
import com.ecms.backend.ecmsapi.models.complain.ComplainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComplainRepository extends JpaRepository<ComplainEntity, Long> {

    /**
     * Retrieves a list of ComplainDto objects based on the provided username.
     *
     * @param username The username used to fetch complaints associated with the user.
     * @return List of ComplainDto objects representing complaints created by the specified user.
     */
    @Query("SELECT NEW com.ecms.backend.ecmsapi.models.complain.ComplainDto" +
            "(complainMaster.id, complainMaster.crimeType, complainMaster.description," +
            "complainMaster.location, complainMaster.createdBy, complainMaster.createdDate, complainMaster.status, complainMaster.updatedOn) " +
            "FROM ComplainEntity complainMaster " +
            "WHERE " +
            "(:role = 'ROLE_ADMIN' AND complainMaster.status in ('Pending')) OR " +
            "(:role = 'ROLE_PUBLIC_USER' AND complainMaster.createdBy = :username) OR " +
            "(:role = 'ROLE_WILDLIFE_OFFICER' AND complainMaster.crimeType = 'Wildlife' AND (complainMaster.status = 'Approved' OR complainMaster.status = 'Investigation' OR complainMaster.status = 'Closed')) OR " +
            "(:role = 'ROLE_FOREST_OFFICER' AND complainMaster.crimeType = 'Forest' AND (complainMaster.status = 'Approved' OR complainMaster.status = 'Investigation' OR complainMaster.status = 'Closed'))")
    List<ComplainDto> findAllComplainByRole(@Param("role") String role, @Param("username") String username);

    @Query("SELECT NEW com.ecms.backend.ecmsapi.models.complain.ComplainDto" +
            "(complainMaster.id, complainMaster.crimeType, complainMaster.description," +
            "complainMaster.location, complainMaster.createdBy, complainMaster.createdDate, complainMaster.status, complainMaster.updatedOn) " +
            "FROM ComplainEntity complainMaster " +
            "WHERE " +
            "(:role = 'ROLE_ADMIN' AND complainMaster.status = :status) OR " +
            "(:role = 'ROLE_PUBLIC_USER' AND complainMaster.createdBy = :username AND complainMaster.status = :status) OR " +
            "(:role = 'ROLE_WILDLIFE_OFFICER' AND complainMaster.crimeType = 'Wildlife' AND complainMaster.status = :status) OR " +
            "(:role = 'ROLE_FOREST_OFFICER' AND complainMaster.crimeType = 'Forest' AND complainMaster.status = :status)")
    List<ComplainDto> findAllComplainByRoleAndStatus(@Param("role") String role, @Param("username") String username, @Param("status") String status);


    @Query("SELECT NEW com.ecms.backend.ecmsapi.models.chart.ChartDto(" +
            "1, COUNT(complainMaster.id), complainMaster.crimeType) " +
            "FROM ComplainEntity complainMaster " +
            "GROUP BY complainMaster.crimeType")
    List<ChartDto> getChartData();

    @Query("SELECT COUNT(c.id) FROM ComplainEntity c WHERE  c.location = :province AND c.crimeType = 'Wildlife'")
    int getCountOfWildlifeByProvince(@Param("province") String province);

    @Query("SELECT COUNT(c.id) FROM ComplainEntity c WHERE  c.location = :province AND c.crimeType = 'Forest'")
    int getCountOfForestByProvince(@Param("province") String province);
}
