# Study App

## About
Java spring boot backend for a game application where user can choose which game to play
from several different games, see scores, game history, game analyzer and more. 

## Technologies:
**Spring Security**: User registration, email confirmation, user login.
<br/>
**Postgresql**: persist user-related data such as profile, game history, game state as json.
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
- [x] Implement game history display.
