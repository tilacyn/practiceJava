package ru.tilacyn.ftp;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * a class that performs the server path of work
 * supports multi-client work
 */
public class Server {

    private ServerSocket serverSocket;

    /**
     * a constructor that connects the main ServerSocket to the specified port
     * @param port port we want to listen
     * @throws IOException if problems with socket creating occurred
     */
    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * starts the server so that it could process requests
     */
    public void start() {
        System.out.println(serverSocket.getLocalPort());
        while (true) {
            try {
                Socket s = serverSocket.accept();
                Thread thread = new Thread(() -> {
                    try {
                        processRequest(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            } catch (IOException e) {

            }
        }
    }


    private void processGet(@NotNull DataInputStream is, @NotNull DataOutputStream os) throws IOException {
        char c;
        StringBuilder path = new StringBuilder();
        while ((int) (c = is.readChar()) != -1) {
            path.append(c);
        }
        File file = new File(path.toString());
        os.writeChars(file.getName());

        if (!file.exists()) {
            os.writeInt(0);
        }

        os.writeInt((int) file.length());

        FileInputStream fis = new FileInputStream(file);

        byte[] buffer = new byte[(int) file.length()];

        fis.read(buffer);
        fis.close();

        os.write(buffer);
    }

    private void processList(@NotNull DataInputStream is, @NotNull DataOutputStream os) throws Exception {
        System.out.println("In processList");


        char c;
        StringBuilder path = new StringBuilder();
        while ((int) (c = is.readChar()) != -1) {
            path.append(c);
        }
        File dir = new File(path.toString());

        if (!dir.exists()) {
            throw new Exception("This file does not exist");
        }

        if (!dir.isDirectory()) {
            os.writeInt(0);
            return;
        }

        File[] children = dir.listFiles();

        if (children == null) {
            os.writeInt(0);
            return;
        }

        int number = children.length;

        os.writeInt(number);

        for (File child : children) {
            os.writeUTF(child.getName());
            if (child.isDirectory()) {
                os.writeBoolean(true);
            } else {
                os.writeBoolean(false);
            }
        }
        System.out.println("End of processList");

    }

    private void processRequest(Socket s) throws Exception {
        try (DataOutputStream os = new DataOutputStream(s.getOutputStream());
             DataInputStream is = new DataInputStream(s.getInputStream())) {
            while (s.isConnected()) {
                int request = is.readInt();

                if (request == 1) {
                    processList(is, os);
                } else if (request == 2) {
                    processGet(is, os);
                }
            }
        }


    }

    /**
     * starts the server
     * it should be called before Client start
     * @param args program arguments, should contain exactly one integer - port for server creating
     */
    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            System.out.println("Bad input!");
            return;
        }

        Server server;
        try {
            server = new Server(Integer.parseInt(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not create client " + e.getMessage());
            return;
        }

        server.start();

    }


}
