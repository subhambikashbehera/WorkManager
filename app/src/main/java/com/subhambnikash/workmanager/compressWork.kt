package com.subhambnikash.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class compressWork(context: Context, private val parameters: WorkerParameters):Worker(context,parameters) {
    override fun doWork(): Result {
        for (i in 0..100){
            Log.d("downloadwork", "compressWork: $$i")
        }
        return Result.success()
    }


}