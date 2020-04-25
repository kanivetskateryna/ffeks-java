package com.dnu.ffeks.modelingofsystems.var11;


public class Processor {

    private Processor.State processorState;

    Processor(Processor.State processorState) {
        this.processorState = processorState;
    }

    // состояние процессора
    enum State {
        FREE, BUSY
    }

    public State getProcessorState() {
        return processorState;
    }

    public void setProcessorState(State processorState) {
        this.processorState = processorState;
    }
}
