package com.cob.salesforce.services.workflow;

public abstract class DoctorStateUpdater {
    abstract void createAction();

    abstract void updateTransition();

    void execute() {
        createAction();
        updateTransition();
    }
}
