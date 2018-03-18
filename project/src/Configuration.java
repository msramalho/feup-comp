import com.google.gson.Gson;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Configuration {
    /**
     * File containing the chosen User settings to be used
     */
    private final String userSettingsFile = "src\\UserSettings.json";

    /**
     * Tool used to interpret json files
     */
    private Gson gson;

    public Configuration() {
        gson = new Gson();

        UserSettings userSettings = gson.fromJson(settingsFileContent(), UserSettings.class);
        System.out.println(userSettings.fix.countFor + " " + userSettings.fix.countForeach + " " + userSettings.output.path);
    }

    /**
     * Gets the content of the user settings file
     *
     * @return string containing the file content
     */
    private String settingsFileContent() {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(userSettingsFile));
            return new String(encoded, Charset.defaultCharset());
        } catch (java.io.IOException e) {
            System.err.println("Failed to read configuration's file.");
            return null;
        }
    }
}
