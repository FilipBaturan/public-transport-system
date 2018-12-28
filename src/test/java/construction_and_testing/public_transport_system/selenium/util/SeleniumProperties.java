package construction_and_testing.public_transport_system.selenium.util;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

public class SeleniumProperties {

    public static String CHROME_DRIVER_PATH;

    static {
        if (System.getProperty("os.name").contains("nux")) {
            try {
                CHROME_DRIVER_PATH = ResourceUtils
                        .getURL("classpath:drivers/linux/chromedriver")
                        .getPath();
            } catch (FileNotFoundException e) {
                CHROME_DRIVER_PATH = "";
            }
        } else if (System.getProperty("os.name").startsWith("Windows")) {
            CHROME_DRIVER_PATH = "...";
        } else {
            CHROME_DRIVER_PATH = "...";
        }
    }


}
