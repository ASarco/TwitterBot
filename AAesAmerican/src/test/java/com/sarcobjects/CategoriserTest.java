package com.sarcobjects;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoriserTest {

    private Categoriser categoriser;

    @Before
    public void setUp() throws IOException {
        categoriser = new Categoriser("/es-doccat.bin");
    }

    @Test
    public void testCategorise() throws Exception {
        String category =
                categoriser.categorise("sindicalistas mafiosos que viven de AA ");
        assertThat(category).isEqualTo("Otro");
    }
}