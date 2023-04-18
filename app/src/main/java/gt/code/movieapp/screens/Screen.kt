package gt.code.movieapp.screens

const val MOVIE_ID = "movieId"
sealed class Screen(val route: String){
    object Home: Screen(route = "home_screen")
    object Details:Screen(route = "details_screen/{$MOVIE_ID}") {
        fun passId(id: String): String {
            return this.route.replace(oldValue = "{$MOVIE_ID}", newValue = id)
        }
    }
    object Favorites: Screen(route = "favorites_screen")

    object AddMovie: Screen(route = "addMovie_screen")


}
