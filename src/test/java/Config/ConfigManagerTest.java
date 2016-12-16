package Config;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by karim on 12/16/2016.
 */
public class ConfigManagerTest {

    @Test
    public void testGetConfig() throws Exception {

        System.out.println(ConfigManager.getInstance().getConfig().getInputGraphFilePath());
    }
}