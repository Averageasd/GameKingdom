# Study App

## About
Java spring boot backend for a game application where user can choose which game to play
from several different games, see scores, game history, game analyzer and more. 

## Technologies:
**Spring Security**: User registration, email confirmation, user login.
<br/>
**RabbitMQ**: used as an message queue to decouple services, facilitate communication
between services, buffer/ingest messages to reduce traffic spike and execute background tasks.
Used with Spring Batch to execute data processing in background, send emails and maintain async task
table
<br/>
**Spring Batch**: Perform bach jobs, process large volume of data, insert data into database.
<br/>
**Postgresql**: persist user-related data such as profile, game history, game state as json.
<br/>
**Athena**: Perform sql queries on DynamoDB.
<br/>
**Docker**: Containerize app. Used to create test db container and other services such as message queue.
<br/>

## Tasks
- [x] Register users. 
- [x] Verify user registration with email token.
- [x] only activate account once user verify their account.
- [x] Refactor code. create handler for exceptions. reference only services inside controllers. 
- [x] Implement password reset logic for authenticated users.
- [x] Implement logic that allows authenticated users to reset email.