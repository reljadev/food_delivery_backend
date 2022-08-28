# food_delivery_backend

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
