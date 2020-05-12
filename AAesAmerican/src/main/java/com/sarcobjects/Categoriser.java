package com.sarcobjects;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;
import twitter4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class Categoriser {

    private static final Logger LOGGER = Logger.getLogger(Categoriser.class);

    private final DocumentCategorizer docCategoriser;

    public Categoriser(String modelName) throws IOException {
        InputStream is = this.getClass().getResourceAsStream(modelName);
        DoccatModel model = new DoccatModel(is);
        docCategoriser = new DocumentCategorizerME(model);
    }

    public String categorise(String inputText) {


        double[] outcomes = docCategoriser.categorize(inputText.replaceAll("[^A-Za-z]", " ").split(" "));

        for(int i=0;i<docCategoriser.getNumberOfCategories();i++){
            LOGGER.info(docCategoriser.getCategory(i)+" : "+outcomes[i]);
        }
        String category = docCategoriser.getBestCategory(outcomes);
        LOGGER.info("Selected Category: " + category);
        return category;
    }
}
