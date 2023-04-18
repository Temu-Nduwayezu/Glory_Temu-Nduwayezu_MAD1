package gt.code.movieapp.screens

sealed class AddMovieUIEvent{
    object TitleChanged : AddMovieUIEvent()
    object YearChanged: AddMovieUIEvent()
    object GenresChanged: AddMovieUIEvent()
    object DirectorChanged: AddMovieUIEvent()
    object ActorsChanged: AddMovieUIEvent()
    object PlotChanged: AddMovieUIEvent()
    object RatingChanged: AddMovieUIEvent()
    object submit: AddMovieUIEvent()
}
