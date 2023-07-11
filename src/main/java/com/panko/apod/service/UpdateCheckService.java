package com.panko.apod.service;

import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Properties;

import static com.panko.apod.service.HttpRequestService.GITHUB_RELEASES_ENDPOINT;

public class UpdateCheckService {

    /**
     * Show Info-level alert message about the availability of a new app version if
     * current app version is older.
     *
     * <p> For comparison with the current version will be used the latest non-prerelease or non-draft release,
     * sorted by the created_at attribute.
     *
     * <p> The current app version stores at pom.xml and in the application.properties for installed app.
     */
    public void showNewUpdateIfAvailable() {
        HttpResponse<String> httpResponse = new HttpRequestService().sendHttpGetRequest(GITHUB_RELEASES_ENDPOINT);
        JSONObject responseBody = new JSONObject(httpResponse.body());
        String latestReleaseVersionName = responseBody.getString("name");
        String latestReleaseHtmlLink = responseBody.getString("html_url");
        String latestReleaseDescription = responseBody.getString("body");

        double currentAppVersion = getCurrentAppVersion();
        double gitHubVersion = Double.parseDouble(latestReleaseVersionName.replaceAll("[a-zA-Z-]+", ""));

        if (gitHubVersion > currentAppVersion) {
            new AlertService().showUpdateAlert(latestReleaseHtmlLink, latestReleaseDescription);
        }
    }

    public Double getCurrentAppVersion() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException exception) {
            new AlertService().showWarningAlert("Cannot get Application version. " +
                    "You can get the latest version on Github page", exception);
            return null;
        }

        return Double.parseDouble(properties.getProperty("project.version"));
    }
}
