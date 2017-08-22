package com.github.superzer0.services;

import edu.stanford.math.plex4.homology.barcodes.BarcodeCollection;

public interface IIntervalsAction {
    void run(ProcessingInputParams inputParams, BarcodeCollection<Double> computedIntervals);
}
