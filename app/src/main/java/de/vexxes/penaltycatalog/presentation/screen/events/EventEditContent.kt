package de.vexxes.penaltycatalog.presentation.screen.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun EventEditContent(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        
    }
}


@Serializable
data class Event(
    val id: String = "",
    val title: String = "",
    @Contextual val dateTime: LocalDateTime = LocalDateTime.now(),
    @Contextual val meetingTime: LocalTime = LocalTime.now(),
    val address: String = "",
    val description: String = ""
)