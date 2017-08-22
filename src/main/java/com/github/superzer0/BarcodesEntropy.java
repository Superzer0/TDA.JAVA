package com.github.superzer0;

import edu.stanford.math.plex4.homology.barcodes.Interval;

import java.util.List;

public class BarcodesEntropy {

    public static double computeDiagramEntropy(int maxFiltration, List<Interval<Double>> intervals) {

        double entropy = 0;
        double L = 0;
        for (Interval<Double> interval : intervals) {
            Double l = getLValueFromInterval(maxFiltration, interval);
            L += l;
        }

        for (Interval<Double> interval : intervals) {
            Double l = getLValueFromInterval(maxFiltration, interval);
            if (l == 0d)
                continue;

            double p = l / L;
            entropy += p * Math.log(p);
        }

        return entropy * -1;
    }

    private static Double getLValueFromInterval(double maxFiltration, Interval<Double> interval) {
        if (interval == null)
            return 0d;

        Double rightIndex;
        Double leftIndex;
        if (interval.isRightInfinite()) {
            rightIndex = maxFiltration;
        } else {
            rightIndex = interval.getEnd();
        }

        if (interval.isLeftInfinite()) {
            leftIndex = 0d;
        } else {
            leftIndex = interval.getStart();
        }

        return rightIndex - leftIndex;
    }
}
