package ru.tilacyn.ftp;

import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class ClientTest {

    @Test
    public void mockedList() throws Exception {
        Socket socket = mock(Socket.class);

        when(socket.getInputStream()).thenReturn(
                getInputStream(
                        createClientFileArray(
                                Arrays.asList("2", "3", "4"))));

        when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        Client client = new Client(socket);
        assertEquals(client.list("1"), new ArrayList<>(createClientFileArray(Arrays.asList("2", "3", "4"))));
    }

    @Test
    public void mockedGet() throws Exception {
        Socket socket = mock(Socket.class);

        byte[] content = {1, 2, 3, 4};

        when(socket.getInputStream()).thenReturn(
                getInputStream(new Client.ContentFile("file", content)));

        when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        Client client = new Client(socket);
        assertEquals(client.get("1"), new Client.ContentFile("file", content));
    }

    private InputStream getInputStream(List<Client.File> files) throws IOException {
        int number = files.size();

        ByteArrayOutputStream b;

        DataOutputStream os = new DataOutputStream(b = new ByteArrayOutputStream());

        os.writeInt(number);

        for (Client.File child : files) {
            os.writeUTF(child.getName());
            if (child.isDir()) {
                os.writeBoolean(true);
            } else {
                os.writeBoolean(false);
            }
        }
        return new ByteArrayInputStream(b.toByteArray());
    }

    private List<Client.File> createClientFileArray(List<String> names) {
        return names
                .stream()
                .map(name -> Client.File.createFile(name, true))
                .collect(Collectors.toList());
    }

    private InputStream getInputStream(Client.ContentFile file) throws IOException {
        int length = file.getContent().length;

        ByteArrayOutputStream b;

        DataOutputStream os = new DataOutputStream(b = new ByteArrayOutputStream());

        os.writeInt(length);

        os.write(file.getContent());

        return new ByteArrayInputStream(b.toByteArray());

    }

}