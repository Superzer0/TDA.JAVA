package com.github.superzer0.services.intervalsActions;

import com.esotericsoftware.minlog.Log;
import com.github.superzer0.DefaultSettings;
import com.github.superzer0.services.IIntervalsAction;
import com.github.superzer0.services.ProcessingInputParams;
import edu.stanford.math.plex4.homology.barcodes.BarcodeCollection;
import edu.stanford.math.plex4.homology.barcodes.Interval;
import edu.stanford.math.plex4.homology.barcodes.PersistenceInvariantDescriptor;
import edu.stanford.math.plex4.io.BarcodeWriter;

import java.io.IOException;
import java.nio.file.Paths;

public class SaveIntervalsDiagrams implements IIntervalsAction {

    private static <G> void createBarcodePlot(PersistenceInvariantDescriptor<Interval<Double>, G> collection,
                                              double endPoint, String folderPath) throws IOException {
        BarcodeWriter writer = BarcodeWriter.getInstance();

        for (int dimension : collection.getDimensions()) {
            String imageFilename = DefaultSettings.DEFAULT_IMAGE_CAPTION + "_" + dimension;
            String fullFileName = imageFilename + "." + writer.getExtension();
            String path = Paths.get(folderPath, fullFileName).toString();
            writer.writeToFile(collection, dimension, endPoint, imageFilename, path);
        }
    }

    @Override
    public void run(ProcessingInputParams inputParams, BarcodeCollection<Double> computedIntervals) {
        try {
            Log.info("Creating barcode plot... ");
            createBarcodePlot(computedIntervals, inputParams.getMaxFiltrationValue(),
                    inputParams.getOutputFolder().toString());
            Log.info("Created barcode plot.");
        } catch (IOException e) {
            Log.error("Creating barcode plot failed", e);
        }
    }
}
