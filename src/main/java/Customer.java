import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;


@MessageDriven(mappedName = "FirstQueue")
public class Customer implements MessageListener {
    private final String PATH = "D:\\Java\\RIS\\JMS\\src\\main\\resources\\test.txt";

    @Override
    public void onMessage(Message message) {
        String msg;
        try {
            System.out.println(msg = message.getBody(String.class));
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        String str = getStringfromFile();
        System.out.println(str);
        str = str.replaceAll(msg, "*".repeat(msg.trim().length()));
        System.out.println(str);
        saveStringToFile(str);
    }

    private String getStringfromFile() {
        System.out.println();
        Scanner scanner = null;
        StringBuilder strFromFile = new StringBuilder();
        try {
            scanner = new Scanner(Paths.get(PATH));
            while (scanner.hasNextLine()) {
                strFromFile.append(scanner.nextLine());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return strFromFile.toString();
    }

    private void saveStringToFile(String str) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(PATH, false);
            writer.write(str);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}