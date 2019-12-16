package util;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

public class PropertyReader {
    private Properties properties = new Properties();
    FileInputStream fis;

    public String getProperty(String property, String propertyFileName) {
        //Rewrite
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass()
                                .getClassLoader()
                                .getResourceAsStream(propertyFileName))))) {
            while (br.ready()) {
                String[] arr = br.readLine().split(" = ");
                if (arr[0].equals(property)) {
                    return arr[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "No such property in file";
    }
}
