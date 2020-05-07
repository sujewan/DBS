package com.sujewan.dbs.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.sujewan.dbs.api.ApiResponse
import com.sujewan.dbs.api.Resource

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
constructor() {
    private val result = MediatorLiveData<Resource<ResultType>>()

    // returns a LiveData that represents the resource
    val asLiveData: LiveData<Resource<ResultType>>
        get() = result

    init {
        result.setValue(Resource.loading(null))
        fetchFromNetwork()
    }

    private fun fetchFromNetwork() {
        val apiResponse = fetchService()

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)

            response?.let {
                when(response.isSuccessful) {
                    true -> {
                        response.body?.let {
                            val loaded = gatherFetchData(it)
                            result.setValue(Resource.success(loaded))
                        }
                    }

                    false -> {
                        onFetchFailed()
                        result.setValue(Resource.error(response.error))
                    }
                }
            }
        }
    }


    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        result.value = newValue
    }

    @WorkerThread
    protected abstract fun gatherFetchData(item: RequestType): ResultType

    @MainThread
    protected abstract fun fetchService(): LiveData<ApiResponse<RequestType>>

    @MainThread
    protected abstract fun onFetchFailed()
}