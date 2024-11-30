package com.project.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
object NetworkUtils {
    suspend fun <T> safeApiCall(
        dispatcher: kotlin.coroutines.CoroutineContext = Dispatchers.IO,
        apiCall: suspend () -> Response<T>
    ): T? {
        return withContext(dispatcher) {
            try {
                val response = apiCall.invoke()
                if(response.isSuccessful){
                    response.body()
                }else{
                    throw RequestException(message = "")
                }
            } catch (e: HttpException) {
                val message = e.response()?.errorBody()?.string() ?: e.message
                throw when (e.code()) {
                    400 -> BadRequestException(message = message)
                    401 -> UnauthorizedException(message = message)
                    403 -> ForbiddenException(message = message)
                    404 -> NotFoundException(message = message)
                    500, 501, 502, 503 -> ServerException(message = message)
                    else -> OtherHttpException(code = e.code(), message = message)
                }
            } catch (e: SocketTimeoutException) {
                throw TimeOutException(message = e.message)
            } catch (e: UnknownHostException) {
               throw UnknownException(message = e.message)
            } catch (e: IOException) {
                throw NetworkIOException(message = e.message)
            } catch (e: Throwable) {
                throw UnknownException(message = e.message ?: "Unknown error")
            }
        }
    }
}