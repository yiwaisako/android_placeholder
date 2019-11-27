# android_placeholder（作成中）
- Mavenリポジトリお試し

## use

- build.gradle

  - repositories{
    maven { url 'https://github.com/yiwaisako/android_placeholder/raw/master/maven-repository/' }
  }

- app/build.gradle

  - implementation 'jp.co.yiwaisako:android_placeholder:0.0.1'

- Activityで利用する例
```
Placeholder.with(
    this,
    R.drawable.logo,
    R.color.bg_color
)

val wPixel = 100
val hPixel = 100
val placeholder = Placeholder.createPlaceHolderFromPixel(this, wPixel, hPixel)
```

## 参考
https://qiita.com/wa2c/items/8bb67e11cd7ee9146c2e
