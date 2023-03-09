import jakarta.jms.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.annotation.Resource;

import java.io.IOException;

@WebServlet(urlPatterns = "/produce")
public class ProducerServlet extends HttpServlet {
    @Resource
    ConnectionFactory connectionFactory;
    @Resource(mappedName = "FirstQueue")
    Queue queue;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message  = req.getParameter("message");
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            connection.start();
            MessageProducer messageProducer = session.createProducer(queue);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            messageProducer.send(session.createTextMessage(message));
            connection.close();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
        dispatcher.forward(req, resp);
    }
}
