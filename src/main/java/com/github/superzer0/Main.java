package com.github.superzer0;

import com.esotericsoftware.minlog.Log;
import com.github.superzer0.services.IIntervalsAction;
import com.github.superzer0.services.IntervalsActionsFactory;
import com.github.superzer0.services.IoService;
import com.github.superzer0.services.ProcessingInputParams;
import edu.stanford.math.plex4.api.Plex4;
import edu.stanford.math.plex4.homology.barcodes.BarcodeCollection;
import edu.stanford.math.plex4.homology.chain_basis.Simplex;
import edu.stanford.math.plex4.homology.interfaces.AbstractPersistenceAlgorithm;
import edu.stanford.math.plex4.metric.landmark.MaxMinLandmarkSelector;
import edu.stanford.math.plex4.streams.impl.VietorisRipsStream;

import java.io.IOException;
import java.util.List;

/**
 * CMD application entry point
 * Used to orchestrate processing of Topological Analysis processing and
 * extracting further information like entropy, diagrams ect.
 *
 * @author Jakub Kawa
 * @version 1.0
 */

public class Main {

    /**
     * Entry point of the cmd application.
     * Executes TDA processing on provided args.
     *
     * @param args arguments provided by user when calling from command line.
     * @throws IOException when files provided by caller are missing
     * @exception IOException when files provided by caller are missing
     * @see IOException
     */
    public static void main(String[] args) throws IOException {

        Log.INFO();
        IoService ioService = new IoService();

        ProcessingInputParams inputParams = ioService.getInputParams(args);
        double[][] points = ioService.readPointsCloud(inputParams);

        BarcodeCollection<Double> computedIntervals = computeIntervals(inputParams, points);

        IntervalsActionsFactory actionsFactory = new IntervalsActionsFactory();
        List<IIntervalsAction> actions = actionsFactory.getIntervalsActions();

        runActionsWithFaultTolerance(actions, computedIntervals, inputParams);
    }

    private static BarcodeCollection<Double> computeIntervals(ProcessingInputParams inputParams, double[][] points) {
        VietorisRipsStream<double[]> vietorisRipsStream = getVietorisRipsStream(inputParams, points);
        AbstractPersistenceAlgorithm<Simplex> modularSimplicialAlgorithm =
                Plex4.getModularSimplicialAlgorithm(inputParams.getMaxDimension(), 2);

        Log.info("Starting computing intervals... ");
        BarcodeCollection<Double> computedIntervals = modularSimplicialAlgorithm.computeIntervals(vietorisRipsStream);
        Log.info("Computing intervals done.");
        return computedIntervals;
    }

    private static VietorisRipsStream<double[]> getVietorisRipsStream(ProcessingInputParams inputParams,
                                                                      double[][] points) {
        VietorisRipsStream<double[]> vietorisRipsStream;

        if (inputParams.useLandmarks()) {
            int lazyPointsToProcess = getLazyPointCount(points.length, inputParams.getMaxLandmarks());
            MaxMinLandmarkSelector<double[]> minMaxSelector = Plex4.createMaxMinSelector(points, lazyPointsToProcess);
            vietorisRipsStream = Plex4.createVietorisRipsStream(minMaxSelector,
                    inputParams.getMaxDimension(), inputParams.getMaxFiltrationValue(), DefaultSettings.DEFAULT_MAX_DIVISIONS);

            Log.info("Landmark points to process: " + lazyPointsToProcess);
        } else {
            vietorisRipsStream = Plex4.createVietorisRipsStream(points, inputParams.getMaxDimension(),
                    inputParams.getMaxFiltrationValue(), DefaultSettings.DEFAULT_MAX_DIVISIONS);
            Log.info("Points to process: " + points.length);
        }
        return vietorisRipsStream;
    }

    private static void runActionsWithFaultTolerance(List<IIntervalsAction> actions,
                                                     BarcodeCollection<Double> computedIntervals,
                                                     ProcessingInputParams inputParams) {
        for (IIntervalsAction action : actions) {
            try {
                action.run(inputParams, computedIntervals);
            } catch (Exception ex) {
                Log.warn("Error when executing action", ex);
            }
        }
    }

    private static int getLazyPointCount(int pointsNumber, int maxLandmarks) {
        if (pointsNumber > maxLandmarks)
            return maxLandmarks;
        else return pointsNumber;
    }
}



