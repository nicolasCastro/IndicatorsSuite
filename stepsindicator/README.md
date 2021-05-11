<h1 align="center">Steps Indicator</h1></br>

<p align="center">
:red_circle: :heavy_minus_sign: :white_circle: :heavy_minus_sign: :large_blue_circle: 
</p>
<p align="center">
  A lightweight steps wizard component, fully customizable.
</p>
</br>
<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p> <br>

<p align="center">
  <img src="https://user-images.githubusercontent.com/7327610/110058652-7be6c680-7d41-11eb-975a-467ed2b98fec.gif" width="32%"/>
  <img src="https://user-images.githubusercontent.com/7327610/110058961-17783700-7d42-11eb-8e77-6c7c248e535a.gif" width="32%"/>
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
    implementation 'com.github.nicolasCastro.IndicatorsSuite:stepsindicator:1.2.X'
}
```

## How to use

You could create and customize a **StepView** in both ways, in design view and programmatically.

### In XML

```
<com.thinkup.stepsindicator.StepsView
                android:id="@+id/stepsView"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:currentStep="0"
                app:shownCompetedIcon="true"
                app:stepIconCompleted="@drawable/tkup_completed_step"
                app:stepCompletedColor="@color/colorPrimaryDark"
                app:stepIndicatorSize="32dp"
                app:stepProgressLineCompletedColor="@color/colorPrimaryDark"
                app:stepProgressLineThickness="2dp"
                app:stepProgressLineUncompletedColor="@color/colorAccent"
                app:lineMargins="24dp"
                app:stepSelectedIndicatorSize="48dp"
                app:stepTextCompletedColor="@color/colorAccent"
                app:stepTextUncompletedColor="@color/colorPrimaryDark"
                app:stepUncompletedColor="@color/colorAccent"
                app:stepTextFont="@font/custom_font"
                app:stepTextSize="18sp"
                app:animationDuration="350"
                app:isTouchable="true"
                app:stepsCount="3" />
```

### Programmatically

```
StepsView.Builder(requireContext())
            .setCompletedColor(R.color.green)
            .setUncompletedColor(R.color.blue)
            .setTextCompletedColor(R.color.yellow)
            .setTextUncompletedColor(R.color.yellow)
            .setTextSize(R.dimen_18sp)
            .setTextFont(R.font.custom_font)
            .setIconCompleted(R.drawable.tkup_completed_step)
            .setLineCompletedColor(R.color.green)
            .setLineUncompletedColor(R.color.blue)
            .setSelectedSize(R.dimen.dimen_24)
            .setSize(R.dimen.dimen_20)
            .setLineThickness(R.dimen.dimen_2)
            .setLineMargins(R.dimen.dimen_24)
            .setIsTouchable(true)
            .setShownCompetedIcon(true)
            .setDuration(350)
            .setStepsCount(3)
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
<tr><td>Text font</td>      <td><code>app:stepTextFont</code></td>     <td><code>.setTextFont</code></td>     <td>Any font resource</td></tr>
<tr><td>Text size</td>      <td><code>app:stepTextSize</code></td>     <td><code>.setTextSize</code></td>     <td>Any dimen resource</td></tr>
  <tr><td>Complete Icon resource</td>      <td><code>app:stepIconCompleted</code></td>     <td><code>.setIconCompleted</code></td>     <td>Any drawable resource</td></tr>
  <tr><td>Completed Line color</td>     <td><code>app:stepProgressLineCompletedColor</code></td>     <td><code>.setLineCompletedColor</code></td>     <td>Any color resource</td></tr>
  <tr><td>Unompleted Line color</td>      <td><code>app:stepProgressLineUncompletedColor</code></td>     <td><code>.setLineUncompletedColor</code></td>     <td>Any color resource</td></tr>
  <tr><td>Size</td>     <td><code>app:stepIndicatorSize</code></td>     <td><code>.setSize</code></td>     <td>Any dimen resource</td></tr>
  <tr><td>Selected Size</td>      <td><code>app:stepSelectedIndicatorSize</code></td>     <td><code>.setSelectedSize</code></td>     <td>Any dimen resource</td></tr>
  <tr><td>Line Thickness</td>     <td><code>app:stepProgressLineThickness</code></td>     <td><code>.setLineThickness</code></td>     <td>Any dimen resource</td></tr>
  <tr><td>Line Margins</td>     <td><code>app:lineMargins</code></td>     <td><code>.setLineMargins</code></td>     <td>Any dimen resource</td></tr>
  <tr><td>Is Touchable</td>     <td><code>app:isTouchable</code></td>     <td><code>.setIsTouchable</code></td>     <td>true | false</td></tr>
  <tr><td>Shown Competed Icon</td>    <td><code>app:shownCompetedIcon</code></td>     <td><code>.setShownCompetedIcon</code></td>   <td>true | false</td></tr>
  <tr><td>Duration</td>   <td><code>app:animationDuration</code></td>   <td><code>.setDuration</code></td>    <td>Any integer (> 0)</td></tr>
    <tr><td>Steps count</td>    <td><code>app:stepsCount</code></td>   <td><code>.setStepsCount</code></td>   <td>Any integer (> 0)</td></tr>
    <tr><td>Current Selected Step</td>    <td><code>app:currentStep</code></td>   <td><code>.setCurrentIndex</code></td>      <td>Any integer</td></tr>
</table>


## Add a callback

If you want to perform an action when users touch your **StepView**, you can add a callback calling ```setCallback``` on your view and passing in a **StepsView.Callback** implementation.

```
interface Callback {
    fun onStepChanged(oldIndex: Int, newIndex: Int)
}
```
When the user changes step you will receive a notification to the **onStepChanged*** method with the index that has been abandoned and which has been taken.

Also you can passing it when you are using the **StepView.Builder** calling ```.setCallback```.

## Methods

```.loadItems```: You can change your step items on runtime calling this method. You only need to pass the total count steps and the selected index after load.

```.next```: Move to the next index available (if the last index is selected, no changes were applied).

```.previous```: Move to the previous index available (if the first index is selected, no changes were applied).

```.attach```: If you want to move your **StepView** along with your **RecyclerView**, you can do so by attaching your RecyclerView by calling this method.

```.setSelectedSteps```: Also if you want change the selected item on runtime might use this method passing the index you want.

