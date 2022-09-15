//package com.example.week3assignment.Presenter
//
//import android.net.ConnectivityManager
//import android.net.NetworkCapabilities
//import com.example.week3assignment.model.SongData
//import com.example.week3assignment.model.room.SongDataRepo
//import io.reactivex.disposables.CompositeDisposable
//import javax.inject.Inject
//
//interface SongPresenter {
//    fun init(viewContract: ViewContractAllSongs)
//    fun checkNetworkConnection(connectivityManager: ConnectivityManager)
//    fun getAllSongs()
//    fun destroy()
//
//}
//
//class AllSongsPresenterImpl @Inject constructor(
//    private val disposables: CompositeDisposable = CompositeDisposable(),
//    private val repository: SongDataRepo
//    ):SongPresenter {
//        private var contractAllSongs: ViewContractAllSongs? = null
//    override fun init (viewContract: ViewContractAllSongs) {
//        contractAllSongs = viewContract
//    }
//    override fun checkNetworkConnection(connectivityManager: ConnectivityManager){
//        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {
//            if(!it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
//                contractAllSongs?.onFailure(Throwable("No Internet Connection"))
//            }
//        }
//    }
//    override fun getAllSongs() {
//        contractAllSongs?.loadingSongs(true)
//        repository.getAllSongs()
//            .subscribe(
//                {songs -> contractAllSongs?.onSuccess(songs)},
//                {error -> contractAllSongs?.onFailure(error)}
//            )
//            .also {disposables.add(it)}
//    }
//    override fun destroy(){
//        disposables.clear()
//        contractAllSongs = null
//    }
//}
//
//
//interface ViewContractAllSongs {
//    fun loadingSongs(isLoading: Boolean)
//    fun onSuccess(songs: List<SongData>)
//    fun onFailure(error: Throwable)
//}