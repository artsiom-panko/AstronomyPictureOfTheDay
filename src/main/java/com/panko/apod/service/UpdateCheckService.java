package com.panko.apod.service;

import com.panko.apod.entity.SceneController;
import org.json.JSONObject;

import java.net.http.HttpResponse;

public class UpdateCheckService implements SceneController {
    private static final String GITHUB_RELEASES_ENDPOINT =
            "https://api.github.com/repos/artsiom-panko/AstronomyPictureOfTheDay/releases/latest";

    private SceneService sceneService;

    /**
     * View the latest published full release for the repository.
     *
     * <p>The latest release is the most recent non-prerelease, non-draft release,
     * sorted by the created_at attribute.The created_at attribute is the date of the commit
     * used for the release, and not the date when the release was drafted or published.
     */
    public boolean ifNewVersionAvailable() {
        HttpResponse<String> httpResponse = new ApiService().sendHttpGetRequest(GITHUB_RELEASES_ENDPOINT);
        JSONObject responseBody = new JSONObject(httpResponse.body());
        String latestReleaseVersionName = responseBody.getString("name");
        String latestReleaseHtmlLink = responseBody.getString("html_url");
        String latestReleaseDescription = responseBody.getString("body");

        double gitHubVersion = Double.parseDouble(latestReleaseVersionName.replaceAll("[a-zA-Z-]+", ""));
        // TODO implement getting current project version
        double currentAppVersion = 0.8d;

        if (gitHubVersion > currentAppVersion) {
            new AlertService().showUpdateAlert(latestReleaseHtmlLink, latestReleaseDescription);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setSceneService(SceneService sceneService) {
        this.sceneService = sceneService;
    }
}
