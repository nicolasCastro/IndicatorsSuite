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
  
  <tr><td>Dot width</td>    <td><code>app:dotWidth</code></td>   <td><code>.setWidth</code></td>   <td>Any dimen resource</td></tr>
  <tr><td>Selected dot width</td>    <td><code>app:dotSelectedWidth</code></td>   <td><code>.setSelectedWidth</code></td>   <td>Any dimen resource</td></tr>
  <tr><td>Dot height</td>    <td><code>app:dotHeight</code></td>   <td><code>.setHeight</code></td>   <td>Any dimen resource</td></tr>
  <tr><td>Dot margins</td>    <td><code>app:dotMargin</code></td>   <td><code>.setMargin</code></td>   <td>Any dimen resource</td></tr>

  <tr><td>Selected color</td>    <td><code>app:dotSelectedColor</code></td>   <td><code>.setSelectedResource</code></td>   <td>Any color resource</td></tr>
  <tr><td>Unselected color</td>      <td><code>app:dotUnselectedColor</code></td>     <td><code>.setUnselectedResource</code></td>     <td>Any color resource</td></tr>
  
   <tr><td>Use gradient sequence</td>     <td><code>app:gradient</code></td>     <td><code>.setGradient</code></td>     <td>true | false</td></tr>
   <tr><td>Alpha(%) to select dot</td>     <td><code>app:gradientSelectedPercentage</code></td>     <td><code>.setGradientSelectedPercentage</code></td>     <td>0-100</td></tr>
   <tr><td>Alpha(%) to next dot</td>     <td><code>app:gradientNearNextPercentage</code></td>     <td><code>.setGradientNearNextPercentage</code></td>     <td>0-100</td></tr>
   <tr><td>Alpha(%) to previous dot</td>     <td><code>app:gradientNearPrePercentage</code></td>     <td><code>.setGradientNearPrePercentage</code></td>     <td>0-100</td></tr>
   <tr><td>Alpha(%) to aother dots</td>     <td><code>app:gradientFarPercentage</code></td>     <td><code>.setGradientFarPercentage</code></td>     <td>0-100</td></tr>
   
   
  <tr><td>Shown borndes</td>    <td><code>app:borders</code></td>     <td><code>.setBorder</code></td>   <td>true | false</td></tr>
  <tr><td>Shown rounded dots</td>    <td><code>app:rounded</code></td>     <td><code>.setRounded</code></td>   <td>true | false</td></tr>
  <tr><td>Shown as steps</td>    <td><code>app:steps</code></td>     <td><code>.setSteps</code></td>   <td>true | false</td></tr>
  <tr><td>Shown as loader</td>    <td><code>app:loader</code></td>     <td><code>.setLoader</code></td>   <td>true | false</td></tr>
   
  <tr><td>Repeat loader count</td>   <td><code>app:loaderRepeatCount</code></td>   <td><code>.setRepeat</code></td>    <td>Any integer (> 0)</td></tr> 
  <tr><td>Loader delay(ms)</td>   <td><code>app:loaderDelay</code></td>   <td><code>.setLoaderDelay</code></td>    <td>Any integer (> 0)</td></tr> 
  <tr><td>Duration</td>   <td><code>app:animationDuration</code></td>   <td><code>.setDuration</code></td>    <td>Any integer (> 0)</td></tr>
  <tr><td>Steps count</td>    <td><code>app:itemsCount</code></td>   <td><code>.setStepsCount</code></td>   <td>Any integer (> 0)</td></tr>
  <tr><td>Current selected index</td>    <td><code>app:currentIndex</code></td>   <td><code>.setCurrentIndex</code></td>      <td>Any integer</td></tr>
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

