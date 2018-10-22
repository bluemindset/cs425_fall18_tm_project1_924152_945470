#!/bin/bash

javac *.java
jar cmf clientManifest.mf client.jar *.class *.java
java -jar client.jar localhost  6868 1 
#34.210.67.188