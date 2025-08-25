package com.example.transporter.dto;

public class LaneQuoteDto {
    private Long laneId;
    private Integer quote;

    public Long getLaneId() {
        return laneId;
    }

    public Integer getQuote() {
        return quote;
    }

    public void setLaneId(Long laneId) {
        this.laneId = laneId;
    }

    public void setQuote(Integer quote) {
        this.quote = quote;
    }
}
