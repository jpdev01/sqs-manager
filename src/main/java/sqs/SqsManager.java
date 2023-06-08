package sqs;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqsManager {

    private static final int MAX_BATCH_SIZE = 10;
    private static final int LONG_POLLING_MAX_SECONDS = 2;
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
                .queueUrl(this.queueUrl)
                .entries(entries)
                .build();

        client.sendMessageBatch(sendMessageBatchRequest);
    }

    public List<Message> receiveMessage() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(this.queueUrl)
                .waitTimeSeconds(LONG_POLLING_MAX_SECONDS)
                .maxNumberOfMessages(MAX_BATCH_SIZE)
                .build();

        return client.receiveMessage(receiveMessageRequest).messages();
    }

    public void deleteMessage(Message message) {
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(this.queueUrl)
                .receiptHandle(message.receiptHandle())
                .build();
    }


}
