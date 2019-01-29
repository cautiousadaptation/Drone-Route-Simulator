# Drone Route Simulator -  Cautious Adaptation for Defiant Components

#### SEAMS 2019 artifact

## Summary
This repository contains all the resources supporting a paper at SEAMS 2019, a demo application for our approach (Cautious Adaptation for Defiant Components) and its sources and tools. This text provides instructions on how to use the application.

As described in the accompanying paper in greater detail, the purpose of this application is to evaluate our approach, by simulating a drone route along a track between two hospitals. Through the use of this application, it is possible to observe the different behaviors in the original implementation of the drone and the drone with a wrapper developed using aspect-oriented programming.  

It is written in Java, and developed using IntelliJ IDE. Also, it takes advantage of the following Java technologies: JavaFX and AspectJ.

## Artifact structure
This repository contains the following items:

- **README.md** - This text as Markdown.
- **index.html** - This text as HTML.
- **src/** - Source directory containing the SEAMS demo application code.
- **aspectj-1.9.2/** - Directory for the AspectJ resources used in the aspect implementation.
- **ExecuteApplication/** - Directory including Docker image and .JAR files for executing in Ubuntu and Windows, each one having its proper instructions to run.

## Platform requirements
There are artifact's executables in .JAR format and Dockers images available in this repository to run on Windows 10 or Ubuntu machine. Note that running the artifact on other versions of Microsoft Windows or other Linux versions would probably work, but it is not extensively tested and therefore not officially supported.

## Getting started 
The easiest way to run the artifact is by running the .JAR file, since it only needs a Java Runtime Environment (JRE) installed to work. To run using Docker images, there are files named "Steps.txt" inside the "Docker" directory, which is located in "ExecuteApplication/Ubuntu" and also in "ExecuteApplication/Windows" directories. In this file, a step-by-step for configuring Docker to run the application using its container image is described.

## Running the Simulator - Step-by-step

This sections presents a step-by-step tutorial to use the artifact. Also, it is possible to watch a usage demonstration of this application in: https://www.youtube.com/watch?v=6Hsr2Q9FqZg.

### 1) Setting up Environment

The first step of using the simulator is environment construction. The available elements to insert are:
- River, which cause a drone loss if it lands on them;
- Hospital, which could be set as Source or Target;
- Antenna, which emits waves that eventually causes bad connection to drones located in its adjacent blocks;
- Drone, the main element. It has its own properties that are described in the next steps.

This application provides two options for setting environment:
- create an environment from scratch, inserting each element one by one. For inserting a element, select the button of the respective element, and click at the desired position on grid.
- Use an example environment provided in the application, by accessing "Menu -> London Example".

### 2) Setting Drone Configuration

The next step is configuring drone elements. Select a drone to manipulate its configurations:
- Battery consumption per block: it sets the percentage of battery consumed when the selected drone move from a block to an adjacent block.
- Battery consumption per second: it sets the percentage of battery consumed per second while the selected drone is on.
- Initial battery: the initial percentage value for the selected drone.
- Wrapper checkbox: Checked implies that wrapper implementation is present. Unchecked implies that the selected drone will have its original behavior.
- Automatic checkbox: Checked implies that the selected drone will move forward trying to reach its destiny automatically. Unchecked implies that the selected drone will work by responding the controls presented next.

Controls for Manoeuvring Drones:
- **R** key: turn on/off the drone.
- **SPACEBAR** key: drone takes off/lands.
- **W**, **A**, **S** and **D** keys: drone moves up, left, down and right, respectively.

### 3) Starting the application

The final step is start the execution of the application, by clicking the "Ready" button. That triggers the execution of each drone inserted in the enviroment simultaneously. The results are printed in a text area, where it is possible to verify the messages of each drone that is working at the moment. 

To restart the execution, clicking at the "Restart" button will reset the logs's text area, and restore the initial position of the drones inserted.

When the simulation ends, the logs printed can be used to analyze the cautious adaptation approach, which is reported in greater detail in the evaluation section in the accompanying paper.


## Contact
When faced with complications regarding the setup or use of the artifact, do not hesitate to contact any of the authors for support. The authors can be reached at cautiousadaptation@gmail.com.
