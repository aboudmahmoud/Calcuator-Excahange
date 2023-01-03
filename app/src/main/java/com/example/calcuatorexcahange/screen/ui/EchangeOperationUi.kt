package com.example.calcuatorexcahange.screen.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.material3.*

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calcuatorexcahange.screen.viewmodel.ExchgneViewModel
import com.example.calcuatorexcahange.utils.CoinesState
import com.example.calcuatorexcahange.utils.compants.CoustemDiloage
import com.example.calcuatorexcahange.utils.compants.LoadingBrogessFullWidth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExachnegeScreen(exchangeviewModel: ExchgneViewModel = hiltViewModel()) {
    //form
    val options = listOf("AED", "EGP", "USD")
    //to
    val options2 = listOf("AED", "EGP", "USD")
    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }

    //Make Commpent Box to Creat our View
    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
        Column {
            var value: String by remember {
                mutableStateOf("0")
            }
            TextField(
                value = value,
                onValueChange = {

                    if (it.isNotEmpty()) {
                        value = (it.toDouble()).toString()
                    } else {
                        value = "0"
                    }
                    exchangeviewModel.EvenEditInentent(value.toDouble())
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                maxLines = 1,
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    readOnly = true,
                    value = exchangeviewModel.selectedOptionText,
                    modifier = Modifier.menuAnchor(),
                    onValueChange = { },
                    label = { Text("choes currens") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                //Here We Make Select Coint Type That we need , after Select    Aotmtic Courrens Change
                                setCoinType(exchangeviewModel, selectionOption, options, value)
                                expanded = false
                            },
                            text = {
                                Text(text = selectionOption)
                            }
                        )
                    }
                }
            }



            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                readOnly = true,
                onValueChange = { },
                value = exchangeviewModel.AmoutCalacuted.toString()
            )
            ExposedDropdownMenuBox(
                expanded = expanded2,
                onExpandedChange = {
                    expanded2 = !expanded2
                }
            ) {
                TextField(
                    readOnly = true,
                    value = exchangeviewModel.selectedOptionText2,
                    modifier = Modifier.menuAnchor(),
                    onValueChange = { },
                    label = { Text("choes currens") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded2
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded2,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    options2.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                setCoinType2(exchangeviewModel, selectionOption, options2, value)
                                expanded2 = false
                            },
                            text = {
                                Text(text = selectionOption)
                            }
                        )
                    }
                }
            }


        }

    }

    //If There is Error it will Diaply Dilalog Box What is that Error
    if (exchangeviewModel.ErrorStatus) {
        CoustemDiloage(
            dialogOpene = true,
            HeadlineMessage = "Error",
            MainMessage = exchangeviewModel.ErrorMessage!!
        )
    }
    //If its Loading it will Apper Pink ProgessPar
    if (exchangeviewModel.LoadingStatus) {
        LoadingBrogessFullWidth()
    }
}




private fun setCoinType(
    exchangeviewModel: ExchgneViewModel,
    selectionOption: String,
    options: List<String>,
    value: String
) {
    exchangeviewModel.selectedOptionText = selectionOption
    when (exchangeviewModel.selectedOptionText) {
        options[0] -> {
            exchangeviewModel.coinState1 = CoinesState.AED
        }
        options[1] -> {
            exchangeviewModel.coinState1 = CoinesState.EGP
        }
        options[2] -> {
            exchangeviewModel.coinState1 = CoinesState.USD
        }
    }
    exchangeviewModel.EvenEditInentent(value.toDouble())
}

private fun setCoinType2(
    exchangeviewModel: ExchgneViewModel,
    selectionOption: String,
    options2: List<String>,
    value: String
) {
    exchangeviewModel.selectedOptionText2 = selectionOption
    when (exchangeviewModel.selectedOptionText2) {
        options2[0] -> {
            exchangeviewModel.coinState2 = CoinesState.AED
        }
        options2[1] -> {
            exchangeviewModel.coinState2 = CoinesState.EGP
        }
        options2[2] -> {
            exchangeviewModel.coinState2 = CoinesState.USD
        }

    }
    exchangeviewModel.EvenEditInentent(value.toDouble())
}