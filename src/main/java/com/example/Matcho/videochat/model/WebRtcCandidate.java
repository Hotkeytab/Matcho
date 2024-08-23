package com.example.Matcho.videochat.model;


public class WebRtcCandidate {

    private String sdpMid;
    private int sdpMLineIndex;
    private String candidate;

    // Constructors, Getters, and Setters
    public WebRtcCandidate() {
    }

    public WebRtcCandidate(String sdpMid, int sdpMLineIndex, String candidate) {
        this.sdpMid = sdpMid;
        this.sdpMLineIndex = sdpMLineIndex;
        this.candidate = candidate;
    }

    public String getSdpMid() {
        return sdpMid;
    }

    public void setSdpMid(String sdpMid) {
        this.sdpMid = sdpMid;
    }

    public int getSdpMLineIndex() {
        return sdpMLineIndex;
    }

    public void setSdpMLineIndex(int sdpMLineIndex) {
        this.sdpMLineIndex = sdpMLineIndex;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }
}