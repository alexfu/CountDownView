# CountDownView
What is it? It's a custom View that displays a simple countdown. It looks like this:

![CountDownView](http://i.imgur.com/iSSqbyZ.png)

CountDownView is backed by [CountDownTimer](http://developer.android.com/reference/android/os/CountDownTimer.html) so that you don't have to worry about lifting any boxes...

It also uses [Android-RobotoTextView](https://github.com/johnkil/Android-RobotoTextView) for API levels below 11 to keep consistency.

# Interested? Here's how to use it

First, specify the following in your `AndroidManifest.xml`. This library uses a [Service](http://developer.android.com/reference/android/app/Service.html) to handle the [CountDownTimer](http://developer.android.com/reference/android/os/CountDownTimer.html).

```xml
<service android:name="com.alexfu.countdownview.core.TimerService"/>
```

Sample layout...

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:custom="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <com.alexfu.countdownview.widget.CountDownView
        android:id="@+id/countdownview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

</FrameLayout>
```
By default, the above wont show you anything. Choose to display **hours** or **minutes** or **seconds** or **milliseconds**, or all 4.

Show only **hours**:

```xml
<com.alexfu.countdownview.widget.CountDownView
    android:id="@+id/countdownview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    custom:showHour="true"/>
```

Show **hours** and **minutes**:

```xml
<com.alexfu.countdownview.widget.CountDownView
    android:id="@+id/countdownview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    custom:showHour="true"
    custom:showMin="true"/>
```

You can also set text colors for digits and units:

```xml
<com.alexfu.countdownview.widget.CountDownView
    android:id="@+id/countdownview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    custom:showHour="true"
    custom:showMin="true"
    custom:numberColor="@color/black"
    custom:unitColor="@color/grey"/>
```

In code:



```java
CountDownView cdv = (CountDownView) findViewById(R.id.countdownview);
cdv.setInitialTime(30000); // Initial time of 30 seconds.
cdv.start();
cdv.stop();
cdv.reset();

```
To get notified when timer reaches zero implement TimerListener in your activity and override timerElapsed

```java

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ......
        .....
        cdv.setListener(this);
    }

@Override
    public void timerElapsed() {
        //Do something here
    }
```


# TODO

* ~~Implement an event listener when timer is done/reaches zero.~~
* Add support for different text sizes.
* Add more TODO items.
