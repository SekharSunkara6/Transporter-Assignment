package com.example.transporter.dto;

import java.util.List;

public class AssignmentResponseDto {
    private long totalCost; // Change from Integer to long
    private List<AssignmentDto> assignments;
    private List<Long> selectedTransporters;

    public long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public List<AssignmentDto> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<AssignmentDto> assignments) {
        this.assignments = assignments;
    }

    public List<Long> getSelectedTransporters() {
        return selectedTransporters;
    }

    public void setSelectedTransporters(List<Long> selectedTransporters) {
        this.selectedTransporters = selectedTransporters;
    }
}
