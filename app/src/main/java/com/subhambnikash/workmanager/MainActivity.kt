package com.subhambnikash.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    lateinit var textView: TextView

    companion object{
        const val inputDataKey="inputDataKey"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button=findViewById(R.id.button)
        textView=findViewById(R.id.textView)

        button.setOnClickListener {
          //  startOneTimeWorker()
            periodicWorkRequest()
        }
    }

    private fun startOneTimeWorker() {
        val workInstance=WorkManager.getInstance(applicationContext)
        val constraints=Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()
        val data=Data.Builder()
            .putInt(inputDataKey,123)
            .build()

        val oneTimeWorkUploadWork= OneTimeWorkRequest.Builder(UploadWork::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()
        val filterWork=OneTimeWorkRequest.Builder(FilterWork::class.java).build()
        val compressWork=OneTimeWorkRequest.Builder(compressWork::class.java).build()
        val downloadWork=OneTimeWorkRequest.Builder(DownloadWork::class.java).build()

        val parallelWork= mutableListOf<OneTimeWorkRequest>()
            parallelWork.add(downloadWork)
            parallelWork.add(filterWork)

        workInstance.beginWith(parallelWork).then(compressWork).then(oneTimeWorkUploadWork).enqueue()





        workInstance.getWorkInfoByIdLiveData(oneTimeWorkUploadWork.id).observe(this){
            textView.text=it.state.name
            if (it.state.isFinished){
                val outPutDatasource=it.outputData
                val message=outPutDatasource.getString(UploadWork.outPutData)
                Toast.makeText(this,message.toString(),Toast.LENGTH_SHORT).show()
            }
        }


    }


    private fun periodicWorkRequest(){
        val workInstance=WorkManager.getInstance(applicationContext)
        val downloadPeriodic=PeriodicWorkRequest.Builder(DownloadWork::class.java,16, TimeUnit.MINUTES).build()
        workInstance.enqueue(downloadPeriodic)


    }



}