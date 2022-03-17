package com.subhambnikash.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class UploadWork(context: Context,private val params:WorkerParameters):Worker(context,params) {

    companion object{
        const val outPutData="outPutData"
    }


    override fun doWork(): Result {
        return try {
            val getData=inputData.getInt(MainActivity.inputDataKey,0)
            for (i in 0..getData){
                Log.d("downloadwork", "uploadWork: $$i")
            }

       val simpleDateFormat=SimpleDateFormat("dd/m/yyyy hh:mm")
       val date=simpleDateFormat.format(Date())

        val outPutData=Data.Builder().putString(outPutData,date).build()


            Result.success(outPutData)
        } catch (e: Exception) {
            Result.failure()
        }
    }
}