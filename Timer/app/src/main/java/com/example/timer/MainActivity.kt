package com.example.timer

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.example.timer.databinding.ActivityMainBinding
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentDeciSecond = 0 //초
    private var timer : Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lapButton.isClickable = false
        binding.lapButton.alpha = 0.4f

        // 랩 버튼 //
        binding.lapButton.setOnClickListener {
            lap()
        }

        // 초기화 버튼 //
        binding.resetButton.setOnClickListener {
            reset()
        }

        // 시작 버튼 //
        binding.startButton.setOnClickListener {
            start()
        }

        // 중지 버튼 //
        binding.stopButton.setOnClickListener {
            stop()
        }

        // 계속 버튼 //
        binding.playButton.setOnClickListener {
            play()
        }
    }

    private fun lap() {
        if (currentDeciSecond == 0){
            binding.lapButton.isClickable = false
            binding.lapButton.alpha = 0.3f
            return
        }

        binding.lapButton.isVisible = true
        binding.stopButton.isVisible = true
        binding.startButton.isVisible = false
        binding.resetButton.isVisible = false
        binding.playButton.isVisible = false

        val container = binding.lapContainerLinearLayout
        TextView(this).apply {
            textSize = 20f
            gravity = Gravity.CENTER
            text = container.childCount.inc().toString() +
                    "랩 : " +
                    String.format("%02d:%02d", currentDeciSecond.div(10) / 60, currentDeciSecond.div(10) % 60) +
                    String.format(".%01d", currentDeciSecond % 10)
            setPadding(30)
        }.let {
            container.addView(it, 0)
        }

    }

    // 시작 //
    private fun start() {
        binding.lapButton.isVisible = true
        binding.stopButton.isVisible = true
        binding.startButton.isVisible = false
        binding.resetButton.isVisible = false
        binding.playButton.isVisible = false

        binding.lapButton.isClickable = true
        binding.lapButton.alpha = 1f

        runTime()
    }

    // 계속 //
    private fun play() {
        binding.lapButton.isVisible = true
        binding.startButton.isVisible = false
        binding.resetButton.isVisible = false
        binding.playButton.isVisible = false
        binding.stopButton.isVisible = true

        runTime()
    }

    private fun stop() {
        binding.playButton.isVisible = true
        binding.resetButton.isVisible = true
        binding.startButton.isVisible = false
        binding.lapButton.isVisible = false
        binding.stopButton.isVisible = false

        timer?.cancel()
        timer = null
    }

    private fun reset() {
        binding.lapButton.isVisible = true
        binding.startButton.isVisible = true
        binding.resetButton.isVisible = false
        binding.playButton.isVisible = false
        binding.stopButton.isVisible = false

        binding.lapButton.isClickable = false
        binding.lapButton.alpha = 0.4f

        currentDeciSecond = 0
        binding.timeTextView.text = "00:00:"
        binding.secTextView.text = "0"

        binding.lapContainerLinearLayout.removeAllViews()
    }

    private fun runTime() {
        timer = timer(initialDelay = 0, period = 100){
            currentDeciSecond += 1

            val minutes = currentDeciSecond.div(10) / 60
            val seconds = currentDeciSecond.div(10) % 60
            val deciSeconds = currentDeciSecond % 10

            runOnUiThread{
                binding.timeTextView.text = String.format("%02d:%02d:", minutes, seconds)
                binding.secTextView.text = deciSeconds.toString()
            }
        }
    }
}