package de.vexxes.penaltycatalog.presentation.screen.player

import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.domain.model.Player
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import de.vexxes.penaltycatalog.ui.theme.Red80
import de.vexxes.penaltycatalog.ui.theme.Yellow100
import de.vexxes.penaltycatalog.ui.theme.Typography
import de.vexxes.penaltycatalog.util.RequestState

@Composable
fun PlayerListContent(
    players: RequestState<List<Player>>,
    navigateToPlayerDetailScreen: (playerId: Int) -> Unit
) {
    if (players is RequestState.Success) {
        DisplayPlayers(
            players = players.data,
            navigateToPlayerDetailScreen = navigateToPlayerDetailScreen
        )
    }
}

@Composable
private fun DisplayPlayers(
    players: List<Player>,
    navigateToPlayerDetailScreen: (playerId: Int) -> Unit
) {
    LazyColumn {
        items(
            items = players,
            key = { player ->
                player.id
            }
        ) { player ->
            PlayerItem(
                player = player,
                navigateToPlayerDetailScreen = navigateToPlayerDetailScreen
            )
        }
    }
}

@Composable
private fun PlayerItem(
    player: Player,
    navigateToPlayerDetailScreen: (playerId: Int) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                navigateToPlayerDetailScreen(player.id)
            }
            .fillMaxWidth()
            .height(60.dp)
    ) {

        PlayerItemNumber(
            modifier = Modifier
                    .weight(0.1f)
                    .padding(2.dp)
                    .align(CenterVertically),
            text = player.number.toString()
        )

        PlayerItemName(
            modifier = Modifier
                .weight(0.5f)
                .padding(2.dp)
                .align(CenterVertically),
            firstName = player.firstName,
            lastName = player.lastName
        )

        PlayerItemGoals(
            modifier = Modifier
                .weight(0.1f)
                .padding(4.dp)
                .align(CenterVertically),
            text = player.goals.toString()
        )

        PlayerItemIngamePenalties(
            modifier = Modifier.weight(0.1f),
            yellowCards = player.yellowCards.toString(),
            twoMinutes = player.twoMinutes.toString(),
            redCards = player.redCards.toString()
        )

    }
}

@Composable
@Preview(showBackground = true)
private fun PlayerItemPreview() {
    val player = Player(id = 0,
        number = 5,
        firstName = "Thomas",
        lastName = "Schneider",
        zipcode = 49424,
        city = "Goldenstedt",
        playedGames = 3,
        goals = 12,
        yellowCards = 0,
        twoMinutes = 0,
        redCards = 0
    )

    PlayerItem(
        player = player,
        navigateToPlayerDetailScreen = { }
    )
}

@Composable
private fun PlayerItemNumber(
    modifier: Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = Typography.titleLarge,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun PlayerItemName(
    modifier: Modifier,
    firstName: String,
    lastName: String
) {
    Text(
        modifier = modifier,
        text = "$lastName, $firstName",
        style = Typography.titleLarge
    )
}

@Composable
private fun PlayerItemGoals(
    modifier: Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = Typography.titleLarge,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun PlayerItemIngamePenalties(
    modifier: Modifier,
    yellowCards: String,
    twoMinutes: String,
    redCards: String
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.33f)
        ) {
            Canvas(
                modifier = Modifier
                    .padding(2.dp)
                    .width(12.dp)
                    .height(20.dp),

                onDraw = {
                    drawRoundRect(
                        color = Yellow100,
                        cornerRadius = CornerRadius(6f, 6f)
                    )
                }
            )


            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 8.dp),
                text = yellowCards,
                style = Typography.bodyLarge,
                textAlign = TextAlign.End,
            )

        }

        Row(
            modifier = Modifier
                .weight(0.33f)
        ) {

            Text(
                text = "2m",
                style = Typography.bodyLarge
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                text = twoMinutes,
                style = Typography.bodyLarge,
                textAlign = TextAlign.End
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.33f)
        ) {
            Canvas(
                modifier = Modifier
                    .padding(2.dp)
                    .width(12.dp)
                    .height(20.dp),

                onDraw = {
                    drawRoundRect(
                        color = Red80,
                        cornerRadius = CornerRadius(6f, 6f)
                    )
                }
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                text = redCards,
                style = Typography.bodyLarge,
                textAlign = TextAlign.End,
            )
        }
    }
}