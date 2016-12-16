package Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class ConfigManager {

    private static final String ENV_CONF_FILENAME = "env.conf";
    private static final String INPUT_GRAPH_FILE_PATH = "inputGraphFilePath";

    private static final ConfigManager INSTANCE = new ConfigManager();
    private Config config;


    private ConfigManager() {

    }


    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    public Config getConfig() throws IOException {

        if (config == null) {

            Properties prop = new Properties();
            InputStream input = null;
            try {
                String filename = ENV_CONF_FILENAME;
                input = this.getClass().getResourceAsStream(filename);

                prop.load(input);

            } finally {
                try {
                    if (input != null)
                        input.close();
                } catch (IOException e) {
                    throw e;
                }
            }


            config = new Config();
            config.setInputGraphFilePath(prop.getProperty(INPUT_GRAPH_FILE_PATH));
        }
        return config;
    }


    public static class Config {


        private String inputGraphFilePath;

        public String getInputGraphFilePath() {
            return inputGraphFilePath;
        }

        public void setInputGraphFilePath(String inputGraphFilePath) {
            this.inputGraphFilePath = inputGraphFilePath;
        }


    }
}
