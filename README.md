---
highlight: an-old-hope theme: channing-cyan
---

# EAnimator

EAnimator[![](https://jitpack.io/v/RickyHal/EAnimator.svg)](https://jitpack.io/#RickyHal/EAnimator)
是一个使用kotlin开发的动画框架，适用于复杂情况下的动画实现，让您在实现App中的动画时如写作文一般清爽。[Blog](https://juejin.cn/post/7027114415228977165/)
。<a href="https://github.com/RickyHal/EAnimator/tree/main/results/apk">Demo下载</a>


<img src="/results/img2.png"/>

- 效果预览

<img src="/results/img1.png" width=260/>

- 目前支持
- [x] 基础动画
- [x] 自定义类型动画
- [x] 路径动画
- [x] 颜色动画
- [x] 多个View复用同一动画
- [x] 不同动画按顺序执行
- [x] 【自动+手动】取消动画

# 依赖配置

项目 build.gradle

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://www.jitpack.io' }
    }
}
```

模块 build.gradle

```groovy
dependencies {
    implementation 'com.github.RickyHal:EAnimator:$latest_version'
}
```

# 使用方法

### 同一View按顺序执行动画

举个缩放X的🌰

```kotlin
view.animate {
    pivot(0.5f, 0.5f)  // 设置缩放中心，注意这里使用的是百分比
    scaleX(1f, 1.3f, 1.2f, 1.5f, 1.3f)  // 先缩放到1.3倍
    scaleXTo(1f)  // 再缩放至1f
}.start()
```

### 同一View同时执行多个动画

```kotlin
view.animate {
    // 缩放的同时改变透明度
    scaleX(1f, 1.3f, 1.2f, 1.5f, 1.3f) + alpha(1f, 0.8f, 0.5f, 0.7f, 0.9f)
    scaleXTo(1f) + alpha(1f)
}.start()
```

没错，直接使用加号加起来就行了。

### 多个View一起执行动画

```kotlin
listOf(view1, view2, view3).animate {
    scaleX(1f, 1.3f, 1.2f, 1.5f, 1.3f)
    scaleXTo(1f)
}.start()
```

### 不同的View按顺序执行动画

```kotlin
val animation1 = binding.animateView1.animate {
    scaleTo(0.5f) {
        withDuration(200)
    }
    scaleTo(1f) {
        withDuration(200)
    }
}
val animation2 = binding.animateView2.animate {
    withGlobalDuration(3000L)
    translationXTo(200f)
    translationXTo(0f)
}
val animation3 = binding.animateView3.animate {
    withGlobalDuration(3000L)
    textColorTo(Color.RED)
    textColorTo(Color.WHITE)
}
// animation1执行完执行animation2，animation2执行完执行animation3
animation1.next(animation2).next(animation3)
// 只需启动第一个动画即可
animation1.start()
```

### 设置单个动画属性

```kotlin
view.animate {
    scaleX(1f, 1.3f, 1.2f, 1.5f, 1.3f)
    scaleXTo(1f) {
        withDuration(300L)  // 执行时间
        withRepeatCount(1)  // 重复次数
        withRepeatMode(ValueAnimator.RESTART)  // 重复方式
        withInterpolator(DecelerateInterpolator())  // 插值器
    }
}.start()
```

### 设置全局动画属性

```kotlin
view.animate {
    withGlobalDuration(1000L)   // 所有动画执行完花费的时间
    withGlobalInterpolator(DecelerateInterpolator())    // 所有动画的插值器
    scaleX(1f, 1.3f, 1.2f, 1.5f, 1.3f)
    scaleXTo(1f)
}.start()
```

全局属性的优先级大于单个动画的属性，会覆盖掉已经设置的单个动画属性。

### 监听单个动画执行

```kotlin
view.animate {
    scaleX(1f, 1.3f, 1.2f, 1.5f, 1.3f)
    scaleXTo(1f) {
        onStart {
            // 开始
        }
        onEnd {
            // 结束
        }
        onRepeat {
            // 重新执行
        }
        onCancel {
            // 取消
        }
    }
}.start()
```

### 监听全局动画

```kotlin
view.animate {
    onGlobalStart {
        // 执行第一个动画时触发
    }
    onGlobalCancel {
        // 动画被取消
    }
    onGlobalEnd {
        // 所有动画执行完毕
    }
    scaleX(1f, 1.3f, 1.2f, 1.5f, 1.3f)
    scaleXTo(1f)
}.start()
```

### 自定义动画

```kotlin
val alpha = object : Property<View, Float>(Float::class.java, "alpha") {
    override fun get(view: View): Float {
        return view.alpha
    }

    override fun set(view: View, value: Float) {
        view.alpha = value
    }
}

view.animate {
    withGlobalDuration(2000L)
    custom(alpha, 1f, 0.5f, 0.3f)
    customTo(alpha, 1f)
}.start()
```

### 路径动画

```kotlin
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
```

### 取消动画

view在detach的时候会自动取消动画，当然你也可以手动取消动画

```kotlin
val aniamtor = view.animate {
    scaleX(1f, 1.3f, 1.2f, 1.5f, 1.3f)
    scaleXTo(1f)
}
aniamtor.start()
// delay ...
aniamtor.cancel()
```

## License

> ```
> Copyright 2021 RickyHal
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
>
>    http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
> ```