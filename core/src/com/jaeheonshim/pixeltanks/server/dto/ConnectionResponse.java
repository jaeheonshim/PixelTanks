package com.jaeheonshim.pixeltanks.server.dto;

import java.util.UUID;

public class ConnectionResponse {
    private String assignedUUID;

    public ConnectionResponse() {
    }

    public ConnectionResponse(String assignedUUID) {
        this.assignedUUID = assignedUUID;
    }

    public String getAssignedUUID() {
        return assignedUUID;
    }

    public void setAssignedUUID(String assignedUUID) {
        this.assignedUUID = assignedUUID;
    }
}
