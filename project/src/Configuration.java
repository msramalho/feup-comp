import com.google.gson.Gson;

public class Configuration {

    /**
     * File containing the chosen User settings to be used
     */
    private final String userSettings = "UserSettings.json";

    /**
     * Tool used to interpret json files
     */
    private Gson gson;

    Configuration() {
        gson = new Gson();

        //Test sth with json here
    }
}
