#!/bin/bash

kill -9 $(lsof -t -i :6868)
javac *.java
jar cmf serverManifest.mf server.jar *.class *.java
java -jar server.jar 6868 
