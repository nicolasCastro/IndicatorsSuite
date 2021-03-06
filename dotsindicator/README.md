<h1 align="center">Dots Indicator</h1></br>

<p align="center">
:red_circle: :white_circle: :large_blue_circle: 
</p>
<p align="center">
  A lightweight dots component, fully customizable.
</p>
<p align="center">
  You might use it as a position indicator, step indicator or loader. 
  Also you could use your own colors, sizes and margins. 
</p>
</br>
<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p> <br>

<p align="center">
  <img src="https://user-images.githubusercontent.com/7327610/110218399-6afc9900-7e98-11eb-81a1-8027d527d623.gif" width="32%"/>
  <img src="https://user-images.githubusercontent.com/7327610/110218401-6f28b680-7e98-11eb-94aa-0ca8a4702612.gif" width="32%"/>
</p>

## How to include it in your project

[![](https://jitpack.io/v/nicolasCastro/dotsIndicator.svg)](https://jitpack.io/#nicolasCastro/dotsIndicator)

### Gradle
Add below snippet to your **root** `build.gradle`.
```Gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
And add a dependency code to your **module**'s `build.gradle` file.
```gradle
dependencies {
    implementation 'com.github.nicolasCastro.IndicatorsSuite:dotsindicator:1.2.X'
}
```

## How to use

You could create and customize a **DotsView** in both ways, in design view and programmatically.

### In XML

```
<com.thinkup.dotsindicator.DotsView
                android:id="@+id/dotsView"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:dotWidth="@dimen/dimen_24"
                app:dotSelectedWidth="@dimen/dimen_24"
                app:dotHeight="@dimen/dimen_24"
                app:dotMargin="@dimen/dimen_8"
                app:dotSelectedColor="@color/red"
                app:dotUnselectedColor="@color/blue"
                app:gradient="false"
                app:gradientSelectedPercentage="100"
                app:gradientNearNextPercentage="80"
                app:gradientNearPrePercentage="70"
                app:gradientFarPercentage="50"
                app:borders="false"
                app:rounded="true"
                app:steps="false" 
                app:loader="false"
                app:loaderRepeatCount="5"
                app:loaderDelay="500"
                app:animationDuration="350"
                app:itemsCount="4"
                app:currentIndex="0" />
```

### Programmatically

```
DotsView.Builder(context)
            .setWidth(R.dimen.dimen_24)
            .setSelectedWidth(R.dimen.dimen_24)
            .setHeight(R.dimen.dimen_24)
            .setMargin(R.dimen.dimen_8)
            .setSelectedResource(R.color.green)
            .setUnselectedResource(R.color.red)
            .setGradient(false)
            .setGradientSelectedPercentage(100)
            .setGradientNearNextPercentage(100)
            .setGradientNearPrePercentage(100)
            .setGradientFarPercentage(100)
            .setBorder(false)
            .setRounded(true)
            .setSteps(false)
            .setLoader(false)
            .setRepeat(3)
            .setLoaderDelay(200)
            .setDuration(200)
            .setStepsCount(4)
            .setCurrentIndex(0)
            .build()
```

## Customizable Parameters

<table>
  <tr><th>Param</th>  <th>In xml</th>   <th>In Builder</th>   <th>Values</th></tr>
  <tr><td>Completed color</td>    <td><code>app:stepCompletedColor</code></td>   <td><code>.setCompletedColor</code></td>   <td>Any color resource</td></tr>
  <tr><td>Uncompleted color</td>      <td><code>app:stepUncompletedColor</code></td>     <td><code>.setUncompletedColor</code></td>     <td>Any color resource</td></tr>
  <tr><td>Uncompleted Text color</td>     <td><code>app:stepTextUncompletedColor</code></td>     <td><code>.setTextCompletedColor</code></td>     <td>Any color resource</td></tr>
  <tr><td>Selected/completed Text color</td>      <td><code>app:stepTextCompletedColor</code></td>     <td><code>.setTextUncompletedColor</code></td>     <td>Any color resource</td></tr>
  <tr><td>Complete Icon resource</td>      <td><code>app:stepIconCompleted</code></td>     <td><code>.setIconCompleted</code></td>     <td>Any drawable resource</td></tr>
  <tr><td>Completed Line color</td>     <td><code>app:stepProgressLineCompletedColor</code></td>     <td><code>.setLineCompletedColor</code></td>     <td>Any color resource</td></tr>
  <tr><td>Unompleted Line color</td>      <td><code>app:stepProgressLineUncompletedColor</code></td>     <td><code>.setLineUncompletedColor</code></td>     <td>Any color resource</td></tr>
  <tr><td>Size</td>     <td><code>app:stepIndicatorSize</code></td>     <td><code>.setSize</code></td>     <td>Any dimen resource</td></tr>
  <tr><td>Selected Size</td>      <td><code>app:stepSelectedIndicatorSize</code></td>     <td><code>.setSelectedSize</code></td>     <td>Any dimen resource</td></tr>
  <tr><td>Line Thickness</td>     <td><code>app:stepProgressLineThickness</code></td>     <td><code>.setLineThickness</code></td>     <td>Any dimen resource</td></tr>
  <tr><td>Is Touchable</td>     <td><code>app:isTouchable</code></td>     <td><code>.setIsTouchable</code></td>     <td>true | false</td></tr>
  <tr><td>Shown Competed Icon</td>    <td><code>app:shownCompetedIcon</code></td>     <td><code>.setShownCompetedIcon</code></td>   <td>true | false</td></tr>
  <tr><td>Duration</td>   <td><code>app:animationDuration</code></td>   <td><code>.setDuration</code></td>    <td>Any integer (> 0)</td></tr>
    <tr><td>Steps count</td>    <td><code>app:stepsCount</code></td>   <td><code>.setStepsCount</code></td>   <td>Any integer (> 0)</td></tr>
    <tr><td>Current Selected Step</td>    <td><code>app:currentStep</code></td>   <td><code>.setCurrentIndex</code></td>      <td>Any integer</td></tr>
</table>


## Add a callback

If you want to perform an action when your **DotsView** moves, you can add a callback calling ```setCallback``` on your view and passing in a **DotsView.Callback** implementation.

```
interface Callback {
    fun onIndexChange(previous: Int, current: Int)
}
```
When the view change the selected index you will receive a notification to the **onIndexChange*** method with the index that has been abandoned and which has been taken.

Also you can passing it when you are using the **DotsView.Builder** calling ```.setCallback```.

## Methods

```.loadItems```: You can change your dot items on runtime calling this method. You only need to pass the total count steps and the selected index after load.

```.loader```: If you want use that like a loader needs call this method after load the view.

```.next```: Move to the next index available (if the last index is selected, no changes were applied).

```.previous```: Move to the previous index available (if the first index is selected, no changes were applied).

```.attach```: If you want to move your **DotsView** along with your **RecyclerView**, you can do so by attaching your RecyclerView by calling this method.

```.selectIndex```: Also if you want change the selected item on runtime might use this method passing the index you want.

