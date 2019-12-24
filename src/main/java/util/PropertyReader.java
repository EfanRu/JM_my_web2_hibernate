package util;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

public class PropertyReader {
    private static Properties properties;
    private static InputStreamReader in;
    private static String propertiesFileName = "DB.properties";

    static {
        in = new InputStreamReader(Objects.requireNonNull(PropertyReader.class
                .getClassLoader()
                .getResourceAsStream(propertiesFileName)));
        properties = new Properties();

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

    public PropertyReader() {}

    public static String getProperty(String property) {
        return properties.getProperty(property);
    }
}
