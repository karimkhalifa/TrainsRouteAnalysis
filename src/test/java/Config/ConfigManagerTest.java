package Config;

import org.junit.Test;

import static org.junit.Assert.*;


public class ConfigManagerTest {

    @Test
    public void testGetConfig() throws Exception {

        System.out.println(ConfigManager.getInstance().getConfig().getInputGraphFilePath());
    }
}