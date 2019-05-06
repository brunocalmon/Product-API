# Product-API
Rest Webservice API to Product's management

## Usage:
### This project is powered by maven.

#### To run the project you must have maven installed on your machine:

After download the project follow the commands below to set the project:
 - Fisrt build mongo docker image 
	`docker build docker/`
 - Start de image builded
	`docker run -d -p 27017:27017 mongo:4.1`
 - To install all required dependencies:
	`mvn clean install`
 - To run tests
	`mvn test`

# Swagger DOC
## You can find the API Rest documentation under this path '/swagger-ui.html' or '/v2/api-docs'

