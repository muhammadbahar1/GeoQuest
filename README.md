# GeoQuest

## INSTRUCTIONS HOW TO BUILD APP

1.Clone the Repository

2.Open in Android Studio
3.Open Android Studio
4.Click File → Open
5.Select the GeoQuest folder
6.Wait for Gradle to sync automatically

7.Run the App
8.Connect an Android device or start an emulator
Click the green Run button
Allow location permissions when prompted



## Description
GeoQuest is an android app to facilitate users to discover and log virtual caches tied to real-world GPS locations. Users must physically travel to locations and use mobile sensors and application features to unlock and record finds.


## UI Theme, Design System(Colors, Themes and Typography)

Figma High Fidelity link: https://www.figma.com/design/YFxsUfvjkq2P6BsHI1dq8J/GeoQuest?node-id=1-2&t=e1mu5HOpQoXMxQx9-1
Figma Prototype walkthrough:https://www.figma.com/proto/YFxsUfvjkq2P6BsHI1dq8J/GeoQuest?node-id=11-5&t=e1mu5HOpQoXMxQx9-1&starting-point-node-id=11%3A5

### Color Palette
https://coolors.co/dad7cd-a3b18a-588157-3a5a40-344e41

Dust Grey:#DAD7CD
Dry Sage:#A3B18A
Fern:#588157
Hunter Green:#3A5A40
Pine Teal:#344E41



## Tech Stack, System Architecture, Archietectural Pattern

### Tech Stack
Frontend:
- Native Android Jetpack compose(Kotlin)

Backend:
- FastAPI 0.135.3

Database
- PostgreSQL 18.3

Infrastructure
- AWS EC2, RDS PostgreSQL

API endpoint:
ec2-13-134-244-170.eu-west-2.compute.amazonaws.com

DB Endpoint:
geoquestdb.c9ws8w4gcpk4.eu-west-2.rds.amazonaws.com

### System Architecture

Mobile App -> REST API -> Service Layer -> Data Layer -> DB

### Archietectural Pattern

Mobile App:
- MVVM

Full Stack Application:
- MVC

## API

to access api:

ec2-13-134-244-170.eu-west-2.compute.amazonaws.com

api docs: 

ec2-13-134-244-170.eu-west-2.compute.amazonaws.com/docs


## Authors
- Muhammad Yusuf Bahar (K2323158) (M.Bahar@kingston.ac.uk)
- Sif El Din Deabes (K2373433) (K2373433@kingston.ac.uk)
