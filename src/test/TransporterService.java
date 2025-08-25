package com.example.transporter.service;

import com.example.transporter.dto.*;
import com.example.transporter.model.*;
import com.example.transporter.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TransporterService {

    private final LaneRepository laneRepository;
    private final TransporterRepository transporterRepository;
    private final LaneQuoteRepository laneQuoteRepository;

    public TransporterService(LaneRepository laneRepository,
            TransporterRepository transporterRepository,
            LaneQuoteRepository laneQuoteRepository) {
        this.laneRepository = laneRepository;
        this.transporterRepository = transporterRepository;
        this.laneQuoteRepository = laneQuoteRepository;
    }

    @Transactional
    public void saveInputData(InputDataDto inputData) {
        if (inputData == null || inputData.getLanes() == null || inputData.getTransporters() == null) {
            throw new IllegalArgumentException("Input data, lanes, and transporters must not be null");
        }

        laneQuoteRepository.deleteAll();
        transporterRepository.deleteAll();
        laneRepository.deleteAll();

        inputData.getLanes().forEach(laneDto -> {
            Lane lane = new Lane();
            lane.setId(laneDto.getId());
            lane.setOrigin(laneDto.getOrigin());
            lane.setDestination(laneDto.getDestination());
            laneRepository.save(lane);
        });

        inputData.getTransporters().forEach(transporterDto -> {
            Transporter transporter = new Transporter();
            transporter.setId(transporterDto.getId());
            transporter.setName(transporterDto.getName());
            transporterRepository.save(transporter);

            transporterDto.getLaneQuotes().forEach(quoteDto -> {
                LaneQuote quote = new LaneQuote();
                quote.setLaneId(quoteDto.getLaneId());
                quote.setQuote(quoteDto.getQuote());
                quote.setTransporterId(transporterDto.getId());
                laneQuoteRepository.save(quote);
            });
        });
    }

    @Transactional(readOnly = true)
    public AssignmentResponseDto computeAssignment(int maxTransporters) {
        if (maxTransporters <= 0)
            throw new IllegalArgumentException("maxTransporters must be positive");

        List<Lane> lanes = laneRepository.findAll();
        List<Transporter> transporters = transporterRepository.findAll();

        if (lanes.isEmpty())
            throw new IllegalStateException("No lanes available");
        if (transporters.isEmpty())
            throw new IllegalStateException("No transporters available");

        // Map laneId -> lane quotes sorted by quote ascending
        Map<Long, List<LaneQuote>> laneQuotesMap = new HashMap<>();
        for (Lane lane : lanes) {
            List<LaneQuote> quotes = laneQuoteRepository.findByLaneId(lane.getId());
            if (quotes == null || quotes.isEmpty())
                throw new IllegalStateException("No quotes for lane " + lane.getId());
            quotes.sort(Comparator.comparingInt(LaneQuote::getQuote));
            laneQuotesMap.put(lane.getId(), quotes);
        }

        // Set of used transporter IDs
        Set<Long> usedTransporters = new HashSet<>();
        List<AssignmentDto> assignments = new ArrayList<>();
        long totalCost = 0;

        // Assign lanes prioritizing new transporters to maximize their usage, up to
        // maxTransporters
        for (Lane lane : lanes) {
            List<LaneQuote> quotes = laneQuotesMap.get(lane.getId());

            // Try assign to transporter not yet used and maxTransporters not reached
            LaneQuote selectedQuote = null;

            for (LaneQuote quote : quotes) {
                if (!usedTransporters.contains(quote.getTransporterId()) &&
                        usedTransporters.size() < maxTransporters) {
                    selectedQuote = quote;
                    break;
                }
            }

            // If no transporter unused can serve, assign to already used transporter
            // (lowest quote)
            if (selectedQuote == null) {
                for (LaneQuote quote : quotes) {
                    if (usedTransporters.contains(quote.getTransporterId())) {
                        selectedQuote = quote;
                        break;
                    }
                }
            }

            // Fallback to cheapest quote if none found (should ideally not happen)
            if (selectedQuote == null) {
                selectedQuote = quotes.get(0);
            }

            assignments.add(new AssignmentDto(selectedQuote.getLaneId(), selectedQuote.getTransporterId()));
            usedTransporters.add(selectedQuote.getTransporterId());
            totalCost += selectedQuote.getQuote();
        }

        AssignmentResponseDto response = new AssignmentResponseDto();
        response.setTotalCost(totalCost);
        response.setAssignments(assignments);
        response.setSelectedTransporters(new ArrayList<>(usedTransporters));

        return response;
    }
}
