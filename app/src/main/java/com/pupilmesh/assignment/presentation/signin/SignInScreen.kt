package com.pupilmesh.assignment.presentation.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pupilmesh.assignment.R

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = hiltViewModel(),
    loggedIn : () -> Unit
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val isPasswordVisible = rememberSaveable { mutableStateOf(false) }
    val isLoggedIn = signInViewModel.isLoggedIn.value

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            loggedIn()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Row(modifier = Modifier.padding(10.dp)) {
                Icon(Icons.Default.Cancel, null)
                Text("Sign In", modifier = Modifier.padding(start = 10.dp))
            }
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .clip(
                            RoundedCornerShape(
                                10.dp
                            )
                        )
                        .background(Color.Gray),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(
                            "PupilMesh", color = Color.White, fontFamily = FontFamily.Serif,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            "Welcome back", color = Color.White, fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold, fontSize = 20.sp
                        )
                        Text(
                            "Please enter your details to sign in",
                            color = Color.White,
                            fontFamily = FontFamily.Serif,
                            fontSize = 12.sp
                        )
                        Row(modifier = Modifier.padding(10.dp)) {
                            IconRender(R.drawable.google)
                            Spacer(modifier = Modifier.padding(10.dp))
                            IconRender(R.drawable.apple_logo)
                        }
                        Row(modifier = Modifier.padding(5.dp)) {
                            Divider(
                                color = Color.White,
                                thickness = 0.5.dp,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(10.dp)
                            )
                            Text("OR", color = Color.White)
                            Divider(
                                color = Color.White,
                                thickness = 0.5.dp,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(10.dp)
                            )
                        }
                        OutlinedTextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            placeholder = { Text("Your Email Address", color = Color.White) },
                            modifier = Modifier.padding(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.White
                            )
                        )
                        OutlinedTextField(value = password.value,
                            onValueChange = { password.value = it },
                            placeholder = { Text("Password", color = Color.White) },
                            modifier = Modifier.padding(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.White,
                                unfocusedTrailingIconColor = Color.White
                            ),
                            visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                Icon(if (isPasswordVisible.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                    null,
                                    modifier = Modifier.clickable {
                                        isPasswordVisible.value = !isPasswordVisible.value
                                    }
                                )
                            }
                        )
                        Button(
                            onClick = {signInViewModel.signIn(email.value, password.value)},
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.DarkGray
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp, vertical = 10.dp),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Sign In", color = Color.White)
                        }
                        Spacer(modifier = Modifier.padding(15.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun IconRender(Icon: Int) {
    Box(modifier = Modifier
        .size(40.dp)
        .border(1.dp, Color.White, CircleShape)
        .clip(CircleShape)) {
        Icon(painterResource(Icon), null, modifier = Modifier.padding(10.dp))
    }
}