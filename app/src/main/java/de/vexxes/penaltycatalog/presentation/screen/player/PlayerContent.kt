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

import de.vexxes.penaltycatalog.ui.theme.Red80
import de.vexxes.penaltycatalog.ui.theme.Yellow100
import de.vexxes.penaltycatalog.ui.theme.Typography

@Composable
fun PlayerListContent(

    navigateToPlayerDetailScreen: (playerId: Int) -> Unit
) {
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
        navigateToPlayerDetailScreen = navigateToPlayerDetailScreen
    )
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

        Text(
            modifier = Modifier
                .weight(0.1f)
                .padding(2.dp)
                .align(CenterVertically),
            text = player.number.toString(),
            style = Typography.titleLarge,
            textAlign = TextAlign.Center,
        )

        Text(
            modifier = Modifier
                .weight(0.5f)
                .padding(2.dp)
                .align(CenterVertically),
            text = player.lastName + ", " + player.firstName,
            style = Typography.titleLarge
        )

        Text(
            modifier = Modifier
                .weight(0.1f)
                .padding(4.dp)
                .align(CenterVertically),
            text = player.goals.toString(),
            style = Typography.titleLarge,
            textAlign = TextAlign.Center
        )


        Column(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
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
                    text = player.yellowCards.toString(),
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
                    text = player.twoMinutes.toString(),
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
                    text = player.redCards.toString(),
                    style = Typography.bodyLarge,
                    textAlign = TextAlign.End,
                )
            }

        }
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