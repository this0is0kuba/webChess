# WebChess
Web Application for chess players

## Description
Application requires create an account to access the content of the web application. After log in user can create a room or join another room to play chess. After each game user's staistics are updated. 
Player can check your points an position in user ranking. Application analize the state of game and sends only possible moves to frontend so user can easily search allowed moves. Backend render pages and after get a request sends them to user.
Backend is created in Spring Boot and connected with MySQL database from which it obtains data. 

## Room Parameters
User can select the colour of pieces and duration of chess game when he creates the room. Then when the second user join his room both users should click "start" button to begin the game.
After this room starts the clock countdown.

## Sample images from Web Application
|                                           |                                            |
| ----------------------------------------- |--------------------------------------------|
|<img src="images/main.png"> | <img src="images/profile.png"> |
|<img src="images/ranking.png"> | <img src="images/lobby.png"> |

### Room images
|                                           |                                            |
| ----------------------------------------- |--------------------------------------------|
|<img src="images/room.png"> | <img src="images/room-3.png"> |

### Possible moves after click queen
|                                           |
| ----------------------------------------- |
|<img src="images/room-2.png"> |
