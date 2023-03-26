package gt.code.movieapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import gt.code.movieapp.models.MovieViewModel
import gt.code.movieapp.navigation.MyNavigation
import gt.code.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // val viewModel: MovieViewModel by viewModels()
        setContent {
            MovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {

                        // Display the movie list
                        MyNavigation()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("MainActivity", "I am onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("MainActivity", "I am onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("MainActivity", "I am onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("MainActivity", "I am onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivity", "I am onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("MainActivity", "I am onRestart")
    }
}

