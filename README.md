# Drone Route Simulator -  Cautious Adaptation for Defiant Components

#### SEAMS 2019 artifact

## Summary
This repository contains all the resources supporting a paper at SEAMS 2019, a demo application for our approach (Cautious Adaptation for Defiant Components) and its sources and tools. This text provides instructions on how to use the application.

As described in the accompanying paper in greater detail, the purpose of this application is to evaluate our approach, by simulating a drone route along a track between two hospitals. Through the use of this application, it is possible to observe the different behaviors in the original implementation of the drone and the drone with a wrapper developed using aspect-oriented programming.  

It is written in Java in IntelliJ, and takes advantage of several Java technologies, such as the JavaFX and AspectJ.

## Artifact structure
This repository contains the following items:

- **README.md** - This text as Markdown.
- **index.html** - This text as HTML.
- **src/** - Source directory containing the SEAMS demo application code.
- **aspectj-1.9.2/** - AspectJ resources used in aspect implementation.
- **ExecuteApplication/** - Directory including Docker image and .JAR files for executing in Ubuntu and Windows, each one having its proper instructions to run.

## Platform requirements
There are artifact's executables in .JAR format and Dockers images available in this repository to run on Windows 10 or Ubuntu machine. Note that running the artifact on other versions of Microsoft Windows or other Linux versions would probably work, but it is not extensively tested and therefore not officially supported.

## Getting started 
The easiest way to run the artifact is by running the .JAR file, since it only needs a Java Runtime Environment (JRE) installed to work. To run using Docker images, there is a file named "Steps" in the directory inside the "Docker" directory, which is located in "ExecuteApplication/Ubuntu" and also "ExecuteApplication/Windows" directories. In this file, a step-by-step for configuring Docker to run the application using the Docker image is described.

## Running the Simulator - Step-by-step



## Contact
When faced with complications regarding the setup or use of the artifact, do not hesitate to contact any of the authors for support. The authors can be reached at cautiousadaptation@gmail.com.
