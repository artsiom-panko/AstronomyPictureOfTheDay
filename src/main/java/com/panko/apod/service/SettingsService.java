package com.panko.apod.service;

import com.panko.apod.util.PreferencesManager;
import javafx.scene.control.TextField;

import java.net.http.HttpResponse;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import static com.panko.apod.util.PreferencesManager.APP_ABSOLUTE_PATH;
import static com.panko.apod.util.PreferencesManager.NASA_API_KEY;

public class SettingsService {
    private final PreferencesManager preferencesManager = new PreferencesManager();

    public void initializeSettingsScene(TextField apiKeyField) {
        String key = preferencesManager.readKey(NASA_API_KEY);

        if (key != null && !key.isEmpty()) {
            apiKeyField.setText(key);
        }
    }

    public boolean saveSettings(TextField apiKeyField) {
        Path applicationAbsolutePath = FileSystems.getDefault().getPath("").toAbsolutePath();
        String picturesPath = applicationAbsolutePath.toString().concat("\\pictures\\");

        String apiKey = apiKeyField.getText();

        if (isApiKeyValid(apiKey)) {
            preferencesManager.saveKey(APP_ABSOLUTE_PATH, picturesPath);
            preferencesManager.saveKey(NASA_API_KEY, apiKey);
            return true;
        } else {
            new AlertService().showWarningAlert("Invalid API key",
                    "Provided API key is wrong or disabled. " +
                            "Please, double-check entered API key or generate a new one.");
            return false;
        }
    }

    private boolean isApiKeyValid(String apiKeyToCheck) {
        if (apiKeyToCheck.isBlank()) {
            return false;
        }

        if (apiKeyToCheck.equals(preferencesManager.readKey(NASA_API_KEY))) {
            return true;
        }

        HttpResponse<String> httpResponse = new HttpRequestService().sendHttpGetRequestToNasa(apiKeyToCheck);

        return !httpResponse.body().contains("API_KEY_INVALID");
    }
}
