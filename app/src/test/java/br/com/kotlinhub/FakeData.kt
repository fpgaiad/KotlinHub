package br.com.kotlinhub

import br.com.kotlinhub.data.model.KotlinReposResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response

object FakeData {
    private val gson = Gson()

    fun getFakeKotlinReposSuccessResponse(): Response<KotlinReposResponse> {
        val json = "{\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": 51148780,\n" +
                "      \"name\": \"architecture-samples\",\n" +
                "      \"owner\": {\n" +
                "        \"login\": \"android\",\n" +
                "        \"avatar_url\": \"https://avatars3.githubusercontent.com/u/32689599?v=4\"\n" +
                "      },\n" +
                "      \"html_url\": \"https://github.com/android/architecture-samples\",\n" +
                "      \"description\": \"A collection of samples to discuss and showcase different architectural tools and patterns for Android apps.\",\n" +
                "      \"stargazers_count\": 37101,\n" +
                "      \"forks_count\": 10299\n" +
                "    }\n" +
                "  ]\n" +
                "}"
        val fakeKotlinReposResponse = gson.fromJson(json, KotlinReposResponse::class.java)
        return Response.success(fakeKotlinReposResponse)
    }

    fun getFake404ErrorResponse(): Response<KotlinReposResponse> {
        return Response.error(
            404,
            ResponseBody.create(
                "application/json".toMediaTypeOrNull(),
                "{\"error\":{\"message\":\"Data Not Found\"}}"
            )
        )
    }
}