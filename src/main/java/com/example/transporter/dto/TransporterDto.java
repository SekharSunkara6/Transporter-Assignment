package com.example.transporter.dto;

import java.util.List;

public class TransporterDto {
    private Long id;
    private String name;
    private List<LaneQuoteDto> laneQuotes;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<LaneQuoteDto> getLaneQuotes() {
        return laneQuotes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLaneQuotes(List<LaneQuoteDto> laneQuotes) {
        this.laneQuotes = laneQuotes;
    }
}
