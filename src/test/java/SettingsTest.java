import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SettingsTest {

    private static Settings settings;

    private static String defaultValue;
    private static Settings.Setting testingSetting;

    @BeforeClass
    public static void init() {
        settings = Settings.getInstance();
        testingSetting = Settings.Setting.MAIN_RESOURCE_NAME;
        defaultValue = (String) testingSetting.getDefaultValue();
    }

    @Test
    public void testGetSetting() {
        assertEquals(defaultValue, settings.getSettingValue(testingSetting));
    }

    @Test
    public void testSetSetting() {
        String newSetting = "hello_world.dat";
        settings.setSettingValue(Settings.Setting.MAIN_RESOURCE_NAME, newSetting);
        assertEquals(newSetting, settings.getSettingValue(testingSetting));
    }

    @Test
    public void testSetDefault() {
        settings.setDefault(testingSetting);
        assertEquals(defaultValue, settings.getSettingValue(testingSetting));
    }

    @After
    public void setDefaultSettings() {
        settings.setSettingValue(testingSetting, defaultValue);
    }
}
