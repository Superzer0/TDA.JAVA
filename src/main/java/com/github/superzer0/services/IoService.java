package com.github.superzer0.services;

import com.esotericsoftware.minlog.Log;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Manages IO operations related to saving TDA processing results as well as reading the points cloud
 * @author Jakub Kawa
 * @version 1.0
 */

public class IoService {

    /**
     * Reads cloud of points file in json format
     * @param processingInputParams Application processing input params
     * @return points cloud
     * @throws IOException when the input file does not exists or access is denied
     */
    public double[][] readPointsCloud(ProcessingInputParams processingInputParams) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(
                new File(processingInputParams.getPointsCloudFilePath().toString()), double[][].class);
    }

    /**
     * Loads input params from args table passed by cmd
     * @param args table of strings passed by cmd
     * @return ProcessingInputParams object that holds all data in right types
     */
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
