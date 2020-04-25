package com.dnu.ffeks.modelingofsystems;

class Channel {

    private State channelState;

    Channel(State channelState) {
        this.channelState = channelState;
    }

    // состояние канала
    enum State {
        FREE,
        BUSY,
        LOCKED
    }

    State getChannelState() {
        return channelState;
    }

    void setChannelState(State channelState) {
        this.channelState = channelState;
    }
}
