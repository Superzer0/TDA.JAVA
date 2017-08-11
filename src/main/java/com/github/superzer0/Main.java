package com.github.superzer0;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.stanford.math.plex4.api.Plex4;
import edu.stanford.math.plex4.bottleneck.BottleneckDistance;
import edu.stanford.math.plex4.examples.PointCloudExamples;
import edu.stanford.math.plex4.homology.barcodes.BarcodeCollection;
import edu.stanford.math.plex4.homology.barcodes.Interval;
import edu.stanford.math.plex4.homology.barcodes.PersistenceInvariantDescriptor;
import edu.stanford.math.plex4.homology.chain_basis.Simplex;
import edu.stanford.math.plex4.homology.interfaces.AbstractPersistenceAlgorithm;
import edu.stanford.math.plex4.io.BarcodeWriter;
import edu.stanford.math.plex4.io.DoubleArrayReaderWriter;
import edu.stanford.math.plex4.metric.impl.EuclideanMetricSpace;
import edu.stanford.math.plex4.metric.landmark.MaxMinLandmarkSelector;
import edu.stanford.math.plex4.streams.impl.VietorisRipsStream;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    private static final String DEFAULT_IMAGE_CAPTION = "Barcodes";
    private static final String DEFAULT_HOMOLOGY_FILE_NAME = "persistent_homology_summary.txt";
    private static final int DEFAULT_COUNT_LANDMARKS = 200;

    public static void main(String[] args) throws IOException {

        Path pointsCloudFilePath = Paths.get(args[0].replace("\"", ""));
        int maxDimension = Integer.parseInt(args[1]);
        int maxFiltrationValue = Integer.parseInt(args[2]);
        boolean lazy = Boolean.parseBoolean(args[3]);
        Path outputFolder = Paths.get(args[4].replace("\"", ""));

        System.out.println("Points Cloud File Path: " + pointsCloudFilePath);
        System.out.println("Max dimension: " + maxDimension);
        System.out.println("Max Filtration Value: " + maxFiltrationValue);
        System.out.println("Landmarks enabled: " + lazy);

        ObjectMapper mapper = new ObjectMapper();

        try {
            double[][] points = mapper.readValue(new File(pointsCloudFilePath.toString()), double[][].class);

            VietorisRipsStream<double[]> vietorisRipsStream;
            if (lazy) {
                int lazyPointsToProcess = getLazyPointCount(points.length);
                MaxMinLandmarkSelector<double[]> minMaxSelector = Plex4.createMaxMinSelector(points, lazyPointsToProcess);
                vietorisRipsStream = Plex4.createVietorisRipsStream(minMaxSelector, maxDimension, maxFiltrationValue, 100);
                System.out.println("Lazy points to process: " + lazyPointsToProcess);
            } else {
                vietorisRipsStream = Plex4.createVietorisRipsStream(points, maxDimension, maxFiltrationValue, 100);
                System.out.println("Points to process: " + points.length);
            }


            System.out.println("Starting computing intervals... ");
            AbstractPersistenceAlgorithm<Simplex> modularSimplicialAlgorithm = Plex4.getModularSimplicialAlgorithm(maxDimension, 2);
            BarcodeCollection<Double> computedIntervals = modularSimplicialAlgorithm.computeIntervals(vietorisRipsStream);
            System.out.println("Computing intervals done.");

            try {
                System.out.println("Saving intervals as text with basic info...");
                PrintWriter writer = new PrintWriter(Paths.get(outputFolder.toString(), DEFAULT_HOMOLOGY_FILE_NAME).toString(), "UTF-8");
                writer.println(computedIntervals.getBettiNumbers());
                writer.println(computedIntervals.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                System.out.println("Creating barcode plot... ");
                createBarcodePlot(computedIntervals, maxFiltrationValue, outputFolder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private static int getLazyPointCount(int pointsNumber) {
        if (pointsNumber > DEFAULT_COUNT_LANDMARKS)
            return DEFAULT_COUNT_LANDMARKS;
        else return pointsNumber;
    }

    private static void Example() throws IOException {

        double[][] points = PointCloudExamples.getHouseExample();
        EuclideanMetricSpace metricSpace = new EuclideanMetricSpace(points);
        double[][] randomSpherePoints = PointCloudExamples.getRandomSpherePoints(1, 1);

        DoubleArrayReaderWriter writer = DoubleArrayReaderWriter.getInstance();
        writer.writeToFile(points, "houseExample.csv");

        System.out.println(Arrays.toString(metricSpace.getPoint(0)));
        System.out.println(Arrays.toString(metricSpace.getPoint(1)));
        System.out.println(metricSpace.distance(metricSpace.getPoint(0), metricSpace.getPoint(1)));
        VietorisRipsStream<double[]> vietorisRipsStream = Plex4.createVietorisRipsStream(points, 2, 4, 100);
        AbstractPersistenceAlgorithm<Simplex> modularSimplicialAlgorithm = Plex4.getModularSimplicialAlgorithm(2, 2);
        BarcodeCollection<Double> computeIntervals = modularSimplicialAlgorithm.computeIntervals(vietorisRipsStream);
        System.out.println(BottleneckDistance.computeBottleneckDistance(computeIntervals.getIntervalsAtDimension(1), computeIntervals.getIntervalsAtDimension(0)));
    }
}



