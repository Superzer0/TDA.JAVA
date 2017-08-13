package com.github.superzer0;

import com.esotericsoftware.minlog.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.stanford.math.plex4.api.Plex4;
import edu.stanford.math.plex4.homology.barcodes.BarcodeCollection;
import edu.stanford.math.plex4.homology.barcodes.Interval;
import edu.stanford.math.plex4.homology.barcodes.PersistenceInvariantDescriptor;
import edu.stanford.math.plex4.homology.chain_basis.Simplex;
import edu.stanford.math.plex4.homology.interfaces.AbstractPersistenceAlgorithm;
import edu.stanford.math.plex4.io.BarcodeWriter;
import edu.stanford.math.plex4.metric.landmark.MaxMinLandmarkSelector;
import edu.stanford.math.plex4.streams.impl.VietorisRipsStream;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class Main {

    private static final String DEFAULT_IMAGE_CAPTION = "Barcodes";
    private static final String DEFAULT_HOMOLOGY_FILE_NAME = "persistent_homology_summary.txt";
    private static final int DEFAULT_MAX_DIVISIONS = 100;

    public static void main(String[] args) throws IOException {

        Log.INFO();

        ProcessingInputParams inputParams = GetInputParams(args);
        Log.info("Input Params: " + inputParams.toString());

        ObjectMapper mapper = new ObjectMapper();

        double[][] points = mapper.readValue(
                new File(inputParams.getPointsCloudFilePath().toString()), double[][].class);

        VietorisRipsStream<double[]> vietorisRipsStream = getVietorisRipsStream(inputParams, points);
        AbstractPersistenceAlgorithm<Simplex> modularSimplicialAlgorithm =
                Plex4.getModularSimplicialAlgorithm(inputParams.getMaxDimension(), 2);

        Log.info("Starting computing intervals... ");

        BarcodeCollection<Double> computedIntervals =
                modularSimplicialAlgorithm.computeIntervals(vietorisRipsStream);

        Log.info("Computing intervals done.");

        saveBarcodesText(inputParams, computedIntervals);
        saveBarcodePlot(inputParams, computedIntervals);
    }

    private static VietorisRipsStream<double[]> getVietorisRipsStream(ProcessingInputParams inputParams, double[][] points) {
        VietorisRipsStream<double[]> vietorisRipsStream;

        if (inputParams.isUseLandmarks()) {
            int lazyPointsToProcess = getLazyPointCount(points.length, inputParams.getMaxLandmarks());
            MaxMinLandmarkSelector<double[]> minMaxSelector = Plex4.createMaxMinSelector(points, lazyPointsToProcess);
            vietorisRipsStream = Plex4.createVietorisRipsStream(minMaxSelector,
                    inputParams.getMaxDimension(), inputParams.getMaxFiltrationValue(), DEFAULT_MAX_DIVISIONS);

            Log.info("Landmark points to process: " + lazyPointsToProcess);
        } else {
            vietorisRipsStream = Plex4.createVietorisRipsStream(points, inputParams.getMaxDimension(),
                    inputParams.getMaxFiltrationValue(), DEFAULT_MAX_DIVISIONS);
            Log.info("Points to process: " + points.length);
        }
        return vietorisRipsStream;
    }

    private static void saveBarcodePlot(ProcessingInputParams inputParams, BarcodeCollection<Double> computedIntervals) {
        try {
            Log.info("Creating barcode plot... ");
            createBarcodePlot(computedIntervals, inputParams.getMaxFiltrationValue(),
                    inputParams.getOutputFolder().toString());
            Log.info("Created barcode plot.");
        } catch (IOException e) {
            Log.error("Creating barcode plot failed", e);
        }
    }

    private static void saveBarcodesText(ProcessingInputParams inputParams, BarcodeCollection<Double> computedIntervals) {
        try {
            Log.info("Saving intervals as text with basic info...");
            PrintWriter writer = new PrintWriter(Paths.get(inputParams.getOutputFolder().toString(),
                    DEFAULT_HOMOLOGY_FILE_NAME).toString(), "UTF-8");
            writer.println(computedIntervals.getBettiNumbers());
            writer.println(computedIntervals.toString());
            writer.close();
            Log.info("Saving intervals done.");
        } catch (IOException e) {
            Log.error("Saving intervals as text with basic info failed", e);
        }
    }

    private static ProcessingInputParams GetInputParams(String[] args) {
        if (args.length < 5) {
            Log.error("Not enough params");
            throw new IndexOutOfBoundsException();
        }

        ProcessingInputParams inputParams = new ProcessingInputParams();
        inputParams.setPointsCloudFilePath(args[0]);
        inputParams.setMaxDimension(args[1]);
        inputParams.setMaxFiltrationValue(args[2]);
        inputParams.setUseLandmarks(args[3]);
        inputParams.setMaxLandmarks(args[4]);
        inputParams.setOutputFolder(args[5]);

        return inputParams;
    }

    private static <G> void createBarcodePlot(PersistenceInvariantDescriptor<Interval<Double>, G> collection,
                                              double endPoint, String folderPath) throws IOException {
        BarcodeWriter writer = BarcodeWriter.getInstance();

        for (int dimension : collection.getDimensions()) {
            String imageFilename = DEFAULT_IMAGE_CAPTION + "_" + dimension;
            String fullFileName = imageFilename + "." + writer.getExtension();
            String path = Paths.get(folderPath, fullFileName).toString();
            writer.writeToFile(collection, dimension, endPoint, imageFilename, path);
        }
    }

    private static int getLazyPointCount(int pointsNumber, int maxLandmarks) {
        if (pointsNumber > maxLandmarks)
            return maxLandmarks;
        else return pointsNumber;
    }
}



