package client;

import server.Service;
import util.User;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.server.RMIClientSocketFactory;
import java.security.NoSuchAlgorithmException;

public class Client implements RMIClientSocketFactory, Serializable {

    public static void main(String[] args) throws IOException, NotBoundException, NoSuchAlgorithmException {

        Service printService = (Service) Naming.lookup("rmi://localhost:5099/printaccess1");
        User alice = new User("Alice", "123456");
        User bob = new User("Bob", "123456");
        User cecilia = new User("Cecilia", "123456");
        User david = new User("David", "123456");
        User erica = new User("Erica", "123456");
        User fred = new User("Fred", "123456");
        User george = new User("George", "123456");
        //User intruder = new User("Alice", "password");

        //Assumed that users are in the database. So this call is just for test purposes.
        /*printService.addUser(alice, 1);
        printService.addUser(bob, 2);
        printService.addUser(cecilia, 3);
        printService.addUser(george, 4);
        printService.addUser(david, 4);
        printService.addUser(fred, 4);
        printService.addUser(erica, 4);*/


        //Assumed that printers are already added in the server. So this is just for test purposes. No authentication needed.
        System.out.println("---" + printService.addPrinter("printer1"));
        System.out.println("---" + printService.addPrinter("printer2"));
        System.out.println("---" + printService.addPrinter("printer3"));
        System.out.println("---" + printService.addPrinter("printer4"));
        System.out.println("---" + printService.addPrinter("printer5"));

        System.out.println("---" + printService.start(erica));
        System.out.println("---" + printService.start(alice));

        System.out.println("---" + printService.print("file1", "printer1", fred));
        System.out.println("---" + printService.print("file2", "printer2", bob));

        System.out.println("---" + printService.queue(fred));
        System.out.println("---" + printService.queue(bob));

        System.out.println("---" + printService.topQueue(5, cecilia));
        System.out.println("---" + printService.topQueue(8, cecilia));
        System.out.println("---" + printService.topQueue(8, george));

        System.out.println("---" + printService.queue(fred));

        System.out.println("---" + printService.setConfig("param1", "value1", alice));
        System.out.println("---" + printService.setConfig("param2", "value2", alice));
        System.out.println("---" + printService.setConfig("param3", "value3", bob));
        System.out.println("---" + printService.setConfig("param4", "value4", david));

        System.out.println("---" + printService.readConfig("param1", bob));
        System.out.println("---" + printService.readConfig("param2", bob));
        System.out.println("---" + printService.readConfig("param3", bob));
        System.out.println("---" + printService.readConfig("param4", david));

        System.out.println("---" + printService.status(alice));
        System.out.println("---" + printService.status(erica));

        System.out.println("---" + printService.restart(cecilia));
        System.out.println("---" + printService.restart(erica));

        System.out.println("---" + printService.queue(fred));
        System.out.println("---" + printService.readConfig("param1", bob));

        System.out.println("---" + printService.stop(cecilia));
        System.out.println("---" + printService.stop(alice));
    }

    public Socket createSocket(String host, int port) throws IOException {
        SocketFactory factory = SSLSocketFactory.getDefault();
        Socket socket = factory.createSocket(host, port);
        return socket;
    }
}
