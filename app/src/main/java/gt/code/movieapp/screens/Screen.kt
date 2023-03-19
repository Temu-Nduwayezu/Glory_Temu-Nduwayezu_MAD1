package gt.code.movieapp.screens

const val movieId = "movieId"
sealed class Screen(val route: String){
    object Home: Screen(route = "home_screen")
    object Details:Screen(route = "details_screen/{$movieId}")
    object Favorites: Screen(route = "favorites_screen"){
        fun passId(id: String): String {
            return "details_screen/$id"
        }
    }

}
