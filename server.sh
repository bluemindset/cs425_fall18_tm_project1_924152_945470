#!/bin/bash

javac *.java
jar cmf serverManifest.mf server.jar *.class *.java
java -jar server.jar 6868 