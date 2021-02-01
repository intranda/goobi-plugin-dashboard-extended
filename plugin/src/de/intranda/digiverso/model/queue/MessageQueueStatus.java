package de.intranda.digiverso.model.queue;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.configuration.XMLConfiguration;
import org.goobi.api.mq.ExternalConnectionFactory;
import org.goobi.api.mq.QueueType;
import org.goobi.managedbeans.ProcessBean;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;

import de.sub.goobi.config.ConfigurationHelper;
import de.sub.goobi.helper.Helper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MessageQueueStatus {

    @Getter
    private boolean showMessageQueue = false;

    private ActiveMQConnection internalQueueConnection;
    private QueueSession queueSession;

    @Getter
    private List<TicketType> fastQueueContent = new ArrayList<>();

    @Getter
    private List<TicketType> slowQueueContent = new ArrayList<>();

    @Getter
    private List<TicketType> externalQueueContent = new ArrayList<>();

    @Getter
    @Setter
    private TicketType currentType;

    public MessageQueueStatus(XMLConfiguration pluginConfig) {

        showMessageQueue = pluginConfig.getBoolean("queue-show", false);

        if (ConfigurationHelper.getInstance().isStartInternalMessageBroker()) {
            // internal queue is enabled

            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

            try {
                internalQueueConnection =
                        (ActiveMQConnection) connectionFactory.createConnection(ConfigurationHelper.getInstance().getMessageBrokerUsername(),
                                ConfigurationHelper.getInstance().getMessageBrokerPassword());
                queueSession = internalQueueConnection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);

                loadFastQueueContent();
                loadSlowQueueContent();
                loadExternalQueueContent();
            } catch (JMSException e) {
                log.error(e);
            }

        }
        if (ConfigurationHelper.getInstance().getExternalQueueType().equals("ACTIVEMQ")) {

        }
    }

    public void loadExternalQueueContent() {
        try {
            if (ConfigurationHelper.getInstance().getExternalQueueType().equals("ACTIVEMQ")) {
                Connection conn = ExternalConnectionFactory.createConnection(ConfigurationHelper.getInstance().getMessageBrokerUsername(),
                        ConfigurationHelper.getInstance().getMessageBrokerPassword());
                conn.start();
                Session queueSession =  conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
                Queue queue = queueSession.createQueue(QueueType.EXTERNAL_QUEUE.getName());
                QueueBrowser browser = queueSession.createBrowser(queue);
                Enumeration<?> messagesInQueue = browser.getEnumeration();
                while (messagesInQueue.hasMoreElements()) {
                    javax.jms.Message queueMessage = (javax.jms.Message) messagesInQueue.nextElement();

                    //                ActiveMQTextMessage queueMessage = (ActiveMQTextMessage) messagesInQueue.nextElement();

                    String type = queueMessage.getStringProperty("ticketType");
                    int processid = queueMessage.getIntProperty("processid");
                    TicketType currentType = null;
                    for (TicketType tt : externalQueueContent) {
                        if (tt.getTicketName().equals(type)) {
                            currentType = tt;
                            break;
                        }
                    }
                    if (currentType == null) {
                        currentType = new TicketType(type);
                        externalQueueContent.add(currentType);
                    }
                    currentType.addProcessId(processid);
                }
                conn.stop();
            } else {



                ConfigurationHelper config = ConfigurationHelper.getInstance();
                AmazonSQS client;
                if (config.isUseLocalSQS()) {
                    String endpoint = "http://localhost:9324";
                    String region = "elasticmq";
                    String accessKey = "x";
                    String secretKey = "x";
                    client = AmazonSQSClientBuilder.standard()
                            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                            .build();
                } else {
                    client = AmazonSQSClientBuilder.defaultClient();
                }
                String queueUrl = client.getQueueUrl(QueueType.EXTERNAL_QUEUE.getName()).getQueueUrl();

                List<Message> messages = client.receiveMessage(queueUrl).getMessages();

                for (Message m : messages) {
                    System.out.println("Attributes: " + m.getAttributes());
                    System.out.println("getBody: " + m.getBody());
                    System.out.println("getMessageId: " + m.getMessageId());
                    System.out.println("getReceiptHandle: " + m.getReceiptHandle());
                    System.out.println("getMessageAttributes: " + m.getMessageAttributes());


                }

            }

        } catch (JMSException e) {
            log.error(e);
        }

    }

    public void loadFastQueueContent() {
        if (ConfigurationHelper.getInstance().isStartInternalMessageBroker()) {
            try {
                internalQueueConnection.start();
                Queue queue = queueSession.createQueue(QueueType.FAST_QUEUE.getName());
                QueueBrowser browser = queueSession.createBrowser(queue);
                Enumeration<?> messagesInQueue = browser.getEnumeration();
                while (messagesInQueue.hasMoreElements()) {
                    ActiveMQTextMessage queueMessage = (ActiveMQTextMessage) messagesInQueue.nextElement();

                    String type = queueMessage.getStringProperty("ticketType");
                    int processid = queueMessage.getIntProperty("processid");
                    TicketType currentType = null;
                    for (TicketType tt : fastQueueContent) {
                        if (tt.getTicketName().equals(type)) {
                            currentType = tt;
                            break;
                        }
                    }
                    if (currentType == null) {
                        currentType = new TicketType(type);
                        fastQueueContent.add(currentType);
                    }
                    currentType.addProcessId(processid);
                }
                internalQueueConnection.stop();
            } catch (JMSException e) {
                log.error(e);
            }
        }
    }

    public void loadSlowQueueContent() {
        if (ConfigurationHelper.getInstance().isStartInternalMessageBroker()) {
            try {
                internalQueueConnection.start();
                Queue queue = queueSession.createQueue(QueueType.SLOW_QUEUE.getName());
                QueueBrowser browser = queueSession.createBrowser(queue);
                Enumeration<?> messagesInQueue = browser.getEnumeration();
                while (messagesInQueue.hasMoreElements()) {
                    ActiveMQTextMessage queueMessage = (ActiveMQTextMessage) messagesInQueue.nextElement();

                    String type = queueMessage.getStringProperty("ticketType");
                    int processid = queueMessage.getIntProperty("processid");
                    TicketType currentType = null;
                    for (TicketType tt : slowQueueContent) {
                        if (tt.getTicketName().equals(type)) {
                            currentType = tt;
                            break;
                        }
                    }
                    if (currentType == null) {
                        currentType = new TicketType(type);
                        slowQueueContent.add(currentType);
                    }
                    currentType.addProcessId(processid);
                }
                internalQueueConnection.stop();
            } catch (JMSException e) {
                log.error(e);
            }
        }
    }

    public String loadProcesses() {
        StringBuilder searchFilter = new StringBuilder();

        for (Integer processId : currentType.getProcessIds()) {
            if (searchFilter.length() > 0) {
                searchFilter.append(" ");
            }
            searchFilter.append(processId);
        }

        ProcessBean bean = (ProcessBean) Helper.getBeanByName("ProzessverwaltungForm", ProcessBean.class);
        bean.setModusAnzeige("");
        bean.setFilter("\"id:" + searchFilter.toString() + "\"");

        return bean.FilterAlleStart();

    }
}
