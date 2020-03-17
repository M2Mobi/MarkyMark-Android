[![](https://jitpack.io/v/M2Mobi/MarkyMark-Android.svg)](https://jitpack.io/#M2Mobi/MarkyMark-Android)

# MarkyMark

MarkyMark is a parser that converts markdown into native views. The way it looks is highly customizable and the supported markdown syntax is easy to extend.

## Usage Android

Create an Android instance of MarkyMark that can convert Markdown to View's. A helper class is provided to create an Android instance.

```kotlin
val markyMark = MarkyMarkAndroid.getMarkyMark(this, ContentfulFlavor(), PicassoImageLoader())
```

There are 2 ways to use the MarkyMark instance.

You can use the MarkyMark instance to parse the Markdown into a list of View's and add them to (for example) a LinearLayout:
```kotlin
val linearLayout = findViewById<LinearLayout>(R.id.linearlayout)
val views = markyMark.parseMarkdown("# Header\nParagraph etc")
for (view in views) {
     layout.addView(view)
}
```