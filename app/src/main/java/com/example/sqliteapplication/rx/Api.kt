package com.example.sqliteapplication.rx

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("timeline/timeline_search_category")
    fun searchOne(@FieldMap body: HashMap<String, Any>): Observable<ResponseBody>

    @FormUrlEncoded
    @POST("timeline/timeline_search_v2")
    fun searchTwo(@FieldMap body: HashMap<String, Any>): Observable<ResponseBody>

    @FormUrlEncoded
    @POST("timeline/timeline_search_category")
    fun searchthree(@FieldMap body: HashMap<String, Any>): Observable<ResponseBody>
}