package com.example.transporter.dto;

import java.util.List;

public class InputDataDto {
    private List<LaneDto> lanes;
    private List<TransporterDto> transporters;

    // Add Getters
    public List<LaneDto> getLanes() {
        return lanes;
    }

    public List<TransporterDto> getTransporters() {
        return transporters;
    }

    // Add Setters
    public void setLanes(List<LaneDto> lanes) {
        this.lanes = lanes;
    }

    public void setTransporters(List<TransporterDto> transporters) {
        this.transporters = transporters;
    }
}
