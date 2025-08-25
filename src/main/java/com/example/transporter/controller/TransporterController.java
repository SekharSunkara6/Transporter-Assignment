package com.example.transporter.controller;

import com.example.transporter.dto.AssignmentRequestDto;
import com.example.transporter.dto.AssignmentResponseDto;
import com.example.transporter.dto.InputDataDto;
import com.example.transporter.service.TransporterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transporters")
public class TransporterController {

    private final TransporterService transporterService;

    public TransporterController(TransporterService transporterService) {
        this.transporterService = transporterService;
    }

    @PostMapping("/input")
    public ResponseEntity<?> submitInputData(@RequestBody InputDataDto inputData) {
        transporterService.saveInputData(inputData);
        return ResponseEntity.ok(java.util.Map.of("status", "success", "message", "Input data saved successfully."));
    }

    @PostMapping("/assignment")
    public ResponseEntity<AssignmentResponseDto> getAssignment(@RequestBody AssignmentRequestDto request) {
        AssignmentResponseDto response = transporterService.computeAssignment(request.getMaxTransporters());
        return ResponseEntity.ok(response);
    }
}
