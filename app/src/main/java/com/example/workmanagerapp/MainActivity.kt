package com.example.workmanagerapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.work.*
import com.google.android.material.button.MaterialButton
import java.util.concurrent.TimeUnit
import kotlin.math.hypot

class MainActivity : AppCompatActivity() {
    private lateinit var workManager: WorkManager
    private lateinit var yourWorkRequest: OneTimeWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workManager = WorkManager.getInstance(this)



        /*findViewById<MaterialButton>(R.id.btn1).setOnClickListener {
            yourWorkRequest = OneTimeWorkRequestBuilder<WorkerClass>().build()
            workManager.enqueue(yourWorkRequest)
        }

        findViewById<MaterialButton>(R.id.btn2).setOnClickListener {
            val yourWorkRequest2 = PeriodicWorkRequestBuilder<WorkerClass>(1, TimeUnit.MINUTES).build()
            workManager.enqueue(yourWorkRequest2)
        }

        findViewById<MaterialButton>(R.id.btn3).setOnClickListener {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val userId = workDataOf("key" to "value")
            val yourWorkRequest3 = OneTimeWorkRequestBuilder<WorkerClassWithData>()

                .setInputData(userId)
                .setConstraints(constraints)
                .setInitialDelay(10000, TimeUnit.MILLISECONDS)
                //.setBackoffCriteria(BackoffPolicy.LINEAR, 10000, TimeUnit.MILLISECONDS)
                .build()
            workManager.enqueue(yourWorkRequest3)
        }*/
        //workManager.cancelWorkById(yourWorkRequest.id)


        /*val metrics = DisplayMetrics()
        val view = findViewById<MaterialButton>(R.id.btn1)
        view.setOnClickListener {

            val baseView  = layoutInflater.inflate(R.layout.item_show,
                findViewById(android.R.id.content), false)?.apply {
                (window.decorView.rootView as ViewGroup).addView(this)
            }
            baseView?.setBackgroundColor(Color.parseColor("#CC000000"))
            baseView?.globalLayoutListener {
                baseView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
                *//*ViewAnimationUtils.createCircularReveal(
                    baseView,
                    metrics.widthPixels/2,
                    metrics.heightPixels/2,
                    0f,
                    hypot(baseView.width.toFloat(), baseView.height.toFloat())
                ).apply {

                    duration = 1000.toLong()
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {

                        }
                    })
                    interpolator = AnimationUtils.loadInterpolator(
                        this@MainActivity,
                        android.R.interpolator.accelerate_cubic
                    )
                    start()
                }*//*
            }
        }*/
    }

    internal fun View.globalLayoutListener(onLayout: () -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    viewTreeObserver?.removeGlobalOnLayoutListener(this)
                } else {
                    viewTreeObserver?.removeOnGlobalLayoutListener(this)
                }
                onLayout()
            }
        })
    }
}


