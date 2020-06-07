package com.elevenetc.android.news.core.api.newsapi

import com.elevenetc.android.news.core.api.NewsApi
import com.squareup.moshi.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Named


@Module
class NewsApiOrgModule {

    @Provides
    fun newsApi(inst: NewsApiImpl): NewsApi = inst

    @Provides
    fun retrofitApi(@Named(Names.ENDPOINT) endpoint: String): NewsApiOrg {

        val client = OkHttpClient.Builder()
            .build()

        val moshi = Moshi.Builder()
            .add(NullSafetyAdapter)
            .add(DateAdapter())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(endpoint)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(NewsApiOrg::class.java)
    }

    @Provides
    fun mapper(): ModelsMapper {
        return ModelsMapper()
    }

    @Named(Names.ENDPOINT)
    @Provides
    fun endpoint(): String {
        return "https://newsapi.org"
    }

    @Named(Names.API_KEY)
    @Provides
    fun apiKey(): String {
        return "6c1f328d50c14011bb7aaecdea767e8c"
    }

    object Names {
        const val API_KEY = "apiKey"
        const val ENDPOINT = "endpoint"
    }
}

object NullSafetyAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) return reader.nextString()
        reader.nextNull<Unit>()
        return ""
    }
}

private class DateAdapter : JsonAdapter<Date>() {
    private val dateFormat = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            dateFormat.parse(dateAsString)
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            writer.value(value.toString())
        }
    }

    companion object {
        const val SERVER_FORMAT = ("yyyy-MM-dd'T'HH:mm:ssZ")
    }
}