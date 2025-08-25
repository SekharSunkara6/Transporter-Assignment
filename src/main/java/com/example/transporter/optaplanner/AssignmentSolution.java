package com.example.transporter.optaplanner;

import com.example.transporter.model.Lane;
import com.example.transporter.model.Transporter;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@PlanningSolution
public class AssignmentSolution {

    @ProblemFactCollectionProperty
    private List<Lane> laneList;

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "transporterRange")
    private List<Transporter> transporterList;

    @PlanningEntityCollectionProperty
    private List<LaneAssignment> laneAssignmentList;

    @PlanningScore
    private HardSoftScore score;

    public List<Lane> getLaneList() {
        return laneList;
    }

    public void setLaneList(List<Lane> laneList) {
        this.laneList = laneList;
    }

    public List<Transporter> getTransporterList() {
        return transporterList;
    }

    public void setTransporterList(List<Transporter> transporterList) {
        this.transporterList = transporterList;
    }

    public List<LaneAssignment> getLaneAssignmentList() {
        return laneAssignmentList;
    }

    public void setLaneAssignmentList(List<LaneAssignment> laneAssignmentList) {
        this.laneAssignmentList = laneAssignmentList;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }
}
