# Food Delivery backend

## Overview

Backend of food delivery application consists of a resource server and an authorization server.\
Authorization is done by [auth0](https://auth0.com) platform. When a client wants to access a protected resource hosted on resource server, it first needs to obtain a JWT token from an auth0 authorization server, with the approval of the resource owner. This token is then attached to client requests to a resource server, in order to confirm client's priviliges when accessing protected resources.\
This repo contains source code of a resource server, and it is hosted on AWS platform. Communication with it is done through REST API found at https://apifooddelivery.tk.

## Usage

### User

#### ADD USER
Add new user to the resource server.\
`POST` /users\
Body: `{id: user id, name: user's name, email: user's email, address: user's address}`\
Authorization: `admin`

#### GET USERS
Returns all registered users.\
`GET` /users\
Authorization: `admin`

#### GET USER BY ID
Returns information of the user with the matching id.\
`GET` /users/:id\
Authorization: `admin`, `user with matching id`

#### UPDATE USER
Update information of a user with the matching id.\
`PUT` /users/:id\
Body: `{id: new user id, name: new user's name, email: new user's email, address: new user's address}`\
Authorization: `admin`

#### DELETE USER
Delete user with the matching id.\
`DELETE` /users/:id\
Authorization: `admin`

### City

#### ADD CITY
Add new city to the resource server.\
`POST` /cities\
Body: `{name: name of the city}`\
Authorization: `admin`

#### GET CITIES
Returns all cities.\
`GET` /cities\
Authorization: `none required`

#### DELETE CITY
Remove the city with the matching id.\
`DELETE` /cities/:id\
Authorization: `admin`

### Restaurant

#### ADD RESTAURANT TO CITY
Add new restaurant in the city with the matching id.\
`POST` /cities/:id/restaurants\
Body: `{name: restaurant name, photoPath: restaurant's thumbnail}`\
Authorization: `admin`

#### GET RESTAURANTS
Returns all restaurants.\
`GET` /restaurants\
Authorization: `none required`

#### GET RESTAURANTS IN CITY
Returns all restaurants in the city with the matching id.\
`GET` /cities/:id/restaurants\
Authorization: `none required`

#### UPDATE RESTAURANT
Update information of the restaurant with the matching id.\
`PUT` /restaurants/:id\
Body: `{name: new restaurant name, photoPath: new restaurant's thumbnail}`\
Authorization: `admin`

#### DELETE RESTAURANT
Remove the restaurant with the matching id.\
`DELETE` /restaurants/:id\
Authorization: `admin`

### Meal

#### ADD MEAL TO RESTAURANT
Add new meal to the restaurant with the matching id.\
`POST` /restaurants/:id/meals\
Body: `{name: name of the meal, description: description of the meal, type: type of the meal (Main course, Dessert...), coverPhotoPath: path to the meal's photo, price: price of the meal}`\
Authorization: `admin`

#### GET MEALS
Returns all meals.\
`GET` /meals\
Authorization: `none required`

#### GET MEALS OF RESTAURANT
Returns all meals of the restaurant with the matching id.\
`GET` /restaurants/:id/meals\
Authorization: `none required`

#### GET MEAL BY ID
Returns information of the meal with the matching id.\
`GET` /meals/:id\
Authorization: `none required`

#### UPDATE MEAL
Update information of the meal with the matching id.\
`PUT` /meals/:id\
Body: `{name: new name of the meal, description: new description of the meal, type: new type of the meal (Main course, Dessert...), coverPhotoPath: new path to the meal's photo, price: new price of the meal}`\
Authorization: `admin`

#### DELETE MEAL
Remove the meal with the matching id.\
`DELETE` /meals/:id\
Authorization: `admin`

### Order

#### ADD ORDER
Adds order of the user that's making the request.\
`POST` /orders\
Body: `{:meal1_id: quantity of the first meal, :meal2_id: quantity of the second meal,...}`\
Authorization: `user`

#### GET ORDERS
Returns all orders.\
`GET` /orders\
Authorization: `admin`

#### GET ORDER BY ID
Returns information of the order with the matching id.\
`GET` /orders/:id\
Authorization: `admin`, `user that made the order`

#### GET ORDERS BY USER ID
Returns all orders of the user with the matching id.\
`GET` /users/:id/orders\
Authorization: `admin`, `user with the matching id`

#### DELETE ORDER
Remove the order with the matching id.\
`DELETE` /orders/:id\
Authorization: `admin`


## Deployment

### Packaging

In order to deploy application to production, we first need to package it as war. To do that, first install maven if you haven't already, by running the command:
```
sudo apt install maven
```
Now we can use maven to package an application. Open the terminal, navigate to the directory containing the source code and run:
```
mvn clean package
```
This generates production build of code in `/target` subdirectory, named `food_delivery_backend-0.0.1-SNAPSHOT.war`.

### Deployment to AWS EC2 instance

#### Connection

Given that we want to deploy our app to AWS EC2 instance, we first have to create an account. After that, head over to EC2 section, and create a new instance named `food delivery backend`. This creates a new Linux VM on amazon servers, and in order to run our app on it, we have to connect to it. So, in the EC2 dashboard, select our instance and click the connect button. In the next menu, select SSH tab and copy the command used for connection to the instance. You can connect from your own machine, or AWS's cloudshell. Open either one, and execute the copied command.

#### Running Tomcat server

Our application already has an embedded tomcat server, and all we need to do is run the `.war` file. First we upload the file to the VM instance, then install `openjdk-11-jdk` package on it and run `java -jar food_delivery_backend-0.0.1-SNAPSHOT.war` which spins up tomcat server, making our app available. However, we have a problem. We don't want to manually run `java` command each time our server crashes, or VM reboots. Luckily, we can easily automate the process, by running our server as a systemd service. Make a `/etc/systemd/system/java-server.service` file, and paste in the following:
```
[Unit]
Description=Java resource server

[Service]
WorkingDirectory=/home/ubuntu
ExecStart=java -jar food_delivery_backend-0.0.1-SNAPSHOT.war
User=root
Type=simple
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```
Now run the service with command `sudo systemctl start java-server`. We can verify that service is running with `systemctl status java-server`. The status should be `loaded`, colored green.

#### Creating a database

To persist all the data our app uses we have to create the database. So, install mySQL with the command `sudo apt install mysql`. Next, we have to create a user & database which our spring application will use. Open mysql as root, with `sudo mysql` and create new user & database by running:
```
CREATE USER 'springuser'@'localhost' IDENTIFIED BY '123';
CREATE DATABASE db_example;
GRANT ALL PRIVILEGES ON db_example.* TO 'springuser'@'localhost';
```

### Assigning domain

TODO:

### Setting up TLS certificate

We need to secure our site, and upgrade the connection to HTTPS. To that end, we must somehow obtain a digital certificate from a certificate authority. There are many CAs out there but I like `let's encrypt` because it's free and easy to use. We fetch the source code of Let’s Encrypt on our server first from their git repo:
```
git clone https://github.com/certbot/certbot 
cd certbot
```
Running the following
```
./certbot-auto certonly -a standalone \
     -d example.com -d www.apifooddelivery.tk
```
generates certificates and a private key for you in `/etc/letsencrypt/live/apifooddelivery.tk` directory. One important thing to note is that SpringBoot does not support PEM files generated by Let’s Encrypt, but it supports PKCS12 extension. Using OpenSSL, we convert our certificate and private key to PKCS12. So run the following:
```
openssl pkcs12 -export -in fullchain.pem \ 
                 -inkey privkey.pem \ 
                 -out keystore.p12 
                 -name tomcat \
                 -CAfile chain.pem \
                 -caname root
```
which generates `keystore.p12` file. Move that file to `/home/ubuntu/keystore/keystore.p12`, so it can be used by our app.\
This finishes the set-up and our backend server is ready to use.

### Renewing the certificate

By default `let's encrypt`'s certificates only last for 90 days, and we can't change that. So, we have to renew them every couple of months. Fortunately, it's really easy. Connect to your server again and run
```
sudo ./certbot-auto renew
```
which generates new certificate. In order for your app to use you have to again convert it to PKCS12, by following the steps in `Setting up TLS certificate`. Now, restart your server instance with `sudo systemctl restart java-server`.
