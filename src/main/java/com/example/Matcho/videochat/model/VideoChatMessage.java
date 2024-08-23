package com.example.Matcho.videochat.model;

public class VideoChatMessage {

    private String type;
    private String roomId;
    private String sender;
    private String receiver;
    private WebRtcSessionDescription sdp;
    private WebRtcCandidate candidate;

    // Constructors, Getters, and Setters
    public VideoChatMessage() {
    }

    public VideoChatMessage(String type, String roomId, String sender, String receiver) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public WebRtcSessionDescription getSdp() {
        return sdp;
    }

    public void setSdp(WebRtcSessionDescription sdp) {
        this.sdp = sdp;
    }

    public WebRtcCandidate getCandidate() {
        return candidate;
    }

    public void setCandidate(WebRtcCandidate candidate) {
        this.candidate = candidate;
    }
}