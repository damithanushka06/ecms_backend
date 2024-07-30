package com.ecms.backend.ecmsapi.controller.complain;
import com.ecms.backend.ecmsapi.models.complain.ComplainDto;
import com.ecms.backend.ecmsapi.models.complain.ComplainEntity;
import com.ecms.backend.ecmsapi.service.complain.ComplainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/complain")
public class ComplainController {

    private final ComplainService complainService;

    public ComplainController(ComplainService complainService) {
        this.complainService = complainService;
    }


    @PostMapping("/create_complain")
    public ResponseEntity<?> createComplain(@RequestBody ComplainDto complainDto) {
        ComplainEntity complainEntity = new ComplainEntity();
        complainEntity.setLocation(complainDto.getLocation());
        complainEntity.setDescription(complainDto.getDescription());
        complainEntity.setCrimeType(complainDto.getCrimeType());
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(complainService.createComplain(complainEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create the complain");
        }
    }


    @GetMapping("/get_audit_log")
    public ResponseEntity<List<ComplainEntity>> getAllComplaints() {
        return ResponseEntity.status(HttpStatus.OK).body(complainService.getAllComplaints());
    }

    @GetMapping("/get_complain_list")
    public ResponseEntity<List<ComplainDto>> getAllComplainByUser() {
        return ResponseEntity.status(HttpStatus.OK).body(complainService.getAllComplainByUser());
    }

    @GetMapping("/get_complain_list_by_status")
    public ResponseEntity<List<ComplainDto>> getAllComplainByUserAndStatus(@RequestParam String status) {
        return ResponseEntity.status(HttpStatus.OK).body(complainService.getAllComplainByUserAndStatus(status));
    }


    @GetMapping ("/get_complain_detail_by_id/{complainId}")
    public ResponseEntity<ComplainEntity> getComplainDetailById(@PathVariable Long complainId){
        return  ResponseEntity.status(HttpStatus.OK).body(complainService.getComplainDetailById(complainId));
    }


    @GetMapping("update_complain_status")
    public ResponseEntity<?> updateComplain(@RequestParam Long complaintId, @RequestParam String status){
        return ResponseEntity.status(HttpStatus.OK).body(complainService.updateComplain(complaintId, status));
    }

    @GetMapping("get_pie_chart_data")
    public ResponseEntity<?> getPieChartData(){
        return ResponseEntity.status(HttpStatus.OK).body(complainService.getPieChartData());
    }

    @GetMapping("get_bar_chart_data")
    public  List<List<Integer>> getBarChartData(){
        return complainService.getBarChartData();
    }

}
