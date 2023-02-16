# Simple Economy Game

A multi-module web application is a simple economic game that allows players to gather resources, build structures, and recruit armies. The application consists of a business layer implemented using the **Spring Boot** framework, a view layer implemented using the **Angular 12** framework, and a data layer created with the **mySQL** database server. All modules can be run using the `docker-compose.yml`

## Business layer ##
The business layer provides the following operations via a REST API:

*Retrieve a list of built buildings (for a logged-in player)
*Retrieve a list of available resources (for a logged-in player)
*Retrieve a list of army units (for a logged-in player)
*Build a building (for a logged-in player)
*Recruit a specified number of chosen units (for a logged-in player)
*Release a specified number of chosen units (for a logged-in player)
*Retrieve a list of players (for admin only)
*Create a new user (registration for an unauthenticated user)
*Delete a user (for admin or logged-in user)

The implementation of these operations includes verification of their feasibility, such as whether the player can afford a specific unit or has the required building. The resource and unit states are updated periodically (`@Scheduled` mechanism), and in the event that the player cannot afford to pay for the units, desertion occurs. Only those units that have not been paid for will desert.

The code has been tested with unit tests.

## View layer ##

The view layer provides the following views:

Registration:

![Alt text](/img/localhost_8081.png?raw=true)

Buildings management:

![Alt text](/img/localhost_8081_buildings.png?raw=true)

Units management:

![Alt text](/img/localhost_8081_units.png?raw=true)

## Instructions

### Docker

Make sure that you have Docker and Docker Compose installed on your system. Navigate to the directory where project `docker-compose.yml` file is located. To build and start project use `docker-compose up --build -d`. If you want to stop the containers, use the `docker-compose down`.