# Drone Route Simulator -  Cautious Adaptation for Defiant Components

#### SEAMS 2019 artifact
The DroneRouteSimulator simulates the drone's behaviour with and without our wrapper solution during its flight from a source to a target hospital.  

## Summary
This repository contains all resources used in the evaluation presented in the paper "Cautious Adaptation of Defiant Components", submitted to SEAMS 2019. It consists of an executable simulator of our proposed approach, its source code and a demonstration video. This text provides instructions on how to use the simulator.

As described in the accompanying paper in more details, the purpose of this artifact is to evaluate our approach by simulating a drone flight between two hospitals to delivery an organ payload. By using this simulation, it is possible to observe the differences between the behaviours of the original implementation of the drone and the drone with our wrapper solution developed using aspect-oriented programming.

The simulator is implemented in Java, uses the JavaFX technology for the Graphical User Interface, and AspectJ for aspect implementation and weaving.

## Artifact structure
This repository contains the following items:

- **README.md** - This text as Markdown.
- **index.html** - This text as HTML.
- **src/** - Source directory containing the SEAMS demo application code.
- **aspectj-1.9.2/** - Directory for the AspectJ resources used in the aspect implementation.
- **ExecuteApplication/** - Directory including Docker image and .JAR files for executing in Ubuntu and Windows, each one having its proper instructions to run.

## Platform requirements
The executable artifact is available in both a single JAR format and Docker images to run on Windows 10 or Ubuntu machines. Note that the artifact may run on other Windows and Linux versions, but it was not extensively tested in all operating systems.


## Getting started 
The easiest way to run the artifact is by running the .JAR file, since it only needs a Java Runtime Environment (JRE) installed to work. To run using Docker images, there are files named "Steps.txt" inside the "Docker" directory, which is located in "ExecuteApplication/Ubuntu" and also in "ExecuteApplication/Windows" directories. In this file, a step-by-step for configuring Docker to run the application using its container image is described.

## Running the Simulator - Step-by-step

This section presents a step-by-step tutorial to use the artifact. In addition, it is possible to watch a usage demonstration of the simulator in https://www.youtube.com/watch?v=6Hsr2Q9FqZg.

### 1) Setting up Environment

The first step of using the simulator is the environment construction. The available elements are:

- River, which the drone should not land on;
- Hospital, which can be set by the user as the Source and Target hospitals of the flight;
- Antenna, which emits waves that cause a bad connection in the drones located in its adjacent blocks;
- Drone, the main element that has its own properties, as described in the next steps.

This simulator provides two options for setting the environment:

- to create an environment from scratch, inserting each element one by one. For inserting an element, the user needs to select the button of the respective element, and click at the desired position on the grid.
- to load the example environment used in the paper, the user can access "Menu -> SEAMS paper example".

### 2) Setting Drone Configuration

The next step consists of configuring the following drone properties:

- Battery consumption per block: it sets the percentage of battery consumed when the selected drone moves from a block to an adjacent block.
- Battery consumption per second: it sets the percentage of battery consumed per second while the selected drone is flying.
- Initial battery: the initial percentage value for the selected drone.
- Wrapper checkbox: by checking this box, the user selects a drone with the wrapper implementation, while leaving the box unchecked means that the selected drone will flight according to its original behaviour.
- Automatic checkbox:  by checking this box, the user  turns the automatic pilot feature of the drone on, so it will move independently following a minimal path algorithm.  On the other hand, leaving the box unchecked implies that the user will pilot the drone manually.

Controls for piloting drones manually:
- **R** key: turn on/off the drone.
- **SPACEBAR** key: drone takes off/lands.
- **W**, **A**, **S** and **D** keys: drone moves up, left, down and right, respectively.

### 3) Starting the application

The final step consists of starting the execution of the application, by clicking the "Ready" button, which triggers the execution of each drone inserted in the environment simultaneously. The traces of each drone are printed in a text area, from which it is possible to verify the scenarios that the drones perform and the current status of their battery.

By clicking on the "Restart" button, the user restarts the execution, i.e., the initial position and initial battery of the drones are restored and the user can start a new simulation.


## Contact
If you have any problem to use the artifact, please do not hesitate to contact us by sending an email to cautiousadaptation@gmail.com.
