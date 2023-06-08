package sqs;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

// TO DO: MAKE AS SINGLETON
public class SqsClientProxy {

    public static SqsClient createClient() {
        SqsClient client = SqsClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create("http://localhost:4566"))
                .build();

        return client;
    }
}
