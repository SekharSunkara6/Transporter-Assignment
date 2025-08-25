package com.example.transporter.util;

import com.example.transporter.model.Lane;
import com.example.transporter.model.Transporter;
import com.example.transporter.model.LaneQuote;
import com.example.transporter.repository.LaneRepository;
import com.example.transporter.repository.TransporterRepository;
import com.example.transporter.repository.LaneQuoteRepository;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CsvDataLoader {

    private final LaneRepository laneRepository;
    private final TransporterRepository transporterRepository;
    private final LaneQuoteRepository laneQuoteRepository;

    public CsvDataLoader(LaneRepository laneRepository,
            TransporterRepository transporterRepository,
            LaneQuoteRepository laneQuoteRepository) {
        this.laneRepository = laneRepository;
        this.transporterRepository = transporterRepository;
        this.laneQuoteRepository = laneQuoteRepository;
    }

    @PostConstruct
    public void loadCsvData() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("transporter_assignment_data.csv");
                InputStreamReader reader = new InputStreamReader(is);
                CSVReader csvReader = new CSVReader(reader)) {

            List<String[]> records = csvReader.readAll();

            // Parse header to map column index to transporter ID
            String[] header = records.get(0);
            Map<Integer, Long> transporterIndexToId = new HashMap<>();

            // Initialize transporters from header row (except first column)
            for (int i = 1; i < header.length; i++) {
                String transporterName = header[i].trim(); // e.g. "T1"
                Long transporterId = Long.parseLong(transporterName.substring(1)); // remove 'T' prefix
                transporterIndexToId.put(i, transporterId);

                // Upsert transporter entity
                Transporter transporter = transporterRepository.findById(transporterId).orElse(new Transporter());
                transporter.setId(transporterId);
                transporter.setName("Transporter " + transporterName);
                transporterRepository.save(transporter);
            }

            // Parse lanes and lane quotes
            for (int rowIndex = 1; rowIndex < records.size(); rowIndex++) {
                String[] row = records.get(rowIndex);

                // Extract lane ID from "Lane X"
                String laneName = row[0].trim();
                Long laneId = Long.parseLong(laneName.replaceAll("[^0-9]", ""));

                // Upsert lane entity
                Lane lane = laneRepository.findById(laneId).orElse(new Lane());
                lane.setId(laneId);
                // Optionally set origin/destination if you have those (or leave empty)
                laneRepository.save(lane);

                // Read quotes for each transporter column
                for (int colIndex = 1; colIndex < row.length; colIndex++) {
                    String quoteStr = row[colIndex].trim();
                    if (quoteStr.isEmpty()) {
                        continue;
                    }
                    int quote = Integer.parseInt(quoteStr);
                    Long transporterId = transporterIndexToId.get(colIndex);

                    LaneQuote laneQuote = new LaneQuote();
                    laneQuote.setLaneId(laneId);
                    laneQuote.setTransporterId(transporterId);
                    laneQuote.setQuote(quote);

                    laneQuoteRepository.save(laneQuote);
                }
            }

            System.out.println("CSV data loaded successfully.");

        } catch (Exception e) {
            System.err.println("Failed to load CSV data:");
            e.printStackTrace();
        }
    }
}
