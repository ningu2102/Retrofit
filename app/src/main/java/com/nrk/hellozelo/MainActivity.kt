package com.nrk.hellozelo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import com.nrk.hellozelo.databinding.ActivityMainBinding
import com.nrk.hellozelo.model.Album
import com.nrk.hellozelo.model.AlbumItem
import com.nrk.hellozelo.network.RetrofitInstance
import com.nrk.hellozelo.service.AlbumService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofitInstance: AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        retrofitInstance = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)

        binding.btnGetAll.setOnClickListener {
            getAll()
        }
    }

    private fun getAll(){
        binding.textView.text = ""

        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofitInstance.getAlbums()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        Log.d("NRK_TEST", response.body()?.size.toString())
                        var albums = response.body()
                        var valueToBeShown = ""
                        if(!albums.isNullOrEmpty()){
                            for (item in albums){
                                valueToBeShown += "title: " + item.title + "\n\n"
                            }
                        }
                        binding.textView.text = valueToBeShown
                        //Do something with response e.g show to the UI.
                    } else {
                    }
                } catch (e: HttpException) {
                } catch (e: Throwable) {
                }
            }
        }
    }
}