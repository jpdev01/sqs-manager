import sqs.SqsManagerService;

public class Application {

    public static void main(String[] args) {
        SqsManagerService tester = new SqsManagerService();
        tester.run();
    }

}
