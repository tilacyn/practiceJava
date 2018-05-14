package ru.tilacyn.ftp;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private Client client;
    private MainLayout mainLayout;
    private String name;

    public Controller(String host, int port) throws IOException {
        client = new Client(host, port);
    }

    public ArrayList<Client.File> list() throws IOException {
        return client.list(name);
    }

    public Client.ContentFile get(String fileName) throws IOException {
        return client.get(fileName);
    }

    public void setMainLayout(MainLayout mainLayout) {
        this.mainLayout = mainLayout;
    }

    public void setFileName(String name) {
        this.name = name;
    }
}
