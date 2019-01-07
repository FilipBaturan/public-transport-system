package construction_and_testing.public_transport_system.selenium.util;

import java.io.File;
import java.nio.file.Paths;

public class SeleniumProperties {

    public static String CHROME_DRIVER_PATH;

    static {
        if (System.getProperty("os.name").contains("nux")) {
            CHROME_DRIVER_PATH = (new File(Paths
                    .get("src", "test", "java", "resources", "drivers", "linux", "chromedriver")
                    .toString())).getAbsolutePath();
        } else {
            CHROME_DRIVER_PATH = (new File(Paths
                    .get("src", "test", "java", "resources", "drivers", "windows", "chromedriver")
                    .toString())).getAbsolutePath();
        }
    }

}
