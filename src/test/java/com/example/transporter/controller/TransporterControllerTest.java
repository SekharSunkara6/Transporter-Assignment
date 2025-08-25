package com.example.transporter.controller;

import com.example.transporter.dto.*;
import com.example.transporter.service.TransporterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(TransporterController.class)
public class TransporterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransporterService transporterService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSubmitInputData_Success() throws Exception {
        InputDataDto inputData = new InputDataDto();
        // ... Initialize inputData with sample data as needed

        Mockito.doNothing().when(transporterService).saveInputData(Mockito.any());

        mockMvc.perform(post("/api/v1/transporters/input")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Input data saved successfully."));
    }

    @Test
    public void testGetAssignment_Success() throws Exception {
        AssignmentResponseDto responseDto = new AssignmentResponseDto();
        responseDto.setTotalCost(10000L);
        responseDto.setAssignments(List.of(new AssignmentDto(1L, 1L)));
        responseDto.setSelectedTransporters(List.of(1L));

        Mockito.when(transporterService.computeAssignment(Mockito.anyInt())).thenReturn(responseDto);

        AssignmentRequestDto requestDto = new AssignmentRequestDto();
        requestDto.setMaxTransporters(3);

        mockMvc.perform(post("/api/v1/transporters/assignment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(10000L))
                .andExpect(jsonPath("$.assignments").isArray())
                .andExpect(jsonPath("$.selectedTransporters").isArray());
    }
}
