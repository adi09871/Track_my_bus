package com.example.trackmybus.userinterface

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.trackmybus.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun studentlogin (


){

    var email by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFE1F5FE))
        .padding(24.dp),horizontalAlignment = Alignment.CenterHorizontally) {


        Spacer(modifier = Modifier.size(60.dp))

        Surface(
            modifier = Modifier.size(100.dp), color = Color(0xFF0061D5),
            shape = RoundedCornerShape(28.dp)
        ) {
            Box(contentAlignment = Alignment.Center)
            {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }

        }
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            text = "Student Login",
            color = Color.Black,
            fontSize = 28.sp,
            letterSpacing = 1.sp
        )


        Spacer(modifier = Modifier.size(16.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Welcome back",
                color = Color.Black,
                fontSize = 30.sp,
                letterSpacing = 0.5.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,

            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Please login to your account",
                color = Color.Black,
                fontSize = 18.sp,
                letterSpacing = 0.5.sp,
                textAlign =TextAlign.Left,
            )
        }
        Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = "Username",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp,
                textAlign = TextAlign.Left,
            )

            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = email, onValueChange = {email = it }, modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Enter your college email id ") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
    contentDescription = null,
                        tint = Color(0xFF0061D5))
                }, shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF0061D5),
                    unfocusedBorderColor = Color(0xFFCCCCCC)
                ), singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )


    }}





