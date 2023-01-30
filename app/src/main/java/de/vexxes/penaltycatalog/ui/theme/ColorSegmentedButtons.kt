package de.vexxes.penaltycatalog.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * A color scheme holds all the named color parameters for a [ColorSegmentedButtons].
 *
 * Color schemes are designed to be harmonious, ensure accessible text, and distinguish UI
 * elements and surfaces from one another. There is one built-in baseline scheme,
 * [colorSchemeSegButtons], that can be used as-is or customized.
 *
 * @property backgroundPaid The backgroundPaid color is used as background color of components to
 * show the paid state.
 * @property foregroundPaid The foregroundPaid color is used as foreground color of components to
 * show the paid state.
 * @property backgroundNotPaid The backgroundNotPaid color is used as background color of components
 * to show the not paid state.
 * @property foregroundNotPaid The foregroundNotPaid color us used as foreground color of components
 * to show the not paid state.
 */
data class ColorSegmentedButtons(
    val backgroundPaid: Color,
    val foregroundPaid: Color,
    val backgroundNotPaid: Color,
    val foregroundNotPaid: Color
)

fun colorSchemeSegButtons(
    backgroundPaid: Color = Color(0xFFABFFCF),
    foregroundPaid: Color = Color(0xFF0A9300),
    backgroundNotPaid: Color = Color(0xFFFFDAD6),
    foregroundNotPaid: Color = Color(0xFFc60005)
): ColorSegmentedButtons =
    ColorSegmentedButtons(
        backgroundPaid = backgroundPaid,
        foregroundPaid = foregroundPaid,
        backgroundNotPaid = backgroundNotPaid,
        foregroundNotPaid = foregroundNotPaid,
    )