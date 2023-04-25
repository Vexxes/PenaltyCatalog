package de.vexxes.penaltycatalog.presentation.screen.events

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.domain.enums.EventType
import de.vexxes.penaltycatalog.domain.enums.PlayerState
import de.vexxes.penaltycatalog.domain.model.EventPlayerAvailability
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.uistate.EventUiState
import de.vexxes.penaltycatalog.domain.uistate.eventUiStateExample1
import de.vexxes.penaltycatalog.domain.uistate.eventUiStateExample2
import de.vexxes.penaltycatalog.domain.uistate.eventUiStateExample3
import de.vexxes.penaltycatalog.ui.theme.Typography
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun EventDetailContent(
    eventUiState: EventUiState,
    onMapsClicked: () -> Unit,
    onAvailabilityChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        EventHeader(text = eventUiState.title)
        EventType(type = eventUiState.type)
        EventStartOfEvent(dateTime = eventUiState.startOfEvent)
        EventStartOfMeeting(dateTime = eventUiState.startOfMeeting)

        if (eventUiState.address.isNotEmpty()) EventAddress(address = eventUiState.address, onMapsClicked = onMapsClicked)
        if (eventUiState.description.isNotEmpty()) EventDescription(description = eventUiState.description)

        PlayerAvailabilityList(
            players = eventUiState.eventAvailablePlayers,
            playerAvailabilities = eventUiState.playerAvailability,
            onAvailabilityChanged = onAvailabilityChanged
        )
    }
}

@Composable
private fun EventHeader(
    text: String
) {
    Text(
        text = text,
        style = Typography.titleLarge
    )
}

@Composable
private fun EventType(
    type: EventType
) {
    LabelHeader(
        modifier = Modifier.padding(top = 32.dp),
        text = stringResource(id = R.string.Type)
    )
    TextValue(text = stringResource(id = type.nameId))
}

@Composable
private fun EventStartOfEvent(
    dateTime: LocalDateTime
) {
    val output = DateTimeFormatter
        .ofPattern("eeee, dd. MMMM y HH:mm")
        .format(dateTime.toJavaLocalDateTime())

    LabelHeader(
        modifier = Modifier.padding(top = 32.dp),
        text = stringResource(id = R.string.StartOfEvent)
    )
    TextValue(text = output)
}

@Composable
private fun EventStartOfMeeting(
    dateTime: LocalDateTime
) {
    val output = DateTimeFormatter
        .ofPattern("HH:mm")
        .format(dateTime.toJavaLocalDateTime())

    LabelHeader(
        modifier = Modifier.padding(top = 32.dp),
        text = stringResource(id = R.string.StartOfMeeting)
    )
    TextValue(text = output)
}

@Composable
private fun EventAddress(
    address: String,
    onMapsClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            LabelHeader(text = stringResource(id = R.string.Address))
            TextValue(text = address)
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(
                onClick = { onMapsClicked() }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(8.dp),
                    imageVector = Icons.Outlined.Map,
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
private fun EventDescription(
    description: String
) {
    LabelHeader(
        modifier = Modifier.padding(top = 32.dp),
        text = stringResource(id = R.string.Description)
    )
    TextValue(text = description)
}

@Composable
private fun PlayerAvailabilityList(
    players: List<Player>,
    playerAvailabilities: List<EventPlayerAvailability>,
    onAvailabilityChanged: (String) -> Unit
) {
    val playerCount = players.count()
    val availablePlayerCount = playerAvailabilities.count {
        it.playerState == PlayerState.PRESENT ||
                it.playerState == PlayerState.PAID_BEER
    }
    val percentageAvailability = availablePlayerCount.toDouble() / playerCount.toDouble() * 100.0

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LabelHeader(
            modifier = Modifier.padding(top = 32.dp),
            text = stringResource(id = R.string.PlayerAvailbility)
        )

        LabelHeader(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(),
            text = "$availablePlayerCount / $playerCount, $percentageAvailability %",
            textAlign = TextAlign.End
        )
    }

    players.forEach { player ->
        var playerState = playerAvailabilities.find { it.playerId ==  player.id }?.playerState
        if (playerState == null) playerState = PlayerState.UNDEFINED

        PlayerAvailabilityItem(
            player = player,
            playerState = playerState,
            onAvailabilityChanged = onAvailabilityChanged
        )
    }
}

@Composable
private fun PlayerAvailabilityItem(
    player: Player,
    playerState: PlayerState,
    onAvailabilityChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable { onAvailabilityChanged(player.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlayerStateIcon(playerState = playerState)
        TextValue(text = "${player.lastName}, ${player.firstName}")
    }
}

@Composable
private fun LabelHeader(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign? = null
) {
    Text(
        modifier = modifier,
        text = text,
        style = Typography.labelMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = textAlign
    )
}

@Composable
private fun TextValue(
    text: String
) {
    Text(
        text = text,
        style = Typography.bodyLarge
    )
}

@Composable
private fun PlayerStateIcon(
    playerState: PlayerState
) {
    Icon(
        modifier = Modifier
            .padding(end = 8.dp),
        imageVector = playerState.icon,
        contentDescription = "",
        tint = playerState.tintColor
    )
}

@Composable
@Preview(showBackground = true)
private fun EventDetailContentPreview1() {
    EventDetailContent(
        eventUiState = eventUiStateExample1(),
        onMapsClicked = { },
        onAvailabilityChanged = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun EventDetailContentPreview2() {
    EventDetailContent(
        eventUiState = eventUiStateExample2(),
        onMapsClicked = { },
        onAvailabilityChanged = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun EventDetailContentPreview3() {
    EventDetailContent(
        eventUiState = eventUiStateExample3(),
        onMapsClicked = { },
        onAvailabilityChanged = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PlayerStateIconPreview() {
    Row {
        PlayerStateIcon(playerState = PlayerState.PAID_BEER)
        PlayerStateIcon(playerState = PlayerState.PRESENT)
        PlayerStateIcon(playerState = PlayerState.CANCELED)
        PlayerStateIcon(playerState = PlayerState.NOT_PRESENT)
        PlayerStateIcon(playerState = PlayerState.UNDEFINED)
    }
}