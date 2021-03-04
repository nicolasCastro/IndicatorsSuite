<h1 align="center">Steps Indicator</h1></br>

<p align="center">
:red_circle: :white_circle: :large_blue_circle: A lightweight steps wizard component, fully customizable.
</p>
</br>
<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p> <br>

<p align="center">
  <img src="https://user-images.githubusercontent.com/24237865/61194943-f9d70380-a6ff-11e9-807f-ba1ca8126f8a.gif" width="32%"/>
  <img src="https://user-images.githubusercontent.com/24237865/61225579-d346b600-a75b-11e9-84f8-3c06047b5003.gif" width="32%"/>
</p>

## How to include it in your project

[![](https://jitpack.io/v/nicolasCastro/dotsIndicator.svg)](https://jitpack.io/#nicolasCastro/dotsIndicator)

### Gradle
Add below codes to your **root** `build.gradle`.
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
    implementation 'com.github.nicolasCastro.IndicatorsSuite:stepsindicator:1.2.0'
}
```

## How to use

You can create and customize a StepView in both ways, in design view and programmatically.

### In XML

```
<com.thinkup.stepsindicator.StepsView
                android:id="@+id/stepsView4"
                android:layout_width="match_parent"
                android:layout_height="60dp"
                android:paddingHorizontal="16dp"
                app:currentStep="0"
                app:layout_constraintTop_toBottomOf="@+id/stepsView3"
                app:shownCompetedIcon="true"
                app:stepCompletedColor="@color/colorPrimaryDark"
                app:stepIndicatorSize="32dp"
                app:stepProgressLineCompletedColor="@color/colorPrimaryDark"
                app:stepProgressLineThickness="2dp"
                app:stepProgressLineUncompletedColor="@color/colorAccent"
                app:stepSelectedIndicatorSize="48dp"
                app:stepTextCompletedColor="@color/colorAccent"
                app:stepTextUncompletedColor="@color/colorPrimaryDark"
                app:stepUncompletedColor="@color/colorAccent"
                app:stepsCount="3" />
```

### Programmatically

```
StepsView.Builder(requireContext())
            .setCompletedColor(R.color.green)
            .setUncompletedColor(R.color.blue)
            .setTextCompletedColor(R.color.yellow)
            .setTextUncompletedColor(R.color.yellow)
            .setIconCompleted(R.drawable.tkup_completed_step)
            .setLineCompletedColor(R.color.green)
            .setLineUncompletedColor(R.color.blue)
            .setSelectedSize(R.dimen.dimen_24)
            .setSize(R.dimen.dimen_20)
            .setLineThickness(R.dimen.dimen_2)
            .setIsTouchable(true)
            .setShownCompetedIcon(true)
            .setDuration(350)
            .setStepsCount(3)
            .setCurrentIndex(0)
```

### Params


