package com.example.simplearithmetic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
// import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.text.input.KeyboardType
import com.example.simplearithmetic.ui.theme.SimpleArithmeticTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleArithmeticTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NumberInputExample(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun NumberInputExample(modifier: Modifier = Modifier) {
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    // var selectedOperation by remember { mutableStateOf("Add") }
    var result by remember { mutableStateOf("") }
    // number are saved with each recomposition

    Column(
        modifier = Modifier
            .fillMaxSize()  // Fill the entire screen
            .padding(16.dp),  // Optional padding
        horizontalAlignment = Alignment.CenterHorizontally,  // Center content horizontally
        verticalArrangement = Arrangement.Center  // Center content vertically
    ) {
        // TextField with number keyboard
        TextField(
            value = number1,
            onValueChange = {
                // Only allow number characters
                if (it.all { char -> char.isDigit() }) {
                    number1 = it
                }
                 }, // 'it' refers to the new input from the TextField
            label = { Text("Enter a number") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Number

            )
        )

        // Second TextField
        TextField(
            value = number2,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    number2 = it
                }
            },
            label = { Text("Enter the second number") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Number

            )
        )

        // Display the operand selection buttons
        Text(text = "Select an Operation:")

        // Add buttons to each Operation
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { performOperation("Add", number1, number2) { result = it } }) {
                Text("Add")
            }
            Button(onClick = { performOperation("Subtract", number1, number2) { result = it } }) {
                Text("Subtract")
            }
            Button(onClick = { performOperation("Multiply", number1, number2) { result = it } }) {
                Text("Multiply")
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { performOperation("Divide", number1, number2) { result = it } }) {
                Text("Divide")
            }
            Button(onClick = { performOperation("Modulus", number1, number2) { result = it } }) {
                Text("Modulus")
            }
        }

        // Display Result
        Text(text = "Result: $result", modifier = Modifier.padding(top = 16.dp))
    }
}

// @Composable
fun performOperation(
    operation: String,
    number1: String,
    number2: String,
    onResult: (String) -> Unit)
{
    // Turn num into values
    val num1 = number1.toIntOrNull()
    val num2 = number2.toIntOrNull()

    // If conditioning

    if (num1 != null && num2 != null) {
        val result = when (operation) {
            "Add" -> num1 + num2
            "Subtract" -> num1 - num2
            "Multiply" -> num1 * num2
            "Divide" -> if (num2 != 0) num1 / num2 else "Error: Divide by Zero not allowed"
            "Modulus" -> if (num2 != 0) num1 % num2 else "Error: Modulus by 0 Error not allowed"
            else -> "Unknown operation"
        }
        onResult(result.toString())
    } else {
        onResult("Input Missing Operation")
    }

}

@Preview(showBackground = true)
@Composable
fun NumberInputPreview() {
    SimpleArithmeticTheme {
        NumberInputExample()
    }
}
