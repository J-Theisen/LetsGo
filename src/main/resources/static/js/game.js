
function loadUp() {
    paintTiles();
    loadCharactersOnBoard();
    checkMonies();
};


function paintTiles() {

    var pageUrl = window.location.href;
    var splitUrl = pageUrl.split("/");
    var gameIdSplit = splitUrl[5];
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/getTiles/' + gameIdSplit,
        success: function (boardTileArray) {

            $.each(boardTileArray, function (index, boardTile) {
                if (boardTile.tileType === "GREEN") {
                    $('#t' + boardTile.boardTileId).css('background-color', 'greenyellow');
                } else if (boardTile.tileType === "BLUE") {
                    $('#t' + boardTile.boardTileId).css('background-color', 'blue');
                } else if (boardTile.tileType === "RED") {
                    $('#t' + boardTile.boardTileId).css('background-color', 'red');
                }
            });
        },
        error: function () {
            alert("FAILURE");
        }
    });
};
function getGameIdFromUrl() {
    var pageUrl = window.location.href;
    var splitUrl = pageUrl.split("/");
    var gameIdSplit = splitUrl[5];
    return gameIdSplit;
}
function loadCharactersOnBoard() {
    var gameIdSplit = getGameIdFromUrl();
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/game/players/' + gameIdSplit,
        success: function (playerArray) {

            var div1 = "";
            var div2 = "";
            var div3 = "";
            var div4 = "";

            var p1Position;
            var p2Position;
            var p3Position;
            var p4Position;

            var p1Turn;
            var p2Turn;
            var p3Turn;
            var p4Turn;

            $.each(playerArray, function (index, player) {
                var playerTurn = player.playerTurn;
                var playerImage = player.imageUrl
                if (playerTurn === 1) {
                    p1Position = player.currentTile;
                    p1Turn = player.playerTurn;
                    div1 = '<div class="imgClass" id="player' + playerTurn + '" ><h6>' + player.playerName + '</h6>  <img id="player' + playerTurn + 'Image" src="' + playerImage + '"> </div>';
                } else if (playerTurn === 2) {
                    p2Position = player.currentTile;
                    p2Turn = player.playerTurn;
                    div2 = '<div class="imgClass" id="player' + playerTurn + '" ><h6>' + player.playerName + '</h6>  <img id="player' + playerTurn + 'Image" src="' + playerImage + '"> </div>';
                } else if (playerTurn === 3) {
                    p3Position = player.currentTile;
                    p3Turn = player.playerTurn;
                    div3 = '<div class="imgClass" id="player' + playerTurn + '" ><h6>' + player.playerName + '</h6>  <img id="player' + playerTurn + 'Image" src="' + playerImage + '"> </div>';
                } else if (playerTurn === 4) {
                    p4Position = player.currentTile;
                    p4Turn = player.playerTurn;
                    div4 = '<div class="imgClass" id="player' + playerTurn + '" ><h6>' + player.playerName + '</h6>  <img id="player' + playerTurn + 'Image" src="' + playerImage + '"> </div>';
                }
            });

            $('#s' + p4Position + p4Turn).append(div4);
            $('#s' + p3Position + p3Turn).append(div3);
            $('#s' + p2Position + p2Turn).append(div2);
            $('#s' + p1Position + p1Turn).append(div1);
        },
        error: function () {
            alert("FAILURE");
        }
    });
};

function highlightCurrentPlayer() {
    $('tr').css("background-color", "white");
    var playerTurn = $('#playerTurn').val() * 1;
    var p = $('h4[data-playerTurn=' + playerTurn + ']');
    p.parent().parent().css("background-color", "rgb(64, 255, 47)");

}

highlightCurrentPlayer();

