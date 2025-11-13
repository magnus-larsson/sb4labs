# Build and run

```
./gradlew build 
java --enable-preview -jar api-provider/build/libs/api-provider-0.0.1-SNAPSHOT.jar &
java --enable-preview -jar api-consumer/build/libs/api-consumer-0.0.1-SNAPSHOT.jar &

curl localhost:7002/product-composite/2
curl localhost:7002/thread-info
kill $(jobs -p)

```