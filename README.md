## Team Assessment API

This Catalyst DevLabs project is the API layer for the Aperture Team Assessment application. It receives AJAX calls from the Angular front end project and
communicates to the data layer via an HTTP Client.  

### Security
Spring security is enabled here, and handles authentication with CAMP's OAUTH2 server.
See Java Layer documents in Confluence for instructions on enabling and disabling security for development.

### Logging
Logging is implemented with log4j2.  See logging/log4j2.xml for properties.

### Data Import from Employee Service
A utility to retrieve User data from Replicon and parse it into usable data exists and will run automatically when starting the project.  See the import property in the application.yml in order to turn this off.

## Build Steps

### Automated Build

This repository is configured to automatically build on check-in.  
See .gitlab-ci.yml file for automated build steps.

### Local Build (runs on port 8090)

$ mvn clean package docker:build  
$ docker push catalystdevlabs/team-assessment-api [Optional]





