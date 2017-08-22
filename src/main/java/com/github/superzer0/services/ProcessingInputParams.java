package com.github.superzer0.services;

import java.nio.file.Path;
import java.nio.file.Paths;

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

    public Path getPointsCloudFilePath() {
        return pointsCloudFilePath;
    }

    void setPointsCloudFilePath(String pointsCloudFilePath) {
        CheckStringParam(pointsCloudFilePath, "pointsCloudFilePath");
        this.pointsCloudFilePath = Paths.get(pointsCloudFilePath.replace("\"", ""));
    }

    public int getMaxDimension() {
        return maxDimension;
    }

    void setMaxDimension(String maxDimension) {
        CheckStringParam(maxDimension, "maxDimension");
        this.maxDimension = Integer.parseInt(maxDimension);
    }

    public int getMaxFiltrationValue() {
        return maxFiltrationValue;
    }

    void setMaxFiltrationValue(String maxFiltrationValue) {
        CheckStringParam(maxFiltrationValue, "maxFiltrationValue");
        this.maxFiltrationValue = Integer.parseInt(maxFiltrationValue);
    }

    public boolean useLandmarks() {
        return useLandmarks;
    }

    void setUseLandmarks(String useLandmarks) {
        CheckStringParam(useLandmarks, "useLandmarks");
        this.useLandmarks = Boolean.parseBoolean(useLandmarks);
    }

    public Path getOutputFolder() {
        return outputFolder;
    }

    void setOutputFolder(String outputFolder) {
        CheckStringParam(outputFolder, "outputFolder");
        this.outputFolder = Paths.get(outputFolder.replace("\"", ""));
    }

    public int getMaxLandmarks() {
        return maxLandmarks;
    }

    void setMaxLandmarks(String maxLandmarks) {
        CheckStringParam(maxLandmarks, "maxLandmarks");
        this.maxLandmarks = Integer.parseInt(maxLandmarks);
    }

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
