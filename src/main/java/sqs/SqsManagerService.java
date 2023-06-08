package sqs;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SqsManagerService {

    final String queueName = "test";

    public void run() {
        SqsCreateQueueManager sqsCreateQueueManager = new SqsCreateQueueManager();

        sqsCreateQueueManager.createQueue(queueName);

        String queueUrl = sqsCreateQueueManager.getQueueUrl(queueName);
        SqsManager sqsManager = new SqsManager(queueUrl);

        sqsManager.sendMessage("Hello World!");

        List<String> entries = new ArrayList<String>();
        entries.add("blabla");
        entries.add("bleee");
        sqsManager.sendBatchMessage(entries);

        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .waitTimeSeconds(2)
                .maxNumberOfMessages(10)
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

        messages.forEach(message -> {
            System.out.println(message.body());
        });

        messages.forEach(message -> {
            System.out.println("Deletando a mensagem " + message.messageId());
            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle())
                    .build();
            sqsClient.deleteMessage(deleteMessageRequest);
        });

        sqsClient.close();
    }
}
