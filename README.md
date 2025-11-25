# Study App

## About
Java spring boot app that allows users to create 
study sessions, verify user registration by email, 
upload study flashcards in bulk, create exams, track user focus, and potentially
use AI/ML services to improve knowledge retention.

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
**Redis**: Cache data that rarely changes, improve throughput.
<br/>
**AWS Kinesis Data Stream**: Ingest user clicks, interactions with the app.
<br/>
**Postgresql**: persist user-related data.
<br/>
**DynamoDB**: Persist users clicks and interactions used for ranking purposes. Reduce write/read load
on main Postgresql database.
<br/>
**Athena**: Perform sql queries on DynamoDB.
<br/>
**Docker**: Containerize app. Used to create test db in dev env.
<br/>

## Tasks
- [x] Register users. 
- [x] Verify user registration with email token.
- [x] only activate account once user verify their account.
- [x] Refactor code. create handler for exceptions. reference only services inside controllers. 
- [x] Implement password reset logic.
- [ ] Implement logic that allows user to set a valid email for registration if they accidentally enter an invalid one.