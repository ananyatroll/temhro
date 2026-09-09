package com.example.ui.api

import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

object TinatNetworkConfig {
    const val BASE_URL = "https://web-production-16f9b.up.railway.app/"
}

data class ActivateRequest(
    @Json(name = "phone") val phone: String,
    @Json(name = "code") val code: String
)

data class PackageDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "name") val name: String? = null
)

data class ActivateResponse(
    @Json(name = "success") val success: Boolean,
    @Json(name = "accessToken") val accessToken: String? = null,
    @Json(name = "package") val `package`: PackageDto? = null,
    @Json(name = "error") val error: String? = null
)

data class EntitlementDto(
    @Json(name = "entitlementId") val entitlementId: String? = null,
    @Json(name = "packageKey") val packageKey: String? = null,
    @Json(name = "packageLabel") val packageLabel: String? = null,
    @Json(name = "activatedAt") val activatedAt: String? = null
)

data class EntitlementResponse(
    @Json(name = "success") val success: Boolean,
    @Json(name = "package") val `package`: PackageDto? = null,
    @Json(name = "entitlement") val entitlement: EntitlementDto? = null,
    @Json(name = "error") val error: String? = null
)

data class PurchaseRequestDto(
    @Json(name = "reference") val reference: String,
    @Json(name = "productId") val productId: String,
    @Json(name = "category") val category: String,
    @Json(name = "stream") val stream: String? = null,
    @Json(name = "academicYear") val academicYear: Int? = null,
    @Json(name = "department") val department: String? = null,
    @Json(name = "plan") val plan: String,
    @Json(name = "amount") val amount: Int,
    @Json(name = "currency") val currency: String = "ETB",
    @Json(name = "language") val language: String = "en"
)

data class PurchaseRequestResponse(
    @Json(name = "success") val success: Boolean,
    @Json(name = "reference") val reference: String? = null,
    @Json(name = "status") val status: String? = null,
    @Json(name = "error") val error: String? = null
)

interface TinatApiService {
    @POST("api/v1/android/activate")
    suspend fun activatePurchase(
        @Body request: ActivateRequest
    ): Response<ActivateResponse>

    @GET("api/v1/android/entitlement")
    suspend fun getEntitlement(
        @Header("Authorization") authHeader: String
    ): Response<EntitlementResponse>

    @POST("api/v1/android/purchase-request")
    suspend fun createPurchaseRequest(
        @Body request: PurchaseRequestDto
    ): Response<PurchaseRequestResponse>
}

object TinatApiClient {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Note: Logging interceptor set to NONE or BASIC without logging sensitive tokens or phones
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val apiService: TinatApiService by lazy {
        Retrofit.Builder()
            .baseUrl(TinatNetworkConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TinatApiService::class.java)
    }

    fun parseErrorCode(errorBodyString: String?): String? {
        if (errorBodyString.isNullOrEmpty()) return null
        return try {
            val json = JSONObject(errorBodyString)
            json.optString("error", null)
        } catch (e: Exception) {
            null
        }
    }

    fun mapErrorCodeToUserMessage(code: String?, statusCode: Int): String {
        return when (code) {
            "INVALID_PHONE" -> "Please enter a valid phone number (9-15 digits)."
            "INVALID_CODE" -> "This code is not recognised."
            "PHONE_MISMATCH" -> "This code belongs to a different phone number."
            "PHONE_NOT_VERIFIED" -> "Your phone number is not verified on Telegram."
            "PAYMENT_NOT_APPROVED" -> "Your purchase has not been approved yet."
            "ALREADY_REDEEMED" -> "This code has already been redeemed."
            "VOUCHER_REVOKED" -> "This code has been revoked."
            "NOT_ASSIGNED" -> "This code is not assigned yet."
            "PACKAGE_INVALID" -> "Invalid package selection."
            "RATE_LIMITED" -> "Too many attempts. Try again in a few minutes."
            "BAD_REQUEST" -> "Malformed request. Please check your phone number and code."
            else -> {
                if (statusCode == 429) "Too many attempts. Try again in a few minutes."
                else "Check your connection and try again."
            }
        }
    }
}
