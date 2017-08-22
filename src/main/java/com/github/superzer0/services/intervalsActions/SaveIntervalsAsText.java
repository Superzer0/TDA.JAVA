package com.github.superzer0.services.intervalsActions;

import com.esotericsoftware.minlog.Log;
import com.github.superzer0.DefaultSettings;
import com.github.superzer0.services.IIntervalsAction;
import com.github.superzer0.services.ProcessingInputParams;
import edu.stanford.math.plex4.homology.barcodes.BarcodeCollection;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class SaveIntervalsAsText implements IIntervalsAction {

    @Override
    public void run(ProcessingInputParams inputParams, BarcodeCollection<Double> computedIntervals) {
        try (PrintWriter writer = new PrintWriter(Paths.get(inputParams.getOutputFolder().toString(),
                DefaultSettings.DEFAULT_HOMOLOGY_FILE_NAME).toString(), "UTF-8")) {
            Log.info("Saving intervals as text with basic info...");

            writer.println(computedIntervals.getBettiNumbers());
            writer.println(computedIntervals.toString());
            Log.info("Saving intervals done.");
        } catch (IOException e) {
            Log.error("Saving intervals as text with basic info failed", e);
        }
    }
}
