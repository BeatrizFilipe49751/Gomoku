# Gomoku API Documentation

This API provides endpoints for interacting with a Gomoku game. Gomoku is a two-player strategy board game where the objective is to connect five of your pieces horizontally, vertically, or diagonally on a grid.

------------------------------------------------------------------------------------------

# Base URL:
All endpoints are relative to the base URL: <b>/api</b>

------------------------------------------------------------------------------------------

## Authentication

All endpoints under <b>`/users/lobby/**`</b>, <b>`/users/game/**`</b>, and <b>`/users/logout`</b> require authentication. Include the following in requests:

```typescript
headers: {
    'Authorization': 'Bearer <your_auth_token>'
}
```
------------------------------------------------------------------------------------------

## User Endpoints [/users]
<details>
<summary><code>GET</code> <code><b>/users/{userId}</b></code> <code>(Retrieves information about a specific user)</code></summary>

##### Parameters

> | name      |  type     | description                                                           |
> |-----------|-----------|-----------------------------------------------------------------------|
> | userId    |  required | ID of the user                                                        |  

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | `{"userId":"1","username":"user1","email":"user1@gmail.com"}`       |
> | `404`         | `application/json`                | `{"message":"User not found"}`                                      |

</details>

<details>
<summary><code>POST</code> <code><b>/users</b></code> <code>(Creates a new user)</code></summary>

##### Request Body

The request body should be a JSON object with the following properties:

> | name      |  type     | description                                                           |
> |-----------|-----------|-----------------------------------------------------------------------|
> | username  |  required | The username for the new user                                         |
> | email     |  required | The email address for the new user                                    |
> | password  |  required | The password for the new user. It should contain an uppercase letter, a number, and a special character. |

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `201`         | `application/json`                | `{"userId":"1","username":"user1","email":"user1@gmail.com"}`       |
> | `400`         | `application/json`                | `{"message":"Username, email or password missing"}`                 |
> | `400`         | `application/json`                | `{"message":"Password needs an uppercase letter, a number and a special character"}`                 |
> | `400`         | `application/json`                | `{"message":"Email doesnt have @"}`                                 |

</details>

<details>
<summary><code>POST</code> <code><b>/users/login</b></code> <code>(Logs in a user)</code></summary>

##### Request Body

The request body should be a JSON object with the following properties:

> | name      |  type     | description                                                           |
> |-----------|-----------|-----------------------------------------------------------------------|
> | email     |  required | The email address associated with the user account                    |
> | password  |  required | The password for the user account                                     |

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | `{"user":{"userId":1,"username":"user1","email":"user1@gmail.com","passwordValidation":{"..."}},"token":"yourAccessToken"}`       |
> | `400`         | `application/json`                | `{"message":"Username, email or password missing"}`                 |
> | `400`         | `application/json`                | `{"message":"No user with the given credentials"}`                  |
> | `404`         | `application/json`                | `{"message":"No user with the given credentials"}`                  |

</details>

<details>
<summary><code>POST</code> <code><b>/users/logout</b></code> <code>(Logs out the authenticated user)</code></summary>

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | `{"userId":"1","username":"user1","email":"user1@gmail.com"}`       |
> | `401`         | `application/json`                | `{"message":"Unauthorized Access"}`                                 |

</details>

<details>
<summary><code>GET</code> <code><b>/users/leaderboard</b></code> <code>(Retrieves the leaderboard)</code></summary>

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | `[{"userId": 1, "username": "user1", "points": 100}, {"userId": 2, "username": "user2", "points": 90}, ...]`       |

</details>

## Lobby Endpoints [/users/lobby]

<details>
<summary><code>POST</code> <code><b>/users/lobby</b></code> <code>(Creates a game lobby for other user to join)</code></summary>

##### Request Body

The request body should be a JSON object with the following properties:

> | name       |  type     | description                                        |
> |------------|-----------|----------------------------------------------------|
> | name       |  required | The name of the lobby.                              |
> | opening    |  required | The opening of the lobby (Freestyle, Pro, Long_Pro, Swap) |
> | variant    |  required | The variant of the lobby (Freestyle, Swap)         |
> | size       |  required | The board size of the lobby (15 or 19)             |

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `201`         | `application/json`                | `{"lobbyId": 1, "p1": "user1", "name": "Lobby1"}`                   |
> | `400`         | `application/json`                | `{"message":"Missing parameters for lobby"}`                        |
> | `400`         | `application/json`                | `{"message":"Those rules are invalid"}`                             |
> | `400`         | `application/json`                | `{"message":"Those rules are invalid"}`                             |
> | `401`         | `application/json`                | `{"message":"Unauthorized Access"}`                                 |
> | `404`         | `application/json`                | `{"message":"User is already in lobby"}`                            |

