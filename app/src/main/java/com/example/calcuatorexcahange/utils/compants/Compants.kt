package com.example.calcuatorexcahange.utils.compants

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay

@ExperimentalMaterial3Api
@Composable
fun CoustemMadeTextField(
    hint: String,

    onChanges:(String)->Unit={}
) {
    var text by rememberSaveable  { mutableStateOf("") }

    TextField(
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Transparent,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(40.dp),
        value = text,
        onValueChange = {
            text = it
            onChanges(it)
        },
        placeholder = { TextUsebla(Hint=hint) },
        )


}

@Composable
fun TextUsebla(
    modifier: Modifier = Modifier,
    Hint: String,
    fontFamily: FontFamily? = null,
    fontSize: TextUnit =12.sp,
    textColor:Color= Color.Black,
    Enabled: Boolean = false,
    Action: () -> Unit = {}
) {
    Text(text = Hint, style = TextStyle(color = textColor, fontSize = fontSize,
        fontFamily =fontFamily
    ),
        modifier = modifier.clickable(enabled = Enabled) {
            Action()
        })
}
@Composable
fun LoadingAnimation(

    circleColor: Color = Color.Magenta,
    animationDelay: Int = 1500
) {

    // 3 circles
    val circles = listOf(
        remember {
            Animatable(initialValue = 0f)
        },
        remember {
            Animatable(initialValue = 0f)
        },
        remember {
            Animatable(initialValue = 0f)
        }
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(Unit) {
            // Use coroutine delay to sync animations
            // divide the animation delay by number of circles
            delay(timeMillis = (animationDelay / 3L) * (index + 1))

            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = animationDelay,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    // outer circle
    Box(
        modifier = Modifier
            .size(size = 200.dp)
            .background(color = Color.Transparent)
    ) {
        // animating circles
        circles.forEachIndexed { index, animatable ->
            Box(
                modifier = Modifier
                    .scale(scale = animatable.value)
                    .size(size = 200.dp)
                    .clip(shape = CircleShape)
                    .background(
                        color = circleColor
                            .copy(alpha = (1 - animatable.value))
                    )
            ) {
            }
        }
    }
}

@Composable
fun LoadingBrogessFullWidth() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LoadingAnimation()
    }

}


@Composable
fun CoustemDiloage(dialogOpene:Boolean,
                   HeadlineMessage:String,
                   MainMessage:String, onClick: (Boolean) -> Unit={}) {
    var dialogOpen by remember {
        mutableStateOf(dialogOpene)
    }

    if (dialogOpen) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality,
                // simply leave this block empty.
                dialogOpen = false
                onClick(false)
            },
            confirmButton = {
                TextButton(onClick = {
                    // perform the confirm action
                    dialogOpen = false
                    onClick(false)
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    dialogOpen = false
                    onClick(false)
                }) {
                    Text(text = "Dismiss")
                }
            },
            title = {
                Text(text = HeadlineMessage)
            },
            text = {
                Text(text = MainMessage)
            },
            modifier = Modifier // Set the width and padding
                .fillMaxWidth()
                .padding(32.dp),
            shape = RoundedCornerShape(5.dp),
            containerColor  = Color.White,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }

}

