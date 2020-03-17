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

Or use the provided [MarkyMarkView](markymark-android/src/main/java/com/m2mobi/markymarkandroid/MarkyMarkView.java) to do it for you.
```kotlin
val markyMarkView = findViewById<MarkyMarkView>(R.id.markymarkview)
markyMarkView.setMarkyMark(markyMark)
markyMarkView.setMarkdown("# Header\nParagraph etc")
```

## Styling

To style your Markdown content you can override MarkyMark styles where necessary.

```xml
<!-- Base application theme. -->
<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
    <!-- Customize your theme here. -->
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent">@color/colorAccent</item>

    <item name="MarkyMarkTheme">@style/MarkdownStyle</item>  
 </style>
    
 <!-- Theme used by MarkyMark -->
 <style name="MarkdownStyle" parent="MarkyMarkStyle">
    <item name="android:lineSpacingExtra">4dp</item>
    <item name="android:lineSpacingMultiplier">1</item>
    <item name="MarkdownHeader4Style">@style/Header4</item>
</style>

<!-- Different color for H4 tags -->
<style name="Header4" parent="MarkyMarkHeader4">
    <item name="android:textColor">#52c222</item>
</style>
```

### Available parent styles

- `MarkyMarkCode`
- `MarkyMarkHeader`
- `MarkyMarkHeader1`
- `MarkyMarkHeader2`
- `MarkyMarkHeader3`
- `MarkyMarkHeader4`
- `MarkyMarkHeader5`
- `MarkyMarkHeader6`
- `MarkyMarkHeaderHorizontalRule` // horizontal lines
- `MarkyMarkImage`
- `MarkyMarkList`
- `MarkyMarkParagraph`
- `MarkyMarkQuote`

If you want more customization it is also possible to provide your own custom inline/block items, or replace existing ones. More on how you can use these in **Advanced Usage** found below

```kotlin
val themedContext = ThemedContext(activity)

// These are some of the default ones, but you get the point
val displayItems = mutableListOf<DisplayItem<*,*,*>>().apply {
	add(HeaderDisplayItem(themedContext))
	add(ParagraphDisplayItem(themedContext))
}
val inlineDisplayItems = mutableListOf<InlineDisplayItem<*,*>>().apply {
	add(BoldInlineDisplayItem())
}
val markyMark = MarkyMarkAndroid.getMarkyMark(
                activity,
                ContentfulFlavor(),
                displayItems,
                inlineDisplayItems,
                PicassoImageLoader()
)
```

### Loading images

