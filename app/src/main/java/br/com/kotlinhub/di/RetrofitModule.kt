package br.com.kotlinhub.di

import br.com.kotlinhub.data.network.GitApi
import br.com.kotlinhub.data.network.RetrofitGitApi
import br.com.kotlinhub.util.NetworkConstants
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofitGitApi(): GitApi {

        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RetrofitGitApi::class.java)
    }
}