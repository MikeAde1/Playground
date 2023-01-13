package com.example.workmanagerapp

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.RxWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random

class WorkerClass(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {

    override fun doWork(): Result {
        // Your work here.
        val randomWorkResult = Random.nextInt(3)
        return if (randomWorkResult < 2) {
            Log.d("result##", "Success")
            Result.success()
        } else {
            Log.d("result##", "Failure")
            Result.retry()
        }
    }
}