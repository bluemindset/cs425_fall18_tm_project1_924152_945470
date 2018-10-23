### Installing
Run the scripts to client and server machines found in source.
In order to run you need openjdk-7 pre installed.
First run the server and make sure ports of the machines are open.

```
sh client.sh 
```
```
sh server.sh 
```
You can edit the client.sh:
```
java -jar client.jar localhost 6868 10 1
localhost = hostname , 6868 = port , 10 = number of users , 1 = repetition
```