package com.example.Matcho.videochat.model;

public class WebRtcSessionDescription {

    private String type;
    private String sdp;

    // Constructors, Getters, and Setters
    public WebRtcSessionDescription() {
    }

    public WebRtcSessionDescription(String type, String sdp) {
        this.type = type;
        this.sdp = sdp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSdp() {
        return sdp;
    }

    public void setSdp(String sdp) {
        this.sdp = sdp;
    }
}
