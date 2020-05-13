package com.sarcobjects;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CircularBuffer {


    //index for cycling through the list
    private AtomicInteger index = new AtomicInteger(0);

    private final List<String> textList;


    public CircularBuffer(List<String> textList) {
        this.textList = textList;
    }

    private static int applyAsInt(int x, int y) {
        return x >= y ? 0 : ++x;
    }

    public String getNextText() {
        int i = index.getAndAccumulate(textList.size() -1,
                CircularBuffer::applyAsInt);
        return textList.get(i);
    }
}
