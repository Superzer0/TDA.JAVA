package com.github.superzer0.services;

import edu.stanford.math.plex4.homology.barcodes.BarcodeCollection;

/**
 * Command interface used to process TDA run output
 * @author Jakub Kawa
 * @version 1.0
 */
public interface IIntervalsAction {

    /**
     * Processes TDA algorithm run output
     * @param inputParams processing params
     * @param computedIntervals TDA algorithm run output
     */
    void run(ProcessingInputParams inputParams, BarcodeCollection<Double> computedIntervals);
}
