package com.ricky.eanimator.demo

import android.content.res.Resources
import android.graphics.Color
import android.graphics.Path
import android.graphics.RectF
import android.os.Bundle
import android.util.Property
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ricky.eanimator.*
import com.ricky.eanimator.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var translationX: EAnimator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnCancel.setOnClickListener { translationX?.cancel() }
        binding.btnMultiView.bindTest(::performMultiView)
        binding.btnMultiViewAndAnimation.bindTest(::performMultiViewAndAnimation)
        binding.btnMultiAnimation.bindTest(::performMultiAnimation)
        binding.btnScaleX.bindTest(::performScaleX)
        binding.btnScaleY.bindTest(::performScaleY)
        binding.btnScale.bindTest(::performScale)
        binding.btnAlpha.bindTest(::performAlpha)
        binding.btnBackgroundColor.bindTest(::performBackgroundColor)
        binding.btnTextColor.bindTest(::performTextColor)
        binding.btnTranslationX.bindTest(::performTranslationX)
        binding.btnTranslationY.bindTest(::performTranslationY)
        binding.btnTranslationZ.bindTest(::performTranslationZ)
        binding.btnRotationX.bindTest(::performRotationX)
        binding.btnRotationY.bindTest(::performRotationY)
        binding.btnRotation.bindTest(::performRotation)
        binding.btnWidth.bindTest(::performWidth)
        binding.btnHeight.bindTest(::performHeight)
        binding.btnMarginLeft.bindTest(::performMarginLeft)
        binding.btnMarginTop.bindTest(::performMarginTop)
        binding.btnMarginRight.bindTest(::performMarginRight)
        binding.btnMarginBottom.bindTest(::performMarginBottom)
        binding.btnPath.bindTest(::performPath)
        binding.btnCustom.bindTest(::performCustom)
    }

    override fun onDestroy() {
        translationX?.cancel()
        super.onDestroy()
    }

    private val Float.dp: Float get() = this * Resources.getSystem().displayMetrics.density + 0.5f

    private fun View.bindTest(func: () -> Unit) {
        setOnClickListener {
            func.invoke()
        }
    }

    private fun performMultiView() {
        listOf(binding.animateView1, binding.animateView2).animate {
            scaleTo(0.5f) {
                withDuration(200)
            }
            scaleTo(1f) {
                withDuration(200)
            }
        }.start()
    }

    private fun performMultiViewAndAnimation() {
        val animation1 = binding.animateView1.animate {
            scaleTo(0.5f) {
                withDuration(200)
            }
            scaleTo(1f) {
                withDuration(200)
            }
        }
        val animation2 = binding.animateView2.animate {
            withGlobalDuration(1000L)
            translationXTo(200f)
            translationXTo(0f)
        }
        val animation3 = binding.animateView3.animate {
            withGlobalDuration(1000L)
            textColorTo(Color.RED)
            textColorTo(Color.WHITE)
        }
        // 使用next连接起来即可
        animation1.next(animation2).next(animation3)
        // 只需启动第一个动画即可
        animation1.start()
    }

    private fun performMultiAnimation() {
        binding.animateView1.animate {
            withGlobalDuration(500L)
            val scaleX = binding.animateView1.scaleX
            val scaleY = binding.animateView1.scaleY
            scaleX(scaleX, 0.5f) + scaleY(scaleY, 0.5f) + alphaTo(0.5f)
            scaleXTo(1f) + scaleYTo(1f) + alphaTo(1f)
        }.start()
    }

    private fun performScaleX() {
        binding.animateView1.animate {
            withGlobalDuration(1000)
            scaleX(1f, 1.3f, 1.2f, 1.5f, 1.3f)
            scaleXTo(1f)
        }.start()
    }

    private fun performScaleY() {
        binding.animateView1.animate {
            scaleY(binding.animateView1.scaleX, 0.5f) {
                withDuration(200)
            }
            scaleYTo(1f) {
                withDuration(200)
            }
        }.start()
    }

    private fun performScale() {
        binding.animateView1.animate {
            pivot(java.util.Random().nextInt(10) / 10f, java.util.Random().nextInt(10) / 10f)
            scale(binding.animateView1.scaleX, 0.5f) {
                withDuration(200)
            }
            scaleTo(1f) {
                withDuration(200)
            }
        }.start()
    }

    private fun performAlpha() {
        binding.animateView1.animate {
            withGlobalDuration(1200L)
            alpha(1f, 0.5f, 0.3f)
            alphaTo(1f)
        }.start()
    }

    private fun performBackgroundColor() {
        binding.animateView1.animate {
            withGlobalDuration(3000L)
            backgroundColor(Color.WHITE, Color.CYAN)
            backgroundColorTo(Color.RED)
            backgroundColor("#FFFF0000", "#FF888888")
            backgroundColorTo("#FFFFFF")
        }.start()
    }

    private fun performTextColor() {
        binding.animateView3.animate {
            withGlobalDuration(3000L)
            textColor(Color.WHITE, Color.CYAN)
            textColorTo(Color.RED)
            textColor("#FFFF0000", "#FF888888")
            textColorTo("#FFFFFF")
        }.start()
    }

    private fun performTranslationX() {
        translationX = binding.animateView1.animate {
            withGlobalDuration(5000L)
            onGlobalStart {
                Toast.makeText(this@MainActivity, "onGlobalStart", Toast.LENGTH_SHORT).show()
            }
            onGlobalCancel {
                Toast.makeText(this@MainActivity, "onGlobalCancel", Toast.LENGTH_SHORT).show()
            }
            onGlobalEnd {
                Toast.makeText(this@MainActivity, "onGlobalEnd", Toast.LENGTH_SHORT).show()
            }
            translationX(0f, 100f) {
                onCancel {
                    Toast.makeText(this@MainActivity, "onCancel", Toast.LENGTH_SHORT).show()
                }
            }
            translationXTo(0f)
        }
        translationX?.start()
    }

    private fun performTranslationY() {
        binding.animateView1.animate {
            withGlobalDuration(1200L)
            translationY(0f, 100f)
            translationYTo(0f)
        }.start()
    }

    private fun performTranslationZ() {
        binding.animateView1.animate {
            withGlobalDuration(1200L)
            translationZ(0f, 100f)
            translationZTo(0f)
        }.start()
    }

    private fun performRotationX() {
        binding.animateView1.animate {
            withGlobalDuration(1200L)
            rotationX(0f, 45f, -45f)
            rotationXTo(0f)
        }.start()
    }

    private fun performRotationY() {
        binding.animateView1.animate {
            withGlobalDuration(1200L)
            rotationY(0f, 45f, -45f)
            rotationYTo(0f)
        }.start()
    }

    private fun performRotation() {
        binding.animateView1.animate {
            withGlobalDuration(900L)
            rotation(0f, 45f)
            rotationTo(0f)
        }.start()
    }

    private fun performWidth() {
        binding.animateView1.animate {
            withGlobalDuration(900L)
            width(50f.dp, 100f.dp)
            widthTo(50f.dp)
        }.start()
    }

    private fun performHeight() {
        binding.animateView1.animate {
            withGlobalDuration(900L)
            height(50f.dp, 100f.dp)
            heightTo(50f.dp)
        }.start()
    }

    private fun performMarginLeft() {
        binding.animateView1.animate {
            withGlobalDuration(900L)
            marginLeft(0f, 100f.dp)
            marginLeftTo(0f.dp)
        }.start()
    }

    private fun performMarginTop() {
        binding.animateView1.animate {
            withGlobalDuration(900L)
            marginTop(0f, 100f.dp)
            marginTopTo(0f.dp)
        }.start()
    }

    private fun performMarginRight() {
        binding.animateView1.animate {
            withGlobalDuration(900L)
            marginRight(0f, 100f.dp)
            marginRightTo(0f.dp)
        }.start()
    }

    private fun performMarginBottom() {
        binding.animateView1.animate {
            withGlobalDuration(900L)
            marginBottom(0f, 100f.dp)
            marginBottomTo(0f.dp)
        }.start()
    }

    private fun performPath() {
        val path = Path()
        path.lineTo(100f, 0f)
        path.lineTo(100f, 100f)
        path.lineTo(0f, 100f)
        path.lineTo(0f, 0f)
        path.arcTo(RectF(0f, 0f, 100f, 100f), -90f, 180f)
        path.lineTo(0f, 0f)
        binding.animateView1.animate {
            withGlobalDuration(3000L)
            path(path)
        }.start()
    }

    private fun performCustom() {
        val alpha = object : Property<View, Float>(Float::class.java, "alpha") {
            override fun get(view: View): Float {
                return view.alpha
            }

            override fun set(view: View, value: Float) {
                view.alpha = value
            }
        }

        binding.animateView1.animate {
            withGlobalDuration(2000L)
            custom(alpha, 1f, 0.5f, 0.3f)
            customTo(alpha, 1f)
        }.start()
    }
}