$('#rollButton').on('click', function () {

    //Grabbing whos turn it is and how many players there are for the game.
    var playerTurn = $('#playerTurn').val() * 1;
    var numPlayers = $('#numPlayers').val() * 1;
    var gameIdSplit = getGameIdFromUrl();
    var p = $('h4[data-playerTurn=' + playerTurn + ']');
    var playerId = p.data('playerid') * 1;

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/get-game-player/' + gameIdSplit + '/' + playerTurn,
        success: function (player) {
            //Where the player is at before the roll.
            var playerPosition = player.currentTile;
            var playerSpacesMoved = player.spacesMoved;
            var pCurrency = player.playerCurrency;
            var pTurn = player.playerTurn;

            //Dice Roll
            var max = 4;
            var min = 1;
            rollNumber = Math.floor(Math.random() * (+max - +min)) + +min;
            $('#rollNumber').text(rollNumber);

            //var playerPositionStart = playerPosition;
            playerPosition = playerPosition + rollNumber;
            playerSpacesMoved += rollNumber;

            if (playerPosition > 12) {
                playerPosition = playerPosition - 12;
            }
            //Gets the tile color
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/api/getTile/' + gameIdSplit + '/' + playerPosition,
                success: function (tile) {

                    var tType = tile.tileType;

                    if (tile.tileType === 'BLUE') {
                        pCurrency += 2;
                        $.ajax({
                            type: "PUT",
                            url: "http://localhost:8080/api/game-player",
                            data: JSON.stringify({
                                id: playerId,
                                currentTile: playerPosition,
                                spacesMoved: playerSpacesMoved,
                                playerCurrency: pCurrency
                            }),
                            headers: {
                                "Accept": "application/json",
                                "Content-Type": "application/json"
                            },
                            success: function (gamePlayer) {
                                $('#playerMoney' + pTurn).text('$'+pCurrency);
                            },
                            error: function () {
                                alert("FAILURE PUT GAME PLAYER IN DB!");
                            }
                        });
                    } else {
                        if (pCurrency - 1 >= 0) {
                            pCurrency += -1;
                        }

                        $.ajax({
                            type: "PUT",
                            url: "http://localhost:8080/api/game-player",
                            data: JSON.stringify({
                                id: playerId,
                                currentTile: playerPosition,
                                spacesMoved: playerSpacesMoved,
                                playerCurrency: pCurrency
                            }),
                            headers: {
                                "Accept": "application/json",
                                "Content-Type": "application/json"
                            },
                            success: function (gamePlayer) {
                                $('#playerMoney' + pTurn).text('$'+pCurrency);
                            },
                            error: function () {
                                alert("FAILURE PUT GAME PLAYER IN DB!");
                            }
                        });
                    }
                },
                error: function () {
                    alert("FAILURE GET TILE");
                }
            });

            $('#s' + playerPosition + player.playerTurn).append($('#player' + player.playerTurn));
            //playerAroundBoard
            $('#playerAroundBoard'+player.playerTurn).text(Math.floor(playerSpacesMoved/12));

            if (playerSpacesMoved >= 36) {
                $('#buyingRow').hide();
                $('#rollRow').hide();
                $('#winnerName').text(player.playerName + " wins! ");
                $('#gamePlayerTable').hide();
                $('#winnerDiv').css("background-color", "rgb(64, 255, 47)");
                $("#").appendTo($('#'));
                $('#winnerDiv').show();

                $.ajax({
                    type: "PUT",
                    url: "http://localhost:8080/gameover/api/" + gameIdSplit,
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    success: function (gamePlayer) {
                    },
                    error: function () {
                        alert("FAILURE on GAME OVER");
                    }
                });

            } else {
                //This is where youd stop going negative

                //Updates the players position and total moves.
                $.ajax({
                    type: "PUT",
                    url: "http://localhost:8080/api/game-player",
                    data: JSON.stringify({
                        id: playerId,
                        currentTile: playerPosition,
                        spacesMoved: playerSpacesMoved,
                    }),
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    success: function (gamePlayer) {
                    },
                    error: function () {
                        alert("FAILURE PUT GAME PLAYER IN DB!");
                    }
                });
            }

        },
        error: function () {
            alert("FAILURE GET GAME PLAYER");
        }
    });

    //nextPlayer
    playerTurn++;
    if (playerTurn > numPlayers) {
        playerTurn = 1;
    }
    //Updates whos turn it is in game.
    $.ajax({
        type: "PUT",
        url: "http://localhost:8080/api/game",
        data: JSON.stringify({
            gameId: gameIdSplit,
            playerTurn: playerTurn
        }),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: function (game) {
            $('#playerTurn').val(game.playerTurn);
            highlightCurrentPlayer();
        },
        error: function () {
            alert("FAILURE UPDATE GAME WHOS UP!");
        }
    });
    checkMonies();
});

function checkMonies() {
    var playerTurn = $('#playerTurn').val() * 1;
    var numPlayers = $('#numPlayers').val() * 1;
    playerTurn++;
    if (playerTurn > numPlayers) {
        playerTurn = 1;
    }
    var gameIdSplit = getGameIdFromUrl();
    var p = $('h4[data-playerTurn=' + playerTurn + ']');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/get-game-player/' + gameIdSplit + '/' + playerTurn,
        success: function (player) {
            if (player.playerCurrency >= 5) {
                $('#buySpaceDiv').css("visibility", "visible");
            } else {
                $('#buySpaceDiv').css("visibility", "hidden");
            }

            // if(player.playerCurrency>=10){
            //      $('#switchPlayerDiv').css("visibility", "visible");
            // } else {
            //      $('#switchPlayerDiv').css("visibility", "hidden");
            // }
        },
        error: function () {
            alert("FAILURE GET GAME PLAYER");
        }
    });
}

function bing() {

}

