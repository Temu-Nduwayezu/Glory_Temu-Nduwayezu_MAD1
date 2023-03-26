package gt.code.movieapp.screens

const val movieId = "movieId"
sealed class Screen(val route: String){
    object Home: Screen(route = "home_screen")
    object Details:Screen(route = "details_screen/{$movieId}")
    object Favorites: Screen(route = "favorites_screen"){
        fun passId(id: String): String { // the fn did not work, I was not able to call the function in HomeScreen
            return "details_screen/$id"
        }
    }

}
