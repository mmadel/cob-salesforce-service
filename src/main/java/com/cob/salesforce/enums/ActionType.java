package com.cob.salesforce.enums;

public enum ActionType {
    PUSH("push"),
    TOUCH("touch"),
    TRIGGER_FOLLOW_UP("trigger_follow_up"),
    VISIT_FOLLOW_UP("visit_follow_up");
    public final String label;

    private ActionType(String label) {
        this.label = label;
    }
}
