<img height="100" src="src\main\resources\img\logo.ico" width="100"/>

## Astronomy picture of the day
Discover the cosmos! This JavaFX application performs only one single function - each day updates 
your desktop image with a different photograph of our fascinating universe, 
along with a brief explanation written by a professional astronomer.

Application is based on Java Platform Module System (JPMS), what means you
can download the .exe file and run it out of the box, without JRE on your computer.

[//]: # (![Rocket launches: ]&#40;https://api.countapi.xyz/get/com.panko.astronomy_picture_of_the_day/launches&#41;)

# Requirements
- OS Windows 8+
- Generated API Key on [NASA Astronomy Picture of the Day API](https://data.nasa.gov/Space-Science/Astronomy-Picture-of-the-Day-API/ez2w-t8ua)
- (Optional) Set background image type - **Fit** on System settings page. 
Open Personalization > Background > Picture > Choose a fit > Fit

# Screenshots
<img height="120" width="120" src="src\main\resources\img\shortcut_logo.png"/>
<img height="300" width="350" src="src\main\resources\img\loading_screenshot_1.png"/>
<img height="300" width="350" src="src\main\resources\img\loading_screenshot_2.png"/>

# References
- [NASA Astronomy Picture of the Day WebSite](https://apod.nasa.gov/apod/astropix.html)
- [NASA Astronomy Picture of the Day API](https://data.nasa.gov/Space-Science/Astronomy-Picture-of-the-Day-API/ez2w-t8ua)

## Building executable JAR and .exe files 
Application is based on Java Platform Module System (JPMS), what means
the final JAR and EXE files contains all the required dependencies and JRE inside, what means you
can download the .exe file and run it out of the box, without JRE on your computer.

There are two building options:
1) Building executable JAR (Based on moditect plugin)
Run the next command manually or execute maven 'package' plugin
```
mvn clean package
```

2) Building executable .exe file (Based on org.panteleyev plugin)
Run the next command manually
```
mvn clean package jpackage:jpackage
```


## TO-DO plans
- To add Error handling
- To add API key verification after input
- Fix all the Critical sonarlint issues
- To add update checker
- (optional) To add number of launches
- (optional) Multi languages support
