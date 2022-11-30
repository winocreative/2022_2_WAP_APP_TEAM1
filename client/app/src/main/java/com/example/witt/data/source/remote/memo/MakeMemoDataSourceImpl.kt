package com.example.witt.data.source.remote.memo

import com.example.witt.data.api.PlanMemoService
import com.example.witt.data.model.plan.detail_plan.request.MemoRequest
import com.example.witt.data.model.plan.memo.MakeMemoResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MakeMemoDataSourceImpl @Inject constructor(
    private val service: PlanMemoService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : MakeMemoDataSource{

    override suspend fun makeMemo(tripId : Int, day: Int, content: String): Result<MakeMemoResponse>
    = withContext(coroutineDispatcher){
        runCatching {
            service.makeMemo(tripId, day, MemoRequest(content))
        }.onFailure {
            it.printStackTrace()
        }
    }
}