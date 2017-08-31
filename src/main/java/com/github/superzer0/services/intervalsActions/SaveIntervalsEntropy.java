package com.github.superzer0.services.intervalsActions;

import com.esotericsoftware.minlog.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.superzer0.BarcodesEntropy;
import com.github.superzer0.DefaultSettings;
import com.github.superzer0.services.IIntervalsAction;
import com.github.superzer0.services.ProcessingInputParams;
import edu.stanford.math.plex4.homology.barcodes.BarcodeCollection;
import edu.stanford.math.plex4.homology.barcodes.Interval;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Computes diagrams entropy based on TDA barcode collection
 * @author Jakub Kawa
 * @version 1.0
 */

public class SaveIntervalsEntropy implements IIntervalsAction {

    /**
     * Saves as json entropy value for each dimension intervals
     * @param inputParams processing params
     * @param computedIntervals TDA algorithm run output
     */
    @Override
    public void run(ProcessingInputParams inputParams, BarcodeCollection<Double> computedIntervals) {
        List<Double> entropiesList = new ArrayList<>();

        Log.info("Computing diagrams entropy...");

        for (int dimension : computedIntervals.getDimensions()) {
            List<Interval<Double>> intervalsAtDimension = computedIntervals.getIntervalsAtDimension(dimension);
            if (intervalsAtDimension != null)
                entropiesList.add(BarcodesEntropy.computeDiagramEntropy(inputParams.getMaxFiltrationValue(),
                        intervalsAtDimension));
        }

        try (PrintWriter writer = new PrintWriter(Paths.get(inputParams.getOutputFolder().toString(),
                DefaultSettings.DEFAULT_ENTROPY_FILE_NAME).toString(), "UTF-8")) {

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, entropiesList);
            Log.info("Computing diagrams entropy done");

        } catch (IOException ioException) {
            Log.error("Saving entropy failed", ioException);
        }
    }
}
