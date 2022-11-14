package com.example.witt.data.repository

import com.example.witt.data.mapper.toMakePlanResponseModel
import com.example.witt.data.model.plan.get_plan.toGetPlanModel
import com.example.witt.data.model.plan.make_plan.request.MakePlanRequest
import com.example.witt.data.source.remote.plan.get_plan.GetPlanDataSource
import com.example.witt.data.source.remote.plan.get_plan.GetPlanListDataSource
import com.example.witt.data.source.remote.plan.make_plan.MakePlanDataSource
import com.example.witt.domain.model.plan.get_plan.GetPlanListModel
import com.example.witt.domain.model.plan.GetPlanModel
import com.example.witt.domain.model.plan.make_plan.MakePlanModel
import com.example.witt.domain.model.plan.make_plan.MakePlanResponseModel
import com.example.witt.domain.repository.PlanRepository
import javax.inject.Inject

class PlanRepositoryImpl @Inject constructor(
    private val makePlanDataSource: MakePlanDataSource,
    private val getPlanListDataSource: GetPlanListDataSource,
    private val getPlanDataSource: GetPlanDataSource
): PlanRepository{
    override suspend fun makePlan(makePlanModel: MakePlanModel): Result<MakePlanResponseModel> {
        return makePlanDataSource.makePlan(
            MakePlanRequest(
                StartDate = makePlanModel.StartDate,
                EndDate = makePlanModel.EndDate,
                Name = makePlanModel.Name,
                Region = makePlanModel.Region
            )
        ).mapCatching { response ->
            response.toMakePlanResponseModel()
        }
    }

    override suspend fun getPlanList(): Result<List<GetPlanListModel>> {
        return getPlanListDataSource.getPlanList().mapCatching{ response ->
            response.map{
                it.toGetPlanModel()
            }
        }
    }

    override suspend fun getPlan(planId: String): Result<GetPlanModel> {
        return getPlanDataSource.getPlan(planId).mapCatching { response ->
            response.toGetPlanModel()
        }
    }
}
