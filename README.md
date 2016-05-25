## Team Assessment API

This Catalyst DevLabs project is the API layer for the Aperture Team Assessment application.


## Build Steps

### Automated Build

This repository is configured to automatically build on check-in.  
See .gitlab-ci.yml file for automated build steps.

### Local Build

$ mvn clean package docker:build  
$ docker push catalystdevlabs/team-assessment-api [Optional]





