import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Configuration {

    /**
     * File containing the chosen User settings to be used
     */
    private final String userSettings = "src\\UserSettings.json";

    /**
     * Tool used to interpret json files
     */
    private Gson gson;

    Configuration() {
        gson = new Gson();

        Type listType = new TypeToken<List<String>>() {}.getType();
        List<String> test = gson.fromJson(settingsFileContent(), listType);
    }

    /**
     * Gets the content of the user settings file
     *
     * @return string containing the file content
     */
    private String settingsFileContent() {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(userSettings));
            return new String(encoded, Charset.defaultCharset());
        } catch (java.io.IOException e) {
            System.err.println("Failed to read configuration's file.");
            return null;
        }
    }
}
