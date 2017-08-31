package com.github.superzer0.services;

import com.github.superzer0.services.intervalsActions.SaveIntervalsAsText;
import com.github.superzer0.services.intervalsActions.SaveIntervalsDiagrams;
import com.github.superzer0.services.intervalsActions.SaveIntervalsEntropy;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory used to abstract commands that process TDA results
 * @author Jakub Kawa
 * @version 1.0
 */

public class IntervalsActionsFactory {

    /**
     * Creates command objects that are used to process TDA run output
     * @return Commands collection that processes TDA algorithm output
     */
    public List<IIntervalsAction> getIntervalsActions() {
        List<IIntervalsAction> actions = new ArrayList<>();
        actions.add(new SaveIntervalsAsText());
        actions.add(new SaveIntervalsDiagrams());
        actions.add(new SaveIntervalsEntropy());
        return actions;
    }
}
