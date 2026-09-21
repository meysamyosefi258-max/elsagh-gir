package com.parsa.elsaghgir

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private data class Group(val phrase: String, val count: Int, val length: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etText = findViewById<EditText>(R.id.etText)
        val tvWordCount = findViewById<TextView>(R.id.tvWordCount)
        val btnAnalyze = findViewById<Button>(R.id.btnAnalyze)
        val tvSummaryTitle = findViewById<TextView>(R.id.tvSummaryTitle)
        val summaryContainer = findViewById<LinearLayout>(R.id.summaryContainer)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        fun toPersianDigits(n: Int): String {
            val fa = "۰۱۲۳۴۵۶۷۸۹"
            return n.toString().map { fa[it - '0'] }.joinToString("")
        }

        fun getWords(): List<String> =
            etText.text.toString().trim()
                .split(Regex("\\s+"))
                .filter { it.isNotEmpty() }

        fun updateWordCount() {
            val n = if (etText.text.toString().isBlank()) 0 else getWords().size
            tvWordCount.text = "${toPersianDigits(n)} کلمه"
        }

        etText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateWordCount()
            }
        })
        updateWordCount()

        fun colorForGroup(i: Int): Pair<Int, Int> {
            val hue = (i * 137.508f) % 360f
            val bg = Color.HSVToColor(90, floatArrayOf(hue, 0.65f, 0.55f))
            val text = Color.HSVToColor(255, floatArrayOf(hue, 0.55f, 0.95f))
            return Pair(bg, text)
        }

        fun findRepeatedGroups(words: List<String>): Pair<List<Group>, IntArray> {
            val n = words.size
            val claimed = BooleanArray(n)
            val highlightGroup = IntArray(n) { -1 }
            val groups = mutableListOf<Group>()

            val maxLen = maxOf(2, minOf(150, n / 2))

            for (len in maxLen downTo 1) {
                val map = HashMap<String, MutableList<Int>>()
                var i = 0
                while (i + len <= n) {
                    var blocked = false
                    for (k in i until i + len) {
                        if (claimed[k]) { blocked = true; break }
                    }
                    if (!blocked) {
                        val key = words.subList(i, i + len).joinToString(" ")
                        map.getOrPut(key) { mutableListOf() }.add(i)
                    }
                    i++
                }
                for ((phrase, positions) in map) {
                    if (positions.size >= 2) {
                        val groupIndex = groups.size
                        for (start in positions) {
                            for (k in start until start + len) {
                                claimed[k] = true
                                highlightGroup[k] = groupIndex
                            }
                        }
                        groups.add(Group(phrase, positions.size, len))
                    }
                }
            }
            return Pair(groups, highlightGroup)
        }

        fun analyze() {
            val words = getWords()
            if (words.size < 4) {
                Toast.makeText(this, "یه متن بلندتر وارد کن", Toast.LENGTH_SHORT).show()
                return
            }
            if (words.size > 8000) {
                Toast.makeText(this, "متن خیلی بزرگه، یه بخش کوچیک‌تر امتحان کن", Toast.LENGTH_SHORT).show()
                return
            }

            btnAnalyze.isEnabled = false
            btnAnalyze.text = "در حال بررسی..."

            Thread {
                val (groups, highlightGroup) = findRepeatedGroups(words)

                val order = groups.indices.sortedWith(
                    compareByDescending<Int> { groups[it].length }.thenByDescending { groups[it].count }
                )

                val builder = SpannableStringBuilder()
                for (i in words.indices) {
                    val start = builder.length
                    builder.append(words[i])
                    val end = builder.length
                    val g = highlightGroup[i]
                    if (g >= 0) {
                        val (bg, text) = colorForGroup(g)
                        builder.setSpan(BackgroundColorSpan(bg), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        builder.setSpan(ForegroundColorSpan(text), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    if (i < words.size - 1) builder.append(" ")
                }

                runOnUiThread {
                    tvSummaryTitle.text = "قسمت‌های تکراری (${toPersianDigits(groups.size)})"
                    summaryContainer.removeAllViews()

                    if (groups.isEmpty()) {
                        val empty = TextView(this).apply {
                            text = "هیچ قسمت تکراری‌ای توی این متن پیدا نشد."
                            setTextColor(resources.getColor(R.color.text_muted, theme))
                            textSize = 13f
                            gravity = Gravity.CENTER
                            setPadding(0, 20, 0, 20)
                        }
                        summaryContainer.addView(empty)
                    } else {
                        for (idx in order) {
                            val g = groups[idx]
                            val (_, textColor) = colorForGroup(idx)

                            val row = LinearLayout(this).apply {
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_VERTICAL
                                setBackgroundResource(R.drawable.bg_field)
                                setPadding(24, 20, 24, 20)
                                layoutParams = LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                ).apply { bottomMargin = 16 }
                            }

                            val swatch = View(this).apply {
                                val size = (12 * resources.displayMetrics.density).toInt()
                                layoutParams = LinearLayout.LayoutParams(size, size).apply { marginEnd = 20 }
                                background = GradientDrawable().apply {
                                    shape = GradientDrawable.OVAL
                                    setColor(textColor)
                                }
                            }
                            row.addView(swatch)

                            val phraseTv = TextView(this).apply {
                                text = g.phrase
                                setTextColor(textColor)
                                textSize = 14f
                                layoutParams = LinearLayout.LayoutParams(
                                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
                                )
                            }
                            row.addView(phraseTv)

                            val countTv = TextView(this).apply {
                                text = "${toPersianDigits(g.count)} بار"
                                setTextColor(resources.getColor(R.color.text_muted, theme))
                                textSize = 12f
                            }
                            row.addView(countTv)

                            summaryContainer.addView(row)
                        }
                    }

                    tvResult.text = builder
                    btnAnalyze.isEnabled = true
                    btnAnalyze.text = "پیدا کردن قسمت‌های مشترک"
                }
            }.start()
        }

        btnAnalyze.setOnClickListener { analyze() }
    }
}
