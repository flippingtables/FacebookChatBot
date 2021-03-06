package com.sandagerdi;

import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Properties;

public class FBChat {
    static Connection connection;

    public static void main(String args[]) throws InterruptedException {
        BotFunctions b = new BotFunctions();
        String result = "";
        try {
            for (int i =0;i<20;i++){
                result =b.readLargerTextFile("begin.txt");
                System.out.println(result);
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Properties prop = new Properties();
        try {
            //load a properties file
            prop.load(new FileInputStream("login.properties"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            login(prop.getProperty("fbusername"), prop.getProperty("fbpassword"));
            System.out.println("Logged in as: " + connection.getUser());
        } catch (XMPPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // register listeners

        connection.getChatManager().addChatListener(new ChatManagerListener() {
            public void chatCreated(final Chat chat,
                                    final boolean createdLocally) {
                chat.addMessageListener(new MessageListener() {
                    public void processMessage(Chat chat, Message message) {
                        System.out.println("Received message1: "
                                + (message != null ? message.getBody() : "NULL"));
                        System.out.println("From: " + message.getFrom());

                        if (message.getBody().startsWith("Tran:")) {
                            try {

                                GoogleTranslator g = new GoogleTranslator();
                                String translatedMessage = g.translate(message.getBody());

                                messageUser(message.getFrom(), translatedMessage, connection);

                            } catch (UnsupportedEncodingException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        if (message.getBody().startsWith("Reverse:")) {
                            messageUser(message.getFrom(),
                                    reverseString(message.getBody()),
                                    connection);
                        }
                        if (message.getBody().startsWith("Calc:")) {
                            String msg = message.getBody();
                            Calculator c = new Calculator();
                            String answer = c.calc1(msg).toString();
                            messageUser(message.getFrom(), answer, connection);
                        }

                        if (message.getBody().startsWith("RandomQuote")) {
                            BotFunctions b = new BotFunctions();
                            String answer = null;
                            try {
                                answer = b.readLargerTextFile("begin.txt");
                            } catch (IOException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }
                            //String output = answer.substring(0, 1).toUpperCase() + answer.substring(1);
                            messageUser(message.getFrom(), answer, connection);
                        }

                    }
                });
            }
        });

        // idle for 20 seconds
        final long start = System.nanoTime();
        while ((System.nanoTime() - start) / 1000000 < 2000000) // do for 20
        // seconds
        {
            Thread.sleep(500);
        }
    }

    public static String getUserName(String input) {
        Roster roster = connection.getRoster();
        Collection<RosterEntry> entries = roster.getEntries();
        String foundUser = "";
        for (RosterEntry entry : entries) {

            if (StringUtils.startsWithIgnoreCase(entry.getName(), input)) {
                foundUser = entry.getUser();
            }
        }
        return foundUser;
    }

    public static void login(String userName, String password)
            throws XMPPException {
        SASLAuthentication.registerSASLMechanism("DIGEST-MD5",
                SASLXFacebookPlatformMechanism.class);
        ConnectionConfiguration config = new ConnectionConfiguration(
                "chat.facebook.com", 5222);

        config.setSASLAuthenticationEnabled(true);
        config.setRosterLoadedAtLogin(true);

        connection = new XMPPConnection(config);
        connection.connect();
        connection.login(userName, password);
    }

    public static void messageUser(String user, String message,
                                   Connection connection) {
        ChatManager chatmanager = connection.getChatManager();
        Chat newChat = chatmanager.createChat(user, new MessageListener() {
            public void processMessage(Chat chat, Message message) {
                System.out.println("Received message2: " + message);
            }
        });

        try {
            newChat.sendMessage(message);
            System.out.println(newChat.getParticipant());
        } catch (XMPPException e) {
            System.out.println("Error Delivering block");
        }
    }

    public static String reverseString(String str) {
        String reversedString = str.split(":")[1];
        return StringUtils.reverse(reversedString);
    }


}