$('#buySpaceButton').on('click', function () {
 
    //Grabbing whos turn it is and how many players there are for the game.
    var playerTurn = $('#playerTurn').val() * 1;
    var numPlayers = $('#numPlayers').val() * 1;
    var gameIdSplit = getGameIdFromUrl();
    var p = $('h4[data-playerTurn=' + playerTurn + ']');
    var playerId = p.data('playerid') * 1;

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/get-game-player/' + gameIdSplit + '/' + playerTurn,
        success: function (player) {
            //Where the player is at before the roll.
            var playerPosition = player.currentTile;
            var playerSpacesMoved = player.spacesMoved;
            var pCurrency = player.playerCurrency-5;
            var pTurn = player.playerTurn;

            //Dice Roll
            var max = 4;
            var min = 1;
            rollNumber = Math.floor(Math.random() * (+max - +min)) + +min;
            rollNumber= rollNumber+2;
            $('#rollNumber').text(rollNumber);

            //var playerPositionStart = playerPosition;
            playerPosition = playerPosition + rollNumber;
            playerSpacesMoved += rollNumber;

            if (playerPosition > 12) {
                playerPosition = playerPosition - 12;
            }
            //Gets the tile color
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/api/getTile/' + gameIdSplit + '/' + playerPosition,
                success: function (tile) {

                    var tType = tile.tileType;

                    if (tile.tileType === 'BLUE') {
                        pCurrency += 2;
                        $.ajax({
                            type: "PUT",
                            url: "http://localhost:8080/api/game-player",
                            data: JSON.stringify({
                                id: playerId,
                                currentTile: playerPosition,
                                spacesMoved: playerSpacesMoved,
                                playerCurrency: pCurrency
                            }),
                            headers: {
                                "Accept": "application/json",
                                "Content-Type": "application/json"
                            },
                            success: function (gamePlayer) {
                                $('#playerMoney' + pTurn).text('$'+pCurrency);
                            },
                            error: function () {
                                alert("FAILURE PUT GAME PLAYER IN DB!");
                            }
                        });
                    } else {
                        if (pCurrency - 1 >= 0) {
                            pCurrency += -1;
                        }

                        $.ajax({
                            type: "PUT",
                            url: "http://localhost:8080/api/game-player",
                            data: JSON.stringify({
                                id: playerId,
                                currentTile: playerPosition,
                                spacesMoved: playerSpacesMoved,
                                playerCurrency: pCurrency
                            }),
                            headers: {
                                "Accept": "application/json",
                                "Content-Type": "application/json"
                            },
                            success: function (gamePlayer) {
                                $('#playerMoney' + pTurn).text('$'+pCurrency);
                            },
                            error: function () {
                                alert("FAILURE PUT GAME PLAYER IN DB!");
                            }
                        });
                    }
                },
                error: function () {
                    alert("FAILURE GET TILE");
                }
            });

            $('#s' + playerPosition + player.playerTurn).append($('#player' + player.playerTurn));
            $('#playerAroundBoard'+player.playerTurn).text(Math.floor(playerSpacesMoved/12));

            if (playerSpacesMoved >= 36) {
                $('#buyingRow').hide();
                $('#rollRow').hide();
                $('#winnerName').text(player.playerName + " wins! ");
                $('#gamePlayerTable').hide();
                $('#winnerDiv').css("background-color", "rgb(64, 255, 47)");
                $("#").appendTo($('#'));
                $('#winnerDiv').show();

                $.ajax({
                    type: "PUT",
                    url: "http://localhost:8080/gameover/api/" + gameIdSplit,
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    success: function (gamePlayer) {
                    },
                    error: function () {
                        alert("FAILURE on GAME OVER");
                    }
                });

            } else {
                $.ajax({
                    type: "PUT",
                    url: "http://localhost:8080/api/game-player",
                    data: JSON.stringify({
                        id: playerId,
                        currentTile: playerPosition,
                        spacesMoved: playerSpacesMoved,
                    }),
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    success: function (gamePlayer) {
                    },
                    error: function () {
                        alert("FAILURE PUT GAME PLAYER IN DB!");
                    }
                });
            }
        },
        error: function () {
            alert("FAILURE GET GAME PLAYER");
        }
    });

    //nextPlayer
    playerTurn++;
    if (playerTurn > numPlayers) {
        playerTurn = 1;
    }
    //Updates whos turn it is in game.
    $.ajax({
        type: "PUT",
        url: "http://localhost:8080/api/game",
        data: JSON.stringify({
            gameId: gameIdSplit,
            playerTurn: playerTurn
        }),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: function (game) {
            $('#playerTurn').val(game.playerTurn);
            highlightCurrentPlayer();
        },
        error: function () {
            alert("FAILURE UPDATE GAME WHOS UP!");
        }
    });
    checkMonies();
});

