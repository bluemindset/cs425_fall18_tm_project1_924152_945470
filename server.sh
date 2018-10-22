#!/bin/bash

kill -9 $(lsof -t -i :6868)
javac *.java
jar cmf serverManifest.mf server.jar *.class *.java
java -jar -Xmx6g server.jar 6868 
