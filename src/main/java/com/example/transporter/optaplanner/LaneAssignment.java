package com.example.transporter.optaplanner;

import com.example.transporter.model.Lane;
import com.example.transporter.model.Transporter;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class LaneAssignment {

    private Lane lane;

    @PlanningVariable(valueRangeProviderRefs = "transporterRange")
    private Transporter assignedTransporter;

    public LaneAssignment() {
        // no-arg constructor required by OptaPlanner
    }

    public LaneAssignment(Lane lane) {
        this.lane = lane;
    }

    public Lane getLane() {
        return lane;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }

    public Transporter getAssignedTransporter() {
        return assignedTransporter;
    }

    public void setAssignedTransporter(Transporter assignedTransporter) {
        this.assignedTransporter = assignedTransporter;
    }
}
