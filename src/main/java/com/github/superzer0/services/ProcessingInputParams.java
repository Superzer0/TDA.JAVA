package com.github.superzer0.services;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Holds information parsed from user input taken as the TDA algorithm input.
 * Used as DTO available for modifications only inside package
 * @author Jakub Kawa
 * @version 1.0
 */

public class ProcessingInputParams {

    private Path pointsCloudFilePath;
    private int maxDimension;
    private int maxFiltrationValue;
    private boolean useLandmarks;
    private Path outputFolder;
    private int maxLandmarks;

    private static void CheckStringParam(String inputValue, String param) {
        if (inputValue == null || inputValue.isEmpty())
            throw new IllegalArgumentException("Provided input is not valid for " + param);
    }

    Path getPointsCloudFilePath() {
        return pointsCloudFilePath;
    }

    void setPointsCloudFilePath(String pointsCloudFilePath) {
        CheckStringParam(pointsCloudFilePath, "pointsCloudFilePath");
        this.pointsCloudFilePath = Paths.get(pointsCloudFilePath.replace("\"", ""));
    }

    /**
     * Returns max dimension
     * @return max dimension that should be computed
     */
    public int getMaxDimension() {
        return maxDimension;
    }

    void setMaxDimension(String maxDimension) {
        CheckStringParam(maxDimension, "maxDimension");
        this.maxDimension = Integer.parseInt(maxDimension);
    }

    /**
     * Returns max filtration
     * @return max filtration that TDA processing should look for holes in points cloud
     */
    public int getMaxFiltrationValue() {
        return maxFiltrationValue;
    }

    void setMaxFiltrationValue(String maxFiltrationValue) {
        CheckStringParam(maxFiltrationValue, "maxFiltrationValue");
        this.maxFiltrationValue = Integer.parseInt(maxFiltrationValue);
    }

    /**
     * Returns whether to use landmarks
     * @return if landmarks should be used
     */
    public boolean useLandmarks() {
        return useLandmarks;
    }

    void setUseLandmarks(String useLandmarks) {
        CheckStringParam(useLandmarks, "useLandmarks");
        this.useLandmarks = Boolean.parseBoolean(useLandmarks);
    }

    /**
     * Returns output folder path
     * @return output folder full path
     */
    public Path getOutputFolder() {
        return outputFolder;
    }

    void setOutputFolder(String outputFolder) {
        CheckStringParam(outputFolder, "outputFolder");
        this.outputFolder = Paths.get(outputFolder.replace("\"", ""));
    }

    /**
     * Returns max landmarks count only used when useLandmarks is true
     * @return max landmarks count
     */
    public int getMaxLandmarks() {
        return maxLandmarks;
    }

    void setMaxLandmarks(String maxLandmarks) {
        CheckStringParam(maxLandmarks, "maxLandmarks");
        this.maxLandmarks = Integer.parseInt(maxLandmarks);
    }

    /**
     *
     * @return Readable representation of the object
     */
    @Override
    public String toString() {
        return "ProcessingInputParams{" +
                "pointsCloudFilePath=" + pointsCloudFilePath +
                ", maxDimension=" + maxDimension +
                ", maxFiltrationValue=" + maxFiltrationValue +
                ", useLandmarks=" + useLandmarks +
                ", outputFolder=" + outputFolder +
                ", maxLandmarks=" + maxLandmarks +
                '}';
    }
}
