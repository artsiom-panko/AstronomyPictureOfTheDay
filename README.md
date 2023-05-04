<img height="100" src="src\main\resources\img\logo.ico" width="100"/>

## Astronomy picture of the day
Discover the cosmos! This JavaFX application performs only one single function - each day updates 
your desktop image with a different photograph of our fascinating universe, 
along with a brief explanation written by a professional astronomer.

![Rocket launches: ](https://api.countapi.xyz/get/com.panko.astronomy_picture_of_the_day/launches)

# Requirements
- OS Windows 8+
- Install Java 11
- Set **JAVA_HOME** user variable in Environmental variables
- Generate API Key on [NASA Astronomy Picture of the Day API](https://data.nasa.gov/Space-Science/Astronomy-Picture-of-the-Day-API/ez2w-t8ua)
- (Optional) Set background image type - **Fit** on System settings page. 
Open Personalization > Background > Picture > Choose a fit > Fit

# Screenshots
<img height="120" width="120" src="src\main\resources\img\shortcut_logo.png"/>
<img height="300" width="350" src="src\main\resources\img\loading_screenshot_1.png"/>
<img height="300" width="350" src="src\main\resources\img\loading_screenshot_2.png"/>

# References
- [NASA Astronomy Picture of the Day WebSite](https://apod.nasa.gov/apod/astropix.html)
- [NASA Astronomy Picture of the Day API](https://data.nasa.gov/Space-Science/Astronomy-Picture-of-the-Day-API/ez2w-t8ua)

## Building jar and exe 
a) launch4j

that runs without an installer and a JRE
to download the .exe file and run it out of the box, without an installer,
even if they don't have a JRE on their computer.
https://stackoverflow.com/questions/69811401/how-to-create-a-standalone-exe-in-java-that-runs-without-an-installer-and-a-jr

Run the next command manually
```
mvn clean compile javafx:jlink jpackage:jpackage -X
```
```
mvn clean compile javafx:jlink jpackage:jpackage

mvn clean resources:copy-dependencies

mvn clean moditect:generate-module-info -X

jdeps
    --module-path C:\Users\artsi\.m2\repository\org\json\json\20230227
    --generate-module-info target\jdeps-modules C:\Users\artsi\.m2\repository\org\json\json\20230227\json-20230227.jar
    
    
```