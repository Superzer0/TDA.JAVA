package com.github.superzer0.services;

import com.esotericsoftware.minlog.Log;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class IoService {

    public double[][] readPointsCloud(ProcessingInputParams processingInputParams) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(
                new File(processingInputParams.getPointsCloudFilePath().toString()), double[][].class);
    }

    public ProcessingInputParams getInputParams(String[] args) {
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

        Log.info("Input Params: " + inputParams.toString());

        return inputParams;
    }

}
