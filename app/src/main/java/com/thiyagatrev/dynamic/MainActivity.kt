package com.thiyagatrev.dynamic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thiyagatrev.dynamic.ui.theme.DynamicAppIconAndNameTheme

class MainActivity : ComponentActivity() {
    private  val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DynamicAppIconAndNameTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Column() {

                        Button(onClick = {
                            val sp = getSharedPreferences("appSettings", Context.MODE_PRIVATE)
                            val previousIcon = sp.getBoolean("appIcon", false)
                            sp.edit().putBoolean("appIcon", !previousIcon).apply()
                            Toast.makeText(this@MainActivity, if (sp.getBoolean("appIcon", false)) "Red Icon" else "Green Icon", Toast.LENGTH_SHORT).show()
                            startService(Intent(this@MainActivity, ChangeAppIconService::class.java))

                        }) {
                            Text(text = "Change the App icon When App is killed")
                        }

                        Button(onClick = {
                            val sp = getSharedPreferences("appSettings", Context.MODE_PRIVATE)

                            val previousIcon = sp.getBoolean("appIcon", false)
                            sp.edit().putBoolean("appIcon", !previousIcon).apply()
                            Toast.makeText(this@MainActivity, if (sp.getBoolean("appIcon", false)) "Red Icon" else "Green Icon", Toast.LENGTH_SHORT).show()

                        }) {
                            Text(text = "Change the App icon When home button clicked")
                        }
                    }

                }
            }
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DynamicAppIconAndNameTheme {
        Greeting("Android")
    }
}