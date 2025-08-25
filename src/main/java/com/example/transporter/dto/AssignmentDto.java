package com.example.transporter.dto;

public class AssignmentDto {
    private Long laneId;
    private Long transporterId;

    public AssignmentDto() {
        // No-arg constructor needed for JSON deserialization
    }

    // Add this constructor
    public AssignmentDto(Long laneId, Long transporterId) {
        this.laneId = laneId;
        this.transporterId = transporterId;
    }

    // Getters and setters
    public Long getLaneId() {
        return laneId;
    }

    public void setLaneId(Long laneId) {
        this.laneId = laneId;
    }

    public Long getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(Long transporterId) {
        this.transporterId = transporterId;
    }
}
