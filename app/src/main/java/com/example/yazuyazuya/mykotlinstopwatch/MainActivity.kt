package com.example.yazuyazuya.mykotlinstopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val handler = Handler()
    var timeValue = 0
    var tapCount = 0

    var startBtn = true
    var stopBtn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // View要素を変数に代入
        val timeText = findViewById(R.id.timeText) as TextView
        val startButton = findViewById(R.id.start) as Button
        val stopButton = findViewById(R.id.stop) as Button
        val resetButton = findViewById(R.id.reset) as Button

        var assistText = findViewById(R.id.assistText) as TextView

        // 1秒ごとに処理を実行
        val runnable = object : Runnable {
            override fun run() {
                timeValue++

                timeToText(timeValue)?.let {
                    timeText.text = it
                }

                handler.postDelayed(this, 10)

            }
        }

        // start
        startButton.setOnClickListener{
            tapCount = 0
            if (startBtn == true) {
                handler.post(runnable)
                startBtn = false
                stopBtn = true
                assistText.text = "時間を測っています"
            } else {
//                assistText.text = "2回以上押しても意味ないよ(笑)"
            }
            //handler.post(runnable)
        }

        // stop
        stopButton.setOnClickListener {
            tapCount = 0
            if (stopBtn == true) {
                handler.removeCallbacks(runnable)
                stopBtn = false
                startBtn = true
                assistText.text = "一時停止しました"
            } else {
                assistText.text = "2回以上押しても意味ないよ(笑)"
            }
            //handler.removeCallbacks(runnable)
        }

        // reset
        resetButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeValue = 0
            tapCount++
            assistText.text = "リセットしました"
            if (tapCount >= 2) {
                assistText.text = "他のボタン押しなよ(笑)"
            }

            timeToText()?.let {
                timeText.text = it
            }

            if (startBtn == false) {
                startBtn = true
            }

        }

    }

    // 数値を00:00:00形式の文字列に変換する関数
    // 引数timeにはデフォルト値0を設定、返却する型はnullableなString?型
    private fun timeToText(time: Int = 0): String? {
        // if式は値を返すため、そのままreturnできる
        return if (time < 0) {
            null
        } else if (time == 0) {
            "00:00:00.00"
        } else {
            //val h = time / 3600           // delayMills: 1000の時
            //val h = time / 36000
            val h = time / 216000
            //val m = time % 3600 / 60      // delayMills: 1000の時
            //val m = (time / 600) % 60       // delayMills: 100の時
            val m = (time / 3600) % 60      // delayMills: 10の時
            //val s = time % 60             // delayMills: 1000の時
            //val s = (time % 600) / 10     // delayMills: 100の時
            val s = time % 3600 / 60        // delayMills: 10の時
            //val ms = (time / 60) % 100
            //val ms = time % 10
            //val ms = (time / 6) % 10
            val ms = ((time * 10) / 6) % 100  // delayMills: 10の時

            when (s % 10) {
                0 -> {
                    assistText.text = "時間を測っています"
                }
                1 -> {
                    assistText.text = "時間を測っています."
                }
                2 -> {
                    assistText.text = "時間を測っています.."
                }
                3 -> {
                    assistText.text = "時間を測っています..."
                }
                4 -> {
                    assistText.text = "時間を測っています...."
                }
                5 -> {
                    assistText.text = "時間を測っています....."
                }
                6 -> {
                    assistText.text = "時間を測っています...."
                }
                7 -> {
                    assistText.text = "時間を測っています..."
                }
                8 -> {
                    assistText.text = "時間を測っています.."
                }
                9 -> {
                    assistText.text = "時間を測っています."
                }
            }

            //"%1$02d:%2$02d:%3$02d".format(h, m, s)
            //"%1$02d:%2$02d:%3$02d".format(m, s, ms)
            "%1$02d:%2$02d:%3$02d.%4$02d".format(h, m, s, ms)

        }

    }

}
