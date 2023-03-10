import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

@MessageDriven(mappedName = "FirstQueue")
public class Customer implements MessageListener {
    @Override
    public void onMessage(Message message) {

        String msg;
        try {
            System.out.println(msg = message.getBody(String.class));
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        FileHandler fileHandler = new FileHandler();
        String str = fileHandler.getStringfromFile(FileHandler.DEFAULT_PATH);
        System.out.println(str);
        str = str.replaceAll(msg, "*".repeat(msg.trim().length()));
        System.out.println(str);
        fileHandler.saveStringToFile(str, FileHandler.DEFAULT_PATH);
    }
}