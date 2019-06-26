package com.rathana.photo_book.app.di.module

import com.rathana.photo_book.app.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class NetworkModule {

    companion object{
        val API_KEY=""
        val HOST="https://picsum.photos"
        const val NO_AUTH_TOKEN ="NO_AUTH_TOKEN"
        const val AUTH_TOKEN="AUTH_TOKEN"
    }

    @Provides
    @ApplicationScope
    @Named("HOST")
    fun provideHostName():String =HOST


    @Provides
    @ApplicationScope
    fun provideRetrofitBuilder(@Named("HOST") host: String):
            Retrofit.Builder=Retrofit.Builder()
            .baseUrl(host)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())


    @Provides
    @ApplicationScope
    fun provideOkHttpClient(): OkHttpClient{
        val builder= OkHttpClient.Builder()
        builder.addInterceptor{chain ->
            val original = chain.request()
            val request =  original.newBuilder()
                .header("Accept", "application/json")
                .header("Authorization", API_KEY)
                .method(original.method(), original.body())
                .build();

            val response =chain.proceed(request)

            response
        }

        return builder.build()
    }


    //RETROFIT WITH AUTH_TOKEN TO SERVER
    @Provides
    @ApplicationScope
    @Named(AUTH_TOKEN)
    fun provideRetrofitAuthToken(retrofitBuilder : Retrofit.Builder,okHttpClient:OkHttpClient ): Retrofit=
        retrofitBuilder.client(okHttpClient).build()

    //RETROFIT HAS NO AUTH_TOKEN TO SERVER
    @Provides
    @ApplicationScope
    @Named(NO_AUTH_TOKEN)
    fun provideRetrofit(retrofitBuilder : Retrofit.Builder): Retrofit=
        retrofitBuilder.build()

}
