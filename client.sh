#!/bin/bash

javac *.java
jar cmf clientManifest.mf client.jar *.class *.java
java -jar client.jar 34.210.67.188  6868 10 
#34.210.67.188