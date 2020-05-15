package com.sarcobjects;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CircularBufferTest {

    CircularBuffer circularBuffer;

    @Before
    public void setUp() {
        circularBuffer = new CircularBuffer(List.of("AA es American Airlines, AR es Aerolíneas Argentinas",
                "AR es Aerolíneas Argentinas, AA es American Airlines",
                "American Airlines es AA, Aerolíneas Argentinas es AR",
                "Aerolíneas Argentinas es AR, American Airlines es AA",
                "Código de Aerolíneas Argentinas: AR, Código de American Airlines: AA",
                "Código de American Airlines: AA, Código de Aerolíneas Argentinas: AR"));
    }

    @Test
    public void testGetNextText() throws Exception {
        List<String> statuses = List.of(circularBuffer.getNextText(),
                circularBuffer.getNextText(),
                circularBuffer.getNextText(),
                circularBuffer.getNextText(),
                circularBuffer.getNextText(),
                circularBuffer.getNextText(),
                circularBuffer.getNextText());


        assertThat(statuses)
                .containsExactly(
                        "AA es American Airlines, AR es Aerolíneas Argentinas",
                        "AR es Aerolíneas Argentinas, AA es American Airlines",
                        "American Airlines es AA, Aerolíneas Argentinas es AR",
                        "Aerolíneas Argentinas es AR, American Airlines es AA",
                        "Código de Aerolíneas Argentinas: AR, Código de American Airlines: AA",
                        "Código de American Airlines: AA, Código de Aerolíneas Argentinas: AR",
                        "AA es American Airlines, AR es Aerolíneas Argentinas");
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme