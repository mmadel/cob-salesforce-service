package com.cob.salesforce.enums;

public enum State {
    POTENTIAL("potential"),
    INITIALIZE("initialize"),
    COMPLETE("complete"),
    FOLLOWUP("followup");
    public final String label;

    private State(String label) {
        this.label = label;
    }
}
