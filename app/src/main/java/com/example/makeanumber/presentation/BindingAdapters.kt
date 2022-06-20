package com.example.makeanumber.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.makeanumber.R
import com.example.makeanumber.domain.entity.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("scoreAnswers")
fun bindAnswersScore(textView: TextView, score: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        score
    )
}

@BindingAdapter("requiredPercent")
fun bindRequiredPercentage(textView: TextView, minPercent: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        minPercent
    )
}

@BindingAdapter("scorePercent")
fun bindPercentageScore(textView: TextView, gameResult: GameResult) {
    val score = gameResult.countOfRightAnswers
    val allAnswers = gameResult.countofQuestionsAnswered
    val percentage =
        (score / allAnswers.toDouble() * 100).toInt()
    textView.text =
        String.format(textView.context.getString(R.string.score_percentage), percentage)
}

@BindingAdapter("emojiResult")
fun bindPicture(imageView: ImageView, win: Boolean) {
    if (win) {
        imageView.setImageResource(R.drawable.happy_robot)
    } else {
        imageView.setImageResource(R.drawable.sad_robot)
    }
}

@BindingAdapter("resultText")
fun bindResultText(textView: TextView, win:Boolean) {
    if (win) {
        textView.text = textView.context.getString(R.string.game_result_win)
    } else {
        textView.text = textView.context.getString(R.string.game_result_lose)
    }
    textView.setTextColor(chooseColor(textView.context, win))
}

private fun chooseColor(context: Context, isWin: Boolean): Int {
    val colorResId = if (isWin) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("progressBarColorLiveData")
fun bindProgressBarColorLiveData(progressBar: ProgressBar, enough: Boolean) {
    val color = chooseColor(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("progressBarPercentRightAnswersLiveData")
fun bindProgressBarPercentRightAnswersLiveData(progressBar: ProgressBar, rightAnswersPercent: Int) {
    progressBar.setProgress(rightAnswersPercent, true)
}

@BindingAdapter("enoughAnswersLiveData")
fun bindEnoughAnswersLiveData(textView: TextView, enough:Boolean) {
    textView.setTextColor(chooseColor(textView.context, enough))
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

interface OnOptionClickListener {
    fun onOptionClick(option: Int)
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}