package de.vexxes.penaltycatalog.presentation.screen.player

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.component.EmptyContent
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.model.playerExample
import de.vexxes.penaltycatalog.ui.theme.Red80
import de.vexxes.penaltycatalog.ui.theme.Typography
import de.vexxes.penaltycatalog.ui.theme.Yellow100

@Composable
fun PlayerListContent(
    players: List<Player>,
    navigateToPlayerDetailScreen: (playerId: String) -> Unit
) {
    if(players.isNotEmpty()) {
        DisplayPlayers(
            players = players,
            navigateToPlayerDetailScreen = navigateToPlayerDetailScreen
        )
    } else {
        EmptyContent()
    }
}

@Composable
private fun DisplayPlayers(
    players: List<Player>,
    navigateToPlayerDetailScreen: (playerId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = players,
            key = { player ->
                player.hashCode()
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
    navigateToPlayerDetailScreen: (playerId: String) -> Unit
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
                .padding(4.dp)
                .weight(0.1f)
                .padding(2.dp)
                .align(CenterVertically),
            text = player.number.toString()
        )

        PlayerItemName(
            modifier = Modifier
                .padding(4.dp)
                .weight(0.5f)
                .padding(2.dp)
                .align(CenterVertically),
            firstName = player.firstName,
            lastName = player.lastName
        )

        PlayerItemGoals(
            modifier = Modifier
                .padding(4.dp)
                .weight(0.1f)
                .align(CenterVertically),
            text = player.goals.toString()
        )

        PlayerItemIngamePenalties(
            modifier = Modifier
                .padding(2.dp)
                .weight(0.1f),
            yellowCards = player.yellowCards.toString(),
            twoMinutes = player.twoMinutes.toString(),
            redCards = player.redCards.toString()
        )
    }

    Divider()
}

@Composable
@Preview(showBackground = true)
private fun PlayerItemPreview() {
    PlayerItem(
        player = playerExample(),
        navigateToPlayerDetailScreen = { }
    )
}

@Preview(showBackground = true)
@Composable
fun PlayerListContentPreview() {

    val players = listOf(
        playerExample(),
        playerExample(),
        playerExample()
    )

    PlayerListContent(
        players = players,
        navigateToPlayerDetailScreen = { }
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyContentPreview() {
    EmptyContent()
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