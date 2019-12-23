package util;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

public class PropertyReader {
    private Properties properties;
    private InputStreamReader in;

    public PropertyReader(String propertyFileName) {
        properties = new Properties();
        in = new InputStreamReader(Objects.requireNonNull(getClass()
                .getClassLoader()
                .getResourceAsStream(propertyFileName)));

        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Exception in property file");
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getProperty(String property) {
        return properties.getProperty(property);
    }
}
