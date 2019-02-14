
function loadUp() {
    paintTiles();
    loadCharactersOnBoard();
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
                    div1 = '<div id="player' + playerTurn + '" ><p>' + player.playerName + '</p>  <img id="player' + playerTurn + 'Image" src="' + playerImage + '"> </div>';
                } else if (playerTurn === 2) {
                    p2Position = player.currentTile;
                    p2Turn = player.playerTurn;
                    div2 = '<div id="player' + playerTurn + '" ><p>' + player.playerName + '</p>  <img id="player' + playerTurn + 'Image" src="' + playerImage + '"> </div>';
                } else if (playerTurn === 3) {
                    p3Position = player.currentTile;
                    p3Turn = player.playerTurn;
                    div3 = '<div id="player' + playerTurn + '" ><p>' + player.playerName + '</p>  <img id="player' + playerTurn + 'Image" src="' + playerImage + '"> </div>';
                } else if (playerTurn === 4) {
                    p4Position = player.currentTile;
                    p4Turn = player.playerTurn;
                    div4 = '<div id="player' + playerTurn + '" ><p>' + player.playerName + '</p>  <img id="player' + playerTurn + 'Image" src="' + playerImage + '"> </div>';
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
    var p = $('p[data-playerTurn=' + playerTurn + ']');
    p.parent().parent().css("background-color", "red");
}

highlightCurrentPlayer();

$('#rollButton').on('click', function () {

    //Grabbing whos turn it is and how many players there are for the game.
    var playerTurn = $('#playerTurn').val() * 1;
    var numPlayers = $('#numPlayers').val() * 1;
    var gameIdSplit = getGameIdFromUrl();
    var p = $('p[data-playerTurn=' + playerTurn + ']');
    var playerId = p.data('playerid') * 1;

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/get-game-player/' + gameIdSplit + '/' + playerTurn,
        success: function (player) {
            //Where the player is at before the roll.
            var playerPosition = player.currentTile;
            var playerSpacesMoved = player.spacesMoved;

            //Dice Roll
            var min = 1;
            var max = 4;
            var rollNumber = Math.floor(Math.random() * (+max - +min)) + +min;
            $('#rollNumber').text(rollNumber);

            //var playerPositionStart = playerPosition;
            playerPosition = playerPosition + rollNumber;
            playerSpacesMoved += rollNumber;

            if (playerPosition > 12) {
                playerPosition = playerPosition - 12;
            }

            $('#s' + playerPosition + player.playerTurn).append($('#player' + player.playerTurn));

            if (playerSpacesMoved >= 36) {
                $('#buttonDiv').hide();
                alert(player.playerName + " wins!!");
                
            } else {

                //Updates the players position and total moves.
                $.ajax({
                    type: "PUT",
                    url: "http://localhost:8080/api/game-player",
                    data: JSON.stringify({
                        id: playerId,
                        currentTile: playerPosition,
                        spacesMoved: playerSpacesMoved
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
    //Uodates whos turn it is in game.
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
});
