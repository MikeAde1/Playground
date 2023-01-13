package com.example.workmanagerapp

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random

class WorkerClassWithData(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {

    override fun doWork(): Result {
        // Your work here.
        val randomWorkResult = Random.nextInt(3)
        return if (randomWorkResult < 2) {
            val userId = inputData.getString("key")
            Log.d("result##", "Success: $userId")
            Result.success()
        } else {
            Log.d("result##", "Retry")
            Result.failure()
        }
    }
}