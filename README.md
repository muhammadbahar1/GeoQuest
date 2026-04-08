# GeoQuest

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

### Theme



### Typography

## Features

 


## Tech Stack, System Architecture, Archietectural Pattern

### Tech Stack
Frontend:
- Native Android Jetpack compose(Kotlin)
- Room DB(SQLite wrapper)

Backend:
- FastAPI 0.135.3

Database
- PostgreSQL 18.3

Infrastructure
- AWS EC2, S3, RDS PostgreSQL, Route 53

API endpoint:
ec2-13-134-244-170.eu-west-2.compute.amazonaws.com

DB Endpoint:
geoquestdb.c9ws8w4gcpk4.eu-west-2.rds.amazonaws.com
username: postgres
password: ab.w7AsjV3VY6Q-


### System Architecture

Mobile App -> REST API -> Service Layer -> Data Layer -> DB

### Archietectural Pattern

Mobile App:
- MVVM

Full Stack Application:
- MVC

## API

api docs: 

ec2-13-134-244-170.eu-west-2.compute.amazonaws.com/docs

## to login to ec2.micro:
go to directory and run you may need to copy .pem file to somewhere else first

ssh -i "GeoQuestVPS.pem" ubuntu@ec2-13-134-244-170.eu-west-2.compute.amazonaws.com


## login to db with ec2:
psql --host=geoquestdb.c9ws8w4gcpk4.eu-west-2.rds.amazonaws.com --port=5432 --username=postgres --password --dbname=postgres

db password: ab.w7AsjV3VY6Q-

## Authors
- Muhammad Yusuf Bahar (K2323158) (M.Bahar@kingston.ac.uk)
- Sif El Din Deabes (K2373433) (K2373433@kingston.ac.uk)