# REST API Demo Project

This Spring Boot project is to demo various services exposed by a REST API

## Getting Started

Here's what you will need to build and run the project

### Prerequisites

* Clone the Git repo (https://github.com/Chironi/api-demo)
* Download and install Java 8 JDK (http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* Download and install Maven (https://maven.apache.org/download.cgi). If you're using OSX you can use Homebrew: ```brew install maven```


### Installing

* Run: ```mvn package``` from the command line while in the root of the project directory:
```
$ mvn package

[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building api-demo 0.0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] .... ..
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ api-demo ---
[INFO] Building jar: /Users/developer/example/spring-boot-example/target/api-demo-0.0.1-SNAPSHOT.jar
[INFO]
[INFO] --- spring-boot-maven-plugin:1.5.8.RELEASE:repackage (default) @ api-demo ---
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```
You'll now have a jar built inside the project directory. Note the name of the jar.

Run the application by running the jar with the command ```java -jar target/api-demo-0.0.1-SNAPSHOT.jar``` :
 ```
 $ java -jar target/api-demo-0.0.1-SNAPSHOT.jar
    
      .   ____          _            __ _ _
     /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
    ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
     \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
      '  |____| .__|_| |_|_| |_\__, | / / / /
     =========|_|==============|___/=/_/_/_/
     :: Spring Boot ::  (v1.5.8.RELEASE)
    ....... . . .
    ....... . . . (log output here)
    ....... . . .
    ........ Started Example in 2.536 seconds (JVM running for 2.864)
```

The application should now be running and ready for API calls

## Running the tests

* Run: ```mvn test``` from the command line while in the root of the project directory:
```
$ mvn test

[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building api-demo 0.0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] ....
[INFO] ....
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
....
....
Results :

Tests run: 19, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 11.653 s
[INFO] Finished at: 2017-11-23T00:36:43-06:00
[INFO] Final Memory: 14M/35M
[INFO] ------------------------------------------------------------------------
```

## REST API Endpoints

### Demo Services API

Just a simple Hello World endpoint.
```
curl --request GET \
  --url http://localhost:8080/hello-world \
  --header 'cache-control: no-cache'
```
Endpoint that accepts a JSON object containing a paragraph of text and returns a JSON array
of objects which represent unique words in the paragraph and the count of occurrences. The
array is sorted alphabetically. 
```
curl --request POST \
  --url http://localhost:8080/unique-words \
  --header 'cache-control: no-cache' \
  --header 'content-type: application/json' \
  --data '{\n	"paragraph" : "The paragraph to parse"\n}'
```
Endpoint that returns a JSON array of the first N Fibonacci numbers. I use the a recursive
algorithm that uses memoization for efficiency. The Big-O complexity is O(n).
```
curl --request POST \
  --url http://localhost:8080/fibonacci-numbers \
  --header 'cache-control: no-cache' \
  --header 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  --form N=100
```
Endpoint that creates two deadlocked threads. The threads run an arbitrary job which runs for
the input timeout in ms.
```
curl --request POST \
  --url http://localhost:8080/deadlock-threads \
  --header 'cache-control: no-cache' \
  --header 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  --form timeout=1000
```
Endpoint which detects the deadlocked threads and returns a list of the deadlocked thread ids.
```
curl --request GET \
  --url http://localhost:8080/detect-deadlock-threads \
  --header 'cache-control: no-cache'
```
Endpoint which calls an external web service using the Spring RestTemplate and returns a
list of Post objects from that external service.
```
curl --request GET \
  --url http://localhost:8080/external-posts-service \
  --header 'cache-control: no-cache'
```
Endpoint which calls an external web service using the Spring RestTemplate and returns a
single Post object for the provided id path parameter.
```
curl --request GET \
  --url http://localhost:8080/external-posts-service/{id} \
  --header 'cache-control: no-cache'
```

### HATEOAS REST API for Hub and Node Resources

Hub
```
curl --request GET \
  --url http://localhost:8080/api/nodes \
  --header 'cache-control: no-cache'
```

```
curl --request GET \
  --url http://localhost:8080/api/nodes/{id} \
  --header 'cache-control: no-cache'
```

```
curl --request POST \
  --url http://localhost:8080/api/nodes \
  --header 'cache-control: no-cache' \
  --header 'content-type: application/json' \
  --data '{"name": "", "label": "", "location": "", "zigbeeId": "", "networkId": "", "productId": "", "owner": "", "maxPorts": 32}'
```

```
curl --request PUT \
  --url http://localhost:8080/api/nodes/{id} \
  --header 'cache-control: no-cache' \
  --header 'content-type: application/json' \
  --data '{"name": "", "label": "", "location": "", "zigbeeId": "", "networkId": "", "productId": "", "owner": "", "maxPorts": 32}'
```

```
curl --request DELETE \
  --url http://localhost:8080/api/nodes/{id} \
  --header 'cache-control: no-cache' \
```

Node
```
curl --request GET \
  --url http://localhost:8080/api/nodes \
  --header 'cache-control: no-cache'
```

```
curl --request GET \
  --url http://localhost:8080/api/nodes/{id} \
  --header 'cache-control: no-cache'
```

```
curl --request POST \
  --url http://localhost:8080/api/nodes \
  --header 'cache-control: no-cache' \
  --header 'content-type: application/json' \
  --data '{"name": "", "label": "", "location": "", "zigbeeId": "", "networkId": "", "productId": "", "type": "", "group": "", "hubId": 1}'
```

```
curl --request PUT \
  --url http://localhost:8080/api/nodes/{id} \
  --header 'cache-control: no-cache' \
  --header 'content-type: application/json' \
  --data '{"name": "", "label": "", "location": "", "zigbeeId": "", "networkId": "", "productId": "", "type": "", "group": "", "hubId": 1}'
```

```
curl --request DELETE \
  --url http://localhost:8080/api/nodes/{id} \
  --header 'cache-control: no-cache' \
```

## Author

* **William Czaja** - https://github.com/chironi

## References Used

* https://spring.io/guides
* https://docs.spring.io/spring-boot/docs/current/reference/html/
* http://jmockit.org/tutorial/Mocking.html
* https://stackoverflow.com/questions/42365266/call-another-rest-api-from-my-server-in-spring-boot
* http://korhner.github.io/java/multithreading/detect-java-deadlocks-programmatically/
* https://gist.github.com/PurpleBooth/109311bb0361f32d87a2#file-readme-template-md
* http://docs.smartthings.com/en/latest/device-type-developers-guide/quick-start.html