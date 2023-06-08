package sqs;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;

public class SqsCreateQueueManager {

    final SqsClient client;

    public SqsCreateQueueManager() {
        this.client = SqsClientProxy.createClient();
    }

    public void createQueue(String queueName) {
        CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                .queueName(queueName)
                .build();

        this.client.createQueue(createQueueRequest);
    }

    public String getQueueUrl(String queueName) {
        GetQueueUrlRequest getQueueUrlRequest = GetQueueUrlRequest.builder()
                .queueName(queueName)
                .build();

        return client.getQueueUrl(getQueueUrlRequest).queueUrl();
    }
}
