package sample;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class StudentServer {

    private ObjectOutputStream outputToFile;
    private ObjectInputStream inputFromClient;

    public StudentServer() {
        try {
            // Kreiranje serverske utičnice (socket)
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server started ");

            // Kreiranje izlaznog toka objekata
            outputToFile = new ObjectOutputStream(
                    new FileOutputStream("student.dat", true));

            while (true) {
                // Osluškivanje zahteva za novom vezom
                Socket socket = serverSocket.accept();

                // Kreiranje ulaznog toka objekata iz utićnice (socket)
                inputFromClient
                        = new ObjectInputStream(socket.getInputStream());

                // Čitanje sa ulaza
                Object object = inputFromClient.readObject();

                // Upisivanje u detoteku
                outputToFile.writeObject(object);
                System.out.println("A new student object is stored");
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                inputFromClient.close();
                outputToFile.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new StudentServer();
    }
}