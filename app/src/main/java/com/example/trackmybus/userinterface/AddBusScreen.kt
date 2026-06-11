package com.example.trackmybus.userinterface

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackmybus.model.Bus
import com.example.trackmybus.model.BusCreateRequest
import com.example.trackmybus.network.RetrofitInstance
import com.example.trackmybus.session.SessionManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBusScreen(
    onBackClick: () -> Unit,
    onCreateBusClick: (String, String, String) -> Unit,
    onBusSelect: (String) -> Unit
) {
    var busNumber by remember { mutableStateOf("") }
    var seatCapacity by remember { mutableStateOf("") }
    var routeName by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var busList by remember {
        mutableStateOf<List<Bus>>(emptyList())
    }
    LaunchedEffect(Unit) {

        try {

            val response =
                RetrofitInstance.api.getBusesByDriverId(
                    SessionManager.driverId
                )

            if (response.isSuccessful) {

                busList = response.body() ?: emptyList()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Add bus",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Set up a vehicle to begin tracking",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Normal
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF8FBFF)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Create New Bus Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(48.dp),
                            color = Color(0xFFF0EDFF),
                            shape = CircleShape
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.DirectionsBus,
                                    contentDescription = null,
                                    tint = Color(0xFF6A39FF)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Create new bus",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "Register vehicle details",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        "Bus number",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = busNumber,
                        onValueChange = { busNumber = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g. B-204", color = Color.LightGray) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.DirectionsBus,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            focusedBorderColor = Color(0xFF6A39FF),
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Total seat capacity",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = seatCapacity,
                        onValueChange = { seatCapacity = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g. 40", color = Color.LightGray) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Groups,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        },
                        trailingIcon = {
                            Column {
                                Icon(
                                    Icons.Default.ArrowDropUp,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            }
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            focusedBorderColor = Color(0xFF6A39FF),
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Route name",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = routeName,
                        onValueChange = { routeName = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g. Campus → Sector 14", color = Color.LightGray) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Route,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            focusedBorderColor = Color(0xFF6A39FF),
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))


                    Button(
                        onClick = {

                            scope.launch {

                                try {

                                    val response = RetrofitInstance.api.createBus(

                                        BusCreateRequest(
                                            busNumber = busNumber,
                                            seatCapacity = seatCapacity.toIntOrNull() ?: 0,
                                            routeName = routeName,
                                            driverId = SessionManager.driverId
                                        )
                                    )

                                    if (response.isSuccessful) {

                                        Toast.makeText(
                                            context,
                                            response.body() ?: "Bus Created Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        onCreateBusClick(
                                            busNumber,
                                            seatCapacity,
                                            routeName
                                        )

                                    } else {

                                        Toast.makeText(
                                            context,
                                            "Failed: ${response.code()}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                } catch (e: Exception) {

                                    Toast.makeText(
                                        context,
                                        e.message ?: "Unknown Error",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6A39FF)
                        )
                    ) {
                        Text(
                            text = "Create bus",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE0E0E0))
                Text(
                    text = "OR",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE0E0E0))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Select Existing Bus Section
            if (busList.isNotEmpty()) {

                Text(
                    text = "Select existing bus",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Recently used vehicles",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 1.dp
                    )
                ) {

                    Column {

                        busList.forEachIndexed { index, bus ->

                            BusListItem(
                                name = bus.busNumber,
                                details = bus.routeName,
                                onClick = onBusSelect
                            )

                            if (index < busList.lastIndex) {

                                HorizontalDivider(
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp
                                    ),
                                    color = Color(0xFFF0F0F0)
                                )
                            }
                        }
                    }
                }
            }}}}
@Composable
fun BusListItem(
    name: String,
    details: String,
    onClick: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(name)
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                text = details,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Icon(
            Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}