</details>

<details>
<summary><code>GET</code> <code><b>/users/lobby/{lobbyId}</b></code> <code>(Retrieves a specific lobby)</code></summary>

##### Parameters

> | name      |  type     | description                                                           |
> |-----------|-----------|-----------------------------------------------------------------------|
> | lobbyId   |  required | ID of the lobby                                                       |  

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | `{"lobbyId": 1, "p1": "user1", "name": "Lobby1"}`                   |
> | `401`         | `application/json`                | `{"message":"Unauthorized Access"}`                                 |
> | `404`         | `application/json`                | `{"message":"Lobby not Found"}`                                     |

</details>

<details>
<summary><code>GET</code> <code><b>/users/lobby/{lobbyId}/full</b></code> <code>(Checks if a lobby is full)</code></summary>

##### Parameters

> | name      |  type     | description                                                           |
> |-----------|-----------|-----------------------------------------------------------------------|
> | lobbyId   |  required | ID of the lobby                                                       |  

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | `{"message": "Lobby has one player", "gameId": "Game has not been created"}` |
> | `401`         | `application/json`                | `{"message":"Unauthorized Access"}`                                 |
> | `404`         | `application/json`                | `{"message":"Lobby not Found"}`                                     |

</details>

<details>
<summary><code>GET</code> <code><b>/users/lobbies</b></code> <code>(Retrieves the list of available lobbies)</code></summary>

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | `[{"lobbyId": 1, "name": "Example Lobby 1", "opening": 1, "variant": 2, "boardSize": 15, "p1": 1, "p2": null}, {"lobbyId": 2, "name": "Example Lobby 2", "opening": 3, "variant": 1, "boardSize": 19, "p1": 5, "p2": null}, ...]`       |
> | `401`         | `application/json`                | `{"message":"Unauthorized Access"}`                                 |

</details>

<details>
<summary><code>GET</code> <code><b>/users/lobby/get</b></code> <code>(If exists, retrieves a lobby where the user is present)</code></summary>

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | `[{"lobbyId": 1, "name": "Example Lobby 1", "opening": 1, "variant": 2, "boardSize": 15, "p1": 1, "p2": null}, {"lobbyId": 2, "name": "Example Lobby 2", "opening": 3, "variant": 1, "boardSize": 19, "p1": 5, "p2": null}, ...]`       |
> | `401`         | `application/json`                | `{"message":"Unauthorized Access"}`                                 |

</details>

<details>
<summary><code>PUT</code> <code><b>/users/lobby/{lobbyId}</b></code> <code>(Allows a user to join an existing lobby)</code></summary>

##### Parameters

> | name      |  type     | description                                                           |
> |-----------|-----------|-----------------------------------------------------------------------|
> | lobbyId   |  required | ID of the lobby                                                       |  

##### Responses

> | http code     | content-type                      | response                                                                                                                                                                        |
> |---------------|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
> | `201`         | `application/json`                | `{"gameId": 1, "name": "Example Game", "playerWhite": 1, "playerBlack": 3, "opening": 3, "variant": 1, "boardSize": 15, "currentTurn": "white", "pieces": "...", "state": "A"}` |
> | `400`         | `application/json`                | `{"message":"User is already in lobby"}`                                                                                                                                        |
> | `400`         | `application/json`                | `{"message":"Game creation error"}`                                                                                                                                             |
> | `401`         | `application/json`                | `{"message":"Unauthorized Access"}`                                                                                                                                             |
> | `404`         | `application/json`                | `{"message":"User not found"}`                                                                                                                                                  |
> | `404`         | `application/json`                | `{"message":"Lobby not Found"}`                                                                                                                                                 |

</details>

<details>
<summary><code>DELETE</code> <code><b>/users/lobby/{lobbyId}</b></code> <code>(Allows a user to quit the lobby)</code></summary>

##### Parameters

> | name      |  type     | description                                                           |
> |-----------|-----------|-----------------------------------------------------------------------|
> | lobbyId   |  required | ID of the lobby                                                       |  

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                |                                                                     |
> | `401`         | `application/json`                | `{"message":"Unauthorized Access"}`                                 |
> | `404`         | `application/json`                | `{"message":"Lobby not Found"}`                                     |
> | `404`         | `application/json`                | `{"message":"User not found"}`                                      |

</details>

## Game Endpoints [/users/game]

<details>
<summary><code>PUT</code> <code><b>/users/game/play/{gameId}</b></code> <code>(Play a Move in a Game)</code></summary>

##### Parameters

> | name      |  type     | description                                                           |
> |-----------|-----------|-----------------------------------------------------------------------|
> | gameId    |  required | ID of the game                                                        |  

##### Request Body

The request body should be a JSON object with the following properties:

> | name       |  type     | description                                        |
> |------------|-----------|----------------------------------------------------|
> | row        |  required | The row where the move is played.                  |
> | col        |  required | The column where the move is played.               |

##### Responses

> | http code     | content-type                      | response                                                                                                                                                                        |
> |---------------|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
> | `200`         | `application/json`                | `{"gameId": 1, "name": "Example Game", "playerWhite": 1, "playerBlack": 3, "opening": 3, "variant": 1, "boardSize": 15, "currentTurn": "white", "pieces": "...", "state": "A"}` |
> | `400`         | `application/json`                | `{"message":"Game failed to update"}`                                                                                                                                           |
> | `400`         | `application/json`                | `{"message":"Not your turn"}`                                                                                                                                                   |
> | `400`         | `application/json`                | `{"message":"Game has finished"}`                                                                                                                                               |
> | `400`         | `application/json`                | `{"message":"Game has finished with a draw"}`                                                                                                                                   |
> | `400`         | `application/json`                | `{"message":"Piece already in that position"}`                                                                                                                                  |
> | `401`         | `application/json`                | `{"message":"Unauthorized Access"}`                                                                                                                                             |
> | `404`         | `application/json`                | `{"message":"Game Not Found"}`                                                                                                                                                  |

</details>

<details>
<summary><code>GET</code> <code><b>/users/game/{gameId}</b></code> <code>(Retrieves information about a specific game)</code></summary>

##### Parameters

> | name      |  type     | description                                                           |
> |-----------|-----------|-----------------------------------------------------------------------|
> | gameId    |  required | ID of the game                                                        |  

##### Responses

> | http code     | content-type                      | response                                                                                                                                                                        |
> |---------------|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
> | `200`         | `application/json`                | `{"gameId": 1, "name": "Example Game", "playerWhite": 1, "playerBlack": 3, "opening": 3, "variant": 1, "boardSize": 15, "currentTurn": "white", "pieces": "...", "state": "A"}` |
> | `401`         | `application/json`                | `{"message":"Unauthorized Access"}`                                                                                                                                             |
> | `404`         | `application/json`                | `{"message":"Game Not Found"}`                                                                                                                                                  |

</details>

<details>
<summary><code>PUT</code> <code><b>/users/game/{gameId}/quit</b></code> <code>(Sets the game's state to cancelled, rendering it unplayable)</code></summary>

##### Parameters

> | name      |  type     | description                                                           |
> |-----------|-----------|-----------------------------------------------------------------------|
> | gameId    |  required | ID of the game                                                        |  

##### Responses

> | http code     | content-type                      | response                                                                                                                                                                        |
> |---------------|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
> | `200`         | `application/json`                | `{"gameId": 1, "name": "Example Game", "playerWhite": 1, "playerBlack": 3, "opening": 3, "variant": 1, "boardSize": 15, "currentTurn": "white", "pieces": "...", "state": "C"}` |
> | `401`         | `application/json`                | `{"message":"Unauthorized Access"}`                                                                                                                                             |
> | `404`         | `application/json`                | `{"message":"Game Not Found"}`                                                                                                                                                  |

</details>

<details>
<summary><code>GET</code> <code><b>/users/game</b></code> <code>(If exists, retrieves an active game in which the user is present)</code></summary>

##### Parameters

>There are no parameters, the request is done through authentication

##### Responses

> | http code     | content-type                      | response                            |
> |---------------|-----------------------------------|-------------------------------------|
> | `200`         | `application/json`                | `{gameId: 1}`                       |
> | `401`         | `application/json`                | `{"message":"Unauthorized Access"}` |
> | `404`         | `application/json`                | `{"message":"Game Not Found"}`      |

</details>