You can use your favorite image loading library to load images. When creating a MarkyMark android instance an implementation of [ImageLoader](markymark-android/src/main/java/com/m2mobi/markymarkandroid/ImageLoader.java) needs to be provided, an example using [Picasso](https://github.com/square/picasso) can be found [here](markymark-sample/src/main/java/com/m2mobi/markymark/PicassoImageLoader.kt).

# Advanced usage
---

## Adding your own rules

As of now only a `ContentfulFlavor` is included, however it is entirely possible to create your own Markdown flavor
and/or corresponding rules.

Adding a rule requires these steps

### Extend the marker interface `MarkdownItem`

Create a `MarkdownItem` from which you can create a `View` later, so you'll want to have every piece of information needed in order to create said `View`

```kotlin
data class NewMarkdownItem(val content: String) : MarkdownItem
```

### Extend `DisplayItem<View, NewMarkdownItem, Spanned>`

Create a `DisplayItem` that can handle your `NewMarkdownItem` and convert it into a `View`

```kotlin
class NewDisplayItem(val context: Context) : DisplayItem<View, NewMarkdownItem, Spanned> {

	override fun create(markdownItem: NewMarkdownItem, inlineConverter: InlineConverter<Spanned>) : View {
		return TextView(context).apply {
			text = inlineConverter.convert(markdownItem.content)
		}
	}
}
```

Now add this item to your `ViewConverter` before you initialize `MarkyMark` like shown at the top of this `README`

```java
viewConverter.addMapping(NewDisplayItem(themedContext))
```

### Extend `Rule`

Create a `Rule` that recognizes your new item and creates a corresponding `MarkdownItem` for it.
Most new rules will just be single line, like headers, in that case your new rule can just extend `RegexRule`.
Return your regular expression `Pattern` in the `getRegex()` method and return your new `MarkdownItem` in the `toMarkdownItem(markdownLines: List<String>)`

```kotlin
class NewRule : RegexRule {

	override fun getRegex() : Pattern = Pattern.compile("some regex")
	
	override fun toMarkdownItem(markdownLines: List<String>) : MarkdownItem {
	    // In this case, since it is a single line rule
	    // markdownLines will always be an list with one String
	    return NewMarkdownItem(markdownLines.first())
	}
}
```

### Adding the new rule to `MarkyMark`

You can add a new rule to your `MarkyMark` instance like this

```kotlin
// adding rule to MarkyMark instance
markyMark.addRule(NewRule())
```

Or create a new `Flavor` altogether

```kotlin
class OtherFlavor : Flavor {

	override fun getRules() : List<Rule> {
		return mutableListOf<Rule>().apply {
			// add all the rules
		}
	}

	override fun getInlineRules() : List<InlineRule> {
		return mutableListOf<InlineRule>().apply {
			// Add all the rules
		}
	}

	override fun getDefaultRule() = NewDefaultRule()
}
```

And pass it in the `MarkyMark.Builder()`

```kotlin
MarkyMark.Builder<View>().addFlavor(OtherFlavor()) // etc
```

### Multi line blocks

For more complicated `Rules` that can detect multi-line blocks you'll want to extend `Rule` and you have override the `conforms(final List<String> pMarkdownLines)` method where you would return `true` if the **first** line is recognized as the start of your block, and false otherwise. However there is a catch, you have to set a global integer with the amount of lines there are in this block, which you have to return in the `getLinesConsumed()` method. This means that you have to count the amount of lines that belong to your block inside the `conforms()` method, which isn't desirable and should be refactored as soon as possible.

```kotlin
class NewRule : Rule {

    /** Start pattern of the block */
    private val startPattern = Pattern.compile("some regex")
    
    /** End pattern of the block */
    private val endPattern = Pattern.compile("some regex")
    
    /** The amount of lines of the block */
    private var lines : Int = 0
    
    override fun getLinesConsumed(): Int = lines

    override fun conforms(markdownLines: MutableList<String>): Boolean {
        if (!startPattern.matcher(markdownLines.first()).matches()) {
            return false
        }
        lines = 0
        for (line in markdownLines) {
            lines += 1
            if (endPattern.matcher(line).matches()) {
                return true
            }
        }
        return false
    }

    override fun toMarkdownItem(markdownLines: MutableList<String>): MarkdownItem = SomeMarkdownItem()
}
```

### Inline rules

For detecting inline Markdown, like **bold** or *italic* strings, instead of extending `RegexRule` or `Rule` just extend `InlineRule` and return a `MarkdownString` instead of a `MarkdownItem`.

For example, a rule that would match %%some text%% would look like this

```kotlin
class PercentRule : InlineRule {

    override fun getRegex() : Pattern = Pattern.compile("%{2}(.+?)-{2}")

    override fun toMarkdownString(content: String) = PercentString(content, true)
}
```

Where `PercentString` would be an extension of `MarkdownString`

```kotlin
class PercentString(content: String, canHasChildItems: Boolean) : MarkdownString(content, canHasChildItems)
```

For inline Markdown, instead of extending `DisplayItem<View, Foo, Spanned>` you'd want to extend `InlineDisplayItem<Spanned, PercentString>`

```kotlin
class PercentInlineDisplayItem : InlineDisplayItem<Spanned, PercentString> {

    override fun create(inlineConverter: InlineConverter<Spanned>, markdownString: PercentString): Spanned {
        // return your Spannable String here
    }
}
```

And add them to `MarkyMark`s `InlineConverter<Spanned>` as explained above

```kotlin
inlineViewConverter.addMapping(PercentInlineDisplayItem())
```


## Supported tags in Contentful Flavour

```
# Headers
---

# H1
## H2
### H3
#### H4
##### H5
###### H6
### __*bold & italic*__

# Lists
---

## Ordered list
1. Number 1
2. Number 2
3. Number 3
5. Number 4
  1. Nested 1
  2. Nested 2
6. Number 5 click [here](https://m2mobi.com)
7. Number 5

## Unordered list
- Item 1
- Item 2
- Item 3
  - Nested item 1
  - Nested item 2
    - Sub-nested item 1
    - Sub-nested item 2
      - Sub-sub-nested item 1
      - Sub-sub-nested item 2
       - Sub-sub-nested item 3 (with single space) and a very long piece of text

## Combo

1. Ordered 1
2. Ordered 2
- Unordered 1
- Unordered 2
  - __*Nested unordered 1 bold*__
  - Nested unordered 2
    1. Sub-nested ordered 1
    2. Sub-nested ordered 2
    - unordered
    - unordered

# Paragraphs

---
## Quotes
> Markdown is *awesome*
> Seriously..

## Links
[This is a test link](https://m2mobi.com)
Inline links are also possible, click [here](https://m2mobi.com)
Phone numbers as well [+06-12345678](tel:06-12345678)

## Code

`inline code`
```code block```

## Styled text
This is __bold__, this is *italic*, this is ~~striked out~~, this is everything __~~*combined*~~__.
Special html symbols: `&euro; &copy;` become -> &euro; &copy;

## Images
---

`![Alternate text](www.imageurl.com)`

```

# Download
---

Add the [Jitpack.io](www.jitpack.io) repository to your project root `build.gradle` file

```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

Android MarkyMark with Contentful support
```groovy
compile 'com.github.m2mobi.MarkyMark-Android:markymark-android:0.2.2' 
compile 'com.github.m2mobi.MarkyMark-Android:markymark-contentful:0.2.2' 
```

If you want to use MarkyMark outside of a Android project you might be interested in these pure Java modules

```groovy
// Base
compile 'com.github.m2mobi.MarkyMark-Android:markymark-core:0.2.2' 
// Commons
compile 'com.github.m2mobi.MarkyMark-Android:markymark-commons:0.2.2' 
```

From which you can create `MarkyMark` like so

```kotlin
val viewConverter = Converter<View>().apply {
	addMapping(SomeItem())
}
val inlineConverter = InlineConverter<Spanned>().apply {
        addMapping(SomeInlineItem())
}

val markyMark = MarkyMark.Builder<View>()
	.addFlavor(SomeFlavor())
	.setConverter(viewConverter)
	.setInlineConverter(inlineConverter)
	.build()
```

# Contributions
---

Contributions are encouraged! Create a PR against the Development branch and always run the tests before doing so.
As of now only the `markymark-contentful` module has tests.

## Starting points for contributions

### Rule parsing

Like mentioned in **Advanced Usage**, the way rules are implemented now is:
- Pass a list of `String`s to an rule
- The rule checks whether the first string conforms to that particular `MarkdownItem`
- The rule also counts (if the first `String` conforms) how many lines belong to the item
- The parser asks the rule how many lines belong to the item
- The parser passes those lines to the rule to create the `MarkdownItem`
- The parser removes those lines from the original list of `Strings`
- Repeat

Like previously mentioned, the rule counts the amount of lines needed for its item inside the `conforms()` method, something that is 
pretty unexpected when you aren't familiar with the code base. This process needs refactoring.
  
### Table support

Implement support for tables.

# Author
---

M2mobi, info@m2mobi.com

# License
---

```
The MIT License (MIT)

Copyright (c) 2016-2018 M2mobi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
