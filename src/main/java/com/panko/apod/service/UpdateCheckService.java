package com.panko.apod.service;

import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Properties;

public class UpdateCheckService {
    private static final String GITHUB_RELEASES_ENDPOINT =
            "https://api.github.com/repos/artsiom-panko/AstronomyPictureOfTheDay/releases/latest";

    /**
     * View the latest published full release for the repository.
     *
     * <p>The latest release is the most recent non-prerelease, non-draft release,
     * sorted by the created_at attribute.The created_at attribute is the date of the commit
     * used for the release, and not the date when the release was drafted or published.
     */
    public void showNewUpdateIfAvailable() throws IOException {
        HttpResponse<String> httpResponse = new ApiService().sendHttpGetRequest(GITHUB_RELEASES_ENDPOINT);
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

    private double getCurrentAppVersion() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        return Double.parseDouble(properties.getProperty("project.version"));
    }
}
