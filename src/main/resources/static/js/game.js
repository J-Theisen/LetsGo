var playerPosition = 1;

var player1Image = "/images/sq.png";
var player2Image = "/images/cat.png";
var player3Image = "/images/dog.png";
var player4Image = "/images/bird.png";

var currentP


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
        url: 'http://localhost:8080/api/game/' + gameIdSplit,
        success: function (boardTileArray) {

            $.each(boardTileArray, function (index, boardTile) {
                if(boardTile.tileType === "GREEN"){
                    $('#t' + boardTile.boardTileId).css('background-color', 'greenyellow');
                } else if(boardTile.tileType === "BLUE"){
                    $('#t' + boardTile.boardTileId).css('background-color', 'blue');
                } else if(boardTile.tileType === "RED"){
                    $('#t' + boardTile.boardTileId).css('background-color', 'red');
                }
               
            });
        },
        error: function () {
           alert("FAILURE");
        }
    });
};

function loadCharactersOnBoard(){
    var pageUrl = window.location.href;
    var splitUrl = pageUrl.split("/");
    var gameIdSplit = splitUrl[5];
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/game/players/' + gameIdSplit,
        success: function (playerArray) {
            $.each(playerArray, function (index, player) {
                var playerTurn = player.playerTurn;
                var playerImage = player.imageUrl
                var div = '<div id="player'+ playerTurn +' class="player'+ playerTurn +'Class"><p>'+player.playerName+'</p> <img id="player' + playerTurn + 'Image" src="'+playerImage+'"> </div>';
                $('#t' + playerPosition).append(div);
            });

        },
        error: function () {
            alert("FAILURE");
         }
     });
};

$('#rollButton').on('click', function () {
    var min = 1;
    var max = 6;
    var rollNumber = Math.floor(Math.random() * (+max - +min)) + +min;
    $('#rollNumber').text(rollNumber);
    $('#t' + playerPosition).css('background-color', 'lightblue');
    //var playerPositionStart = playerPosition;
    playerPosition += rollNumber;
    if (playerPosition > 12) {
        playerPosition = playerPosition - 12;
    }
    $('#t' + playerPosition).append($('#ghostDiv'));
    $('#t' + playerPosition).css({ 'background-color': 'lightgreen', 'transition': "background-color 2s ease" });
    $().empty
});
