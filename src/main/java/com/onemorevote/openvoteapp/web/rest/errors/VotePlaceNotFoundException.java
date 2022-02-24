package com.onemorevote.openvoteapp.web.rest.errors;

public class VotePlaceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -8431804132228029725L;

    public VotePlaceNotFoundException() {
        super("Vote place not found!");
    }
}
