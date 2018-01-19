package com.genix.wordmemoriser.Adapters;

/**
 * Created by genix on 1/19/2018.
 */

public class SetNamesForAdapter {
    private String setName;
    private int id;

    public SetNamesForAdapter(String setName, int id) {
        this.setName = setName;
        this.id = id;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
