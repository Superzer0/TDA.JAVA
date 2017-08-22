package com.github.superzer0.services;

import com.github.superzer0.services.intervalsActions.SaveIntervalsAsText;
import com.github.superzer0.services.intervalsActions.SaveIntervalsDiagrams;
import com.github.superzer0.services.intervalsActions.SaveIntervalsEntropy;

import java.util.ArrayList;
import java.util.List;

public class IntervalsActionsFactory {

    public List<IIntervalsAction> getIntervalsActions() {
        List<IIntervalsAction> actions = new ArrayList<>();
        actions.add(new SaveIntervalsAsText());
        actions.add(new SaveIntervalsDiagrams());
        actions.add(new SaveIntervalsEntropy());
        return actions;
    }
}
