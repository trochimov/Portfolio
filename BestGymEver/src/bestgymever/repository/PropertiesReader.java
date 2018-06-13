package bestgymever.repository;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PropertiesReader {

    private final Path path;
    private final Properties p;
    private String connectionString;
    private String username;
    private String password;

    public PropertiesReader() {
        this.path = Paths.get("settings.properties");
        this.p = new Properties();
    }

    public void loadProperties() {
        try {
            p.load(Files.newInputStream(path));
            connectionString = (p.getProperty("connectionString") + 
                    "?user=" + p.getProperty("user") 
                    + "&password=" + p.getProperty("password"));

        } catch (FileNotFoundException e) {
            System.out.println("File: " + path.getFileName() + " not found");
        } catch (IOException e) {
            System.out.println("IOexception");
        }
    }

    public String getConnectionString() {
        return connectionString;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
