package com.tlannigan.explainyourself;

import java.util.List;

public class ChangedMods {
    private List<String> removedMods;
    private List<String> addedMods;

    public ChangedMods(List<String> removedMods, List<String> addedMods) {
        this.removedMods = removedMods;
        this.addedMods = addedMods;
    }

    public List<String> getRemovedMods() {
        return removedMods;
    }

    public void setRemovedMods(List<String> removedMods) {
        this.removedMods = removedMods;
    }

    public List<String> getAddedMods() {
        return addedMods;
    }

    public void setAddedMods(List<String> addedMods) {
        this.addedMods = addedMods;
    }
}
