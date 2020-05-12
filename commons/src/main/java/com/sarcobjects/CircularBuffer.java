package com.sarcobjects;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CircularBuffer {

    static final List<String> TEXTS_LIST = List.of(
            "AA es American Airlines, AR es Aerolíneas Argentinas",
            "AR es Aerolíneas Argentinas, AA es American Airlines",
            "American Airlines es AA, Aerolíneas Argentinas es AR",
            "Aerolíneas Argentinas es AR, American Airlines es AA",
            "Código de Aerolíneas Argentinas: AR, Código de American Airlines: AA",
            "Código de American Airlines: AA, Código de Aerolíneas Argentinas: AR"
    );

    //index for cycling through the list
    private AtomicInteger index = new AtomicInteger(0);

    private static int applyAsInt(int x, int y) {
        return x >= y ? 0 : ++x;
    }

    public String getNextText() {
        int i = index.getAndAccumulate(TEXTS_LIST.size() -1,
                CircularBuffer::applyAsInt);
        return TEXTS_LIST.get(i);
    }
}
