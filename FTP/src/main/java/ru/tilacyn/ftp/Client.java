package ru.tilacyn.ftp;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * class for client part of FTP server
 * it can create requests to the server of two probable types
 *
 * first type is "list(dir)"
 * these requests should return a collection of Client.File objects which have some information
 * about files in path.listFiles() : name and a flag whether it is a directory
 *
 * second type is "get(file)"
 * these request should return a Client.ContentFile object with a filename and a byte array of files content
 */
public class Client {

    private DataInputStream is;
    private DataOutputStream os;

    /**
     * a constructor for a Client which creates a socket and initializes Data Streams with the socket fields
     * @param host server host we want to connect
     * @param port server port we want to connect
     * @throws IOException if problems with creating socket occurred
     */
    public Client(@NotNull String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        is = new DataInputStream(socket.getInputStream());
        os = new DataOutputStream(socket.getOutputStream());
    }

    Client(@NotNull Socket socket) throws IOException {
        is = new DataInputStream(socket.getInputStream());
        os = new DataOutputStream(socket.getOutputStream());
    }

    private void request(int type, @NotNull String fileName) throws IOException {
        os.writeInt(type);
        os.writeChars(fileName);
        os.flush();
    }

    /**
     * creates a request of type "1", gets a request result from a server
     * @param dirName directory name we want to find about
     * @return a collection of Client.File objects which have some information
     * about files in path.listFiles() : name and a flag whether it is a directory
     * @throws IOException if problems with connection occurred
     */
    public ArrayList<Client.File> list(@NotNull String dirName) throws IOException {
        request(1, dirName);
        int number = is.readInt();

        ArrayList<Client.File> result = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            String fileName = is.readUTF();
            Boolean isDir = is.readBoolean();
            result.add(File.createFile(fileName, isDir));
        }

        return result;
    }

    /**
     * creates a request of type "2", gets a result for the server
     * @param path path to file we want to find about
     * @return Client.ContentFile object with a filename and a byte array of files content
     * @throws IOException if problems with connection occurred
     */
    public Client.ContentFile get(@NotNull String path) throws IOException {
        request(2, path);
        int size = is.readInt();

        byte[] content = new byte[size];

        is.read(content);

        return new ContentFile(path, content);
    }


    /**
     * starts a Client, reads requests from the stdout. Each request (Int : type, String : path)
     * @param args program arguments, first is host, second is port to create Socket
     */
    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.out.println("Bad input!");
            return;
        }

        Client client;
        try {
            client = new Client(args[0], Integer.parseInt(args[1]));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not create client " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            int request = scanner.nextInt();
            String s = scanner.next();

            try {
                if (request == 1) {
                    System.out.println(client.list(s));
                } else if (request == 2) {
                    System.out.println(client.get(s));
                } else {
                    break;
                }
            } catch(IOException e) {
                e.printStackTrace();
                System.out.println("Could not successfully make a request " + e.getMessage());
            }
        }

        scanner.close();

    }


    /**
     * additional class for necessary File representation
     */
    public static class File {
        private String name;

        /**
         * is directory or not
         */
        Boolean isDir = null;

        /**
         * simple constructor
         * @param name file name
         */
        public File(@NotNull String name) {
            this.name = name;
        }

        /**
         * creates a file or a directory
         * @param name file name
         * @param isDir if true than creates directory, else creates file
         * @return new file
         */
        public static File createFile(@NotNull String name, boolean isDir) {
            if (!isDir) {
                return new File(name);
            } else {
                return new Directory(name);
            }
        }

        /**
         * returns file name
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * returns whether a file is a directory
         * @return whether a file is a directory
         */
        public Boolean isDir() {
            return isDir;
        }

        /**
         * simple equals that checks whether the object fields are the same
         * @param obj another object
         * @return true if they are equal, false else
         */
        @Override
        public boolean equals(Object obj) {
            File other = (File) obj;
            return name.equals(other.name) && isDir.equals(other.isDir);
        }
    }

    /**
     * directory with a fixed parameter isDir = true
     */
    public static class Directory extends File {

        /**
         * simple constructor
         * @param name file name
         */
        public Directory(@NotNull String name) {
            super(name);
            isDir = true;
        }
    }

    /**
     * stores file content for "get" client function
     */
    public static class ContentFile extends File {

        private byte[] content;

        {
            isDir = false;
        }

        /**
         * simple contructor
         * @param name file name
         * @param content file content
         */
        public ContentFile(@NotNull String name, byte[] content) {
            super(name);
            this.content = content;
        }

        /**
         * returns content
         * @return content
         */
        public byte[] getContent() {
            return content;
        }

        /**
         * a simple equals method, checks for fields to be equal
         * @param obj another object
         * @return true if equals, false else
         */
        @Override
        public boolean equals(Object obj) {
            ContentFile cf = (ContentFile) obj;
            return getName().equals(cf.getName()) && Arrays.equals(getContent(), cf.getContent());
        }
    }
}
