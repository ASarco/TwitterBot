package com.sarcobjects;

import com.google.cloud.vision.v1p4beta1.*;
import twitter4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class ImageIdentifier {

    private static final Logger LOGGER = Logger.getLogger(ImageIdentifier.class);
    public static final String TOWER_BRIDGE = "Tower Bridge";


    public boolean detectLandmarks(List<String> urls) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        Feature feat = Feature.newBuilder().setType(Feature.Type.LANDMARK_DETECTION).build();
        for (String imageUrl : urls) {
            ImageSource imgSource = ImageSource.newBuilder().setImageUri(imageUrl).build();
            Image img = Image.newBuilder().setSource(imgSource).build();
            AnnotateImageRequest request =
                    AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
            requests.add(request);
        }

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {

            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    LOGGER.warn(format("Error: %s%n", res.getError().getMessage()));
                } else {

                    // For full list of available annotations, see http://g.co/cloud/vision/docs
                    Optional<String> towerBridgeFound = res.getLandmarkAnnotationsList().stream()
                            .map(EntityAnnotation::getDescription)
                            .filter(TOWER_BRIDGE::equals)
                            .findFirst();
                    if (towerBridgeFound.isPresent()) {
                        LOGGER.info("*** Tower Bridge identificado ***");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
