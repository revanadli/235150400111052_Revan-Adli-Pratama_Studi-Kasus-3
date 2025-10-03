package com.example.aplikasiregistrasi

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplikasiregistrasi.ui.theme.AplikasiRegistrasiTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikasiRegistrasiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("registration") {
            RegistrationScreen(navController = navController)
        }
        composable("detail/{nim}/{nama}/{email}") { backStackEntry ->
            val nim = backStackEntry.arguments?.getString("nim") ?: ""
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            DetailScreen(navController = navController, nim = nim, nama = nama, email = email)
        }
    }
}
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Halaman Login",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                // Logika login bisa ditambahkan di sini
                // Untuk saat ini, kita anggap login berhasil dan pindah ke halaman registrasi
                // jika ingin langsung ke detail. Atau biarkan kosong.
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "LOGIN", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                navController.navigate("registration")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "DAFTAR", fontSize = 16.sp)
        }
    }
}
@Composable
fun RegistrationScreen(navController: NavController) {
    var nim by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var tglLahir by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "NIM: 235150400111052",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Nama: Revan Adli Pratama",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(
            text = "Halaman Registrasi",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = nim,
            onValueChange = { nim = it },
            label = { Text("NIM") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("Nama Lengkap") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        DatePickerView(onDateSelected = { selectedDate -> tglLahir = selectedDate })
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if (nim.isNotBlank() && nama.isNotBlank() && email.isNotBlank() && tglLahir.isNotBlank()) {
                    navController.navigate("detail/$nim/$nama/$email")
                } else {
                    Toast.makeText(context, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "DAFTAR", fontSize = 16.sp)
        }
    }
}
@Composable
fun DetailScreen(navController: NavController, nim: String, nama: String, email: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Detail Pendaftaran",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(text = "NIM: $nim", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
        Text(text = "Nama: $nama", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
        Text(text = "Email: $email", fontSize = 20.sp, modifier = Modifier.padding(bottom = 24.dp))
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "KEMBALI", fontSize = 16.sp)
        }
    }
}

@Composable
fun DatePickerView(onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    var selectedDateText by remember { mutableStateOf("Pilih Tanggal Lahir") }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            val newDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            selectedDateText = newDate
            onDateSelected(newDate)
        }, year, month, day
    )

    Button(onClick = {
        datePickerDialog.show()
    }) {
        Text(text = selectedDateText)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AplikasiRegistrasiTheme {
        // Ganti ini untuk melihat preview layar yang berbeda
        // LoginScreen(navController = rememberNavController())
        // RegistrationScreen(navController = rememberNavController())
        DetailScreen(navController = rememberNavController(), nim = "12345", nama = "Contoh Nama", email = "contoh@email.com")
    }
}
