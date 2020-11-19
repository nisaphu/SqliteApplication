package com.example.sqliteapplication.rx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sqliteapplication.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function3
import io.reactivex.rxkotlin.toObservable
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap

class MultiRxActivity : AppCompatActivity() {

    private val TAG = "MultiRxActivity"
    private var disposable: Disposable? = null
    private var response1: ResponseBody? = null
    private var response2: ResponseBody? = null
    private var response3: ResponseBody? = null
    var items: HashMap<String, Any>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muti_rx)
        requestResult()
    }

    private fun requestResult() {
        items = HashMap()
        items!!["text_search"] = "ข"
        val backendApi = MyRetrofitHelper.ApiInstance()
        disposable = Observable.zip(
            backendApi.searchOne(items!!),
            backendApi.searchTwo(items!!),
            backendApi.searchthree(items!!),
            Function3<ResponseBody, ResponseBody, ResponseBody, Unit> { response, response2, response3 ->
                this.response1 = response
                this.response2 = response2
                this.response3 = response3
            }).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { }
            .doOnTerminate { }
            .subscribe({
                getResponse1(response2)
                Log.d(TAG, "Success Execution! : yes")
            },
                { Log.d(TAG, "Failure in Executing API! : no") }
            )
    }

    private fun getResponse1(t1: ResponseBody?) {
        val jsonObject = JSONObject(t1!!.string())
        val error = jsonObject.getInt("error")
        val msg = jsonObject.getString("msg")
        val result = jsonObject.getJSONObject("result")
        val array = result.getJSONArray("post_list")

        Log.d(TAG, "error : $error")
        Log.d(TAG, "msg : $array")

    }

    private fun obServableCreat() {
        Observable.create<String> {
            it.onNext("Gulf")
            it.onNext("Yin")
            it.onNext("War")
            it.onNext("Save")
        }.subscribe {
            Log.d(TAG, "it : $it")
        }
    }

    private fun obServableJust() {
        Observable.just("Gulf", "Yin", "War", "Save").subscribe(::println)
    }

    private fun obServableFromIterable() {
        val son = listOf("Gulf2", "Yin2", "War2", "Save2")
        Observable.fromIterable(son).subscribe(::println)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null) {
            disposable?.dispose()
        }
    }

}
