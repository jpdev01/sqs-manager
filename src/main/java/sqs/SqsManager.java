package sqs;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.ArrayList;
import java.util.List;

public class SqsManager {

    private final SqsClient client;
    private final String queueUrl;

    public SqsManager(String queueUrl) {
        this.queueUrl = queueUrl;
        this.client = SqsClientProxy.createClient();
    }

    public void sendMessage(String message) {
        this.client.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(message)
                .delaySeconds(1)
                .build());
    }

    public void sendBatchMessage(List<String> messageList) {
        List<SendMessageBatchRequestEntry> entries = new ArrayList<SendMessageBatchRequestEntry>();
        for (int index = 0; index  < messageList.size(); index++) {
            SendMessageBatchRequestEntry entry = SendMessageBatchRequestEntry.builder()
                    .id(String.valueOf(index))
                    .messageBody(messageList.get(index))
                    .build();
            entries.add(entry);
        }

        SendMessageBatchRequest sendMessageBatchRequest = SendMessageBatchRequest.builder()
                .queueUrl(queueUrl)
                .entries(entries)
                .build();

        client.sendMessageBatch(sendMessageBatchRequest);
    }
}
