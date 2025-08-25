package com.example.transporter.optaplanner;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import java.util.List;
import java.util.stream.Collectors;

public class AssignmentConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                laneAssignedOnce(constraintFactory),
                transporterLimit(constraintFactory),
                minimizeCost(constraintFactory)
        };
    }

    // Hard constraint: Each lane must be assigned exactly once
    private Constraint laneAssignedOnce(ConstraintFactory factory) {
        return factory.from(LaneAssignment.class)
                .filter(assignment -> assignment.getAssignedTransporter() == null)
                .penalize("Unassigned lane", HardSoftScore.ONE_HARD);
    }

    // Hard constraint: Max number of distinct transporters used
    private Constraint transporterLimit(ConstraintFactory factory) {
        // This is a simplified example; you may need a more complex rule to count
        // transporters used
        // For now, skip this or implement via score calculation outside constraint
        // provider
        return factory.from(LaneAssignment.class)
                .filter(assignment -> false) // no penalty here, add if implementing transporter count
                .penalize("Transporter usage limit", HardSoftScore.ONE_HARD, assignment -> 0);
    }

    // Soft constraint: Minimize total quote cost
    private Constraint minimizeCost(ConstraintFactory factory) {
        return factory.from(LaneAssignment.class)
                .filter(assignment -> assignment.getAssignedTransporter() != null)
                .reward("Minimize cost", HardSoftScore.ONE_SOFT,
                        assignment -> {
                            // You need to retrieve the cost of assignment here from quote data
                            // Placeholder: return 0, implement accordingly
                            return 0;
                        });
    }
}
