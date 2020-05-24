# Log file watcher

Client and server app to watch and aggregate log files. Given an interval in seconds and the path of the log file to watch, the server will check the log file every 500 ms for changes, if the file has changed it will count the number of records for each severity level in the last interval.

The interval and the file path are set up in: `./logsummary/src/main/resources/application.properties`.

The server pushes a notification with the updated counts everytime the file changes.

## Quick start

### Start the server
```sh
cd logsummary
./mvnw spring-boot:run
```

### Start the client
```sh
cd client
npm i
npm start
```
