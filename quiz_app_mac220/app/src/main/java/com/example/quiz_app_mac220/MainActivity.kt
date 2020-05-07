package com.example.quiz_app_mac220

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils.lastIndexOf
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import pl.droidsonroids.gif.GifImageView
import kotlin.math.roundToInt

private const val Tag = "MainActivity"
private const val quizGame = "Page"
private const val Key = "value"
private var right = 0
private var wrong = 0


class MainActivity : AppCompatActivity() {
    private lateinit var tBtn: Button
    private lateinit var fBtn: Button
    private lateinit var nBtn: ImageButton
    private lateinit var bBtn: ImageButton
    private lateinit var triviaTextV: TextView
    private var images: GifImageView? = null

    private val quizList = listOf(
        quizQuestions(R.string.q, false),
        quizQuestions(R.string.q2, true),
        quizQuestions(R.string.q3, true),
        quizQuestions(R.string.q4, false),
        quizQuestions(R.string.q5, true)

    )
    private var currentIndex = 0

    var quizArr: IntArray = intArrayOf(quizList.size)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(quizGame, 0)
            quizArr = savedInstanceState.getIntArray(Key)!!
        }
        images = image
        tBtn = trueBtn
        fBtn = falseBtn
        nBtn = nextBtn
        bBtn = backBtn
        triviaTextV = questions_textView

        tBtn.setOnClickListener {
            val ans = checkAnswer(true)
            Toast.makeText(this, ans, Toast.LENGTH_SHORT).show()
            tBtn.isEnabled = false
            fBtn.isEnabled = false
            quizArr += currentIndex
            updateScore(true)
        }
        fBtn.setOnClickListener {
            val ans2 = checkAnswer(false)
            Toast.makeText(this, ans2, Toast.LENGTH_SHORT).show()
            tBtn.isEnabled = false
            fBtn.isEnabled = false
            quizArr+=currentIndex
            updateScore(false)
            }
        nBtn.setOnClickListener {
            Toast.makeText(this, "Here's another question", Toast.LENGTH_SHORT).show()
            currentIndex = (currentIndex + 1) % quizList.size
            tBtn.isEnabled = true
            fBtn.isEnabled = true
            updateQuestion()
            image_change()
        }
        bBtn.setOnClickListener {
            Toast.makeText(this, "Looking back?", Toast.LENGTH_SHORT).show()
            currentIndex = (currentIndex - 1) % quizList.size
            tBtn.isEnabled = true
            fBtn.isEnabled = true
            updateQuestion()
            image_change()
        }
        triviaTextV.setOnClickListener{
            currentIndex = (currentIndex + 1) % quizList.size
            tBtn.isEnabled = false
            fBtn.isEnabled = false
            updateQuestion()
            image_change()
        }
        updateQuestion()
        image_change()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(quizGame, currentIndex)
        savedInstanceState.putIntArray(Key, quizArr)
    }

     private fun updateScore(userInput: Boolean) {
         val correctAnswer = quizList[currentIndex].answer
         //val final_score = (right.toFloat()/quizList.size)*100
         if (userInput == correctAnswer) {
             right++
         } else {
             wrong++
         }
         onScoreUpdated()
     }
    private fun onScoreUpdated(){
         if(right+wrong == quizList.size){
        //val result =
        return Toast.makeText(this, "You were right:  $right You were wrong:  $wrong Your final percentage is: ${calculateFinalScore()} %", Toast.LENGTH_LONG).show()
         }
    }
    private fun calculateFinalScore(): Float = (right.toFloat()/quizList.size)*100
    private fun updateQuestion(){
        val questionId = quizList[currentIndex].textResId
        questions_textView.setText(questionId)
        oneClick()
    }
    private fun checkAnswer(userInput: Boolean):Int{
        val correctAns = quizList[currentIndex].answer
        val messageId = if(userInput == correctAns){
            R.string.toast_true1
        }
        else {
            R.string.toast_false1
        }
        return messageId
    }

    private fun oneClick() {
        if (quizArr.contains(currentIndex)) {
            fBtn.isEnabled = false
            tBtn.isEnabled = false
        } else {
            fBtn.isEnabled = true
            tBtn.isEnabled = true
        }
    }

    private fun image_change(){
        val drawableResource = when (currentIndex){
            0 -> R.drawable.pac_man
            1 -> R.drawable.nintendo
            2 -> R.drawable.final_fantasy
            3 -> R.drawable.super_nintendo
            4-> R.drawable.tetris
            else -> R.drawable.many_games
        }
        images?.setImageResource(drawableResource)
    }
    }



