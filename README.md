# Farm service

### Must have the mongodb server on port 27017

### Run on development

    ./gradlew bootRun
    
### Package for production

    ./gradlew bootRepackage

    java -jar build/libs/farm-service-0.0.1-SNAPSHOT.war --spring.profiles.active=prod

