# CountDownView
What is it? It's a custom View that displays a simple countdown. It looks like this:

![CountDownView](http://i.imgur.com/iSSqbyZ.png)

CountDownView is backed by [CountDownTimer](http://developer.android.com/reference/android/os/CountDownTimer.html) so that you don't have to worry about lifting any boxes...

It also uses [Android-RobotoTextView](https://github.com/johnkil/Android-RobotoTextView) for API levels below 11 to keep consistency.

# Interested? Here's how to use it

In XML...

    <com.alexfu.countdownview.widget.CountDownView
    	android:id="@+id/countdownview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

Dont forget to set the custom namespace... `xmlns:custom="http://schemas.android.com/apk/res-auto"`
By default, the above wont show you anything. Choose to display **hours** or **minutes** or **seconds** or **milliseconds**, or all 4.

Show only **hours**:

    <com.alexfu.countdownview.widget.CountDownView
    	android:id="@+id/countdownview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        custom:showHour="true"/>

Show **hours** and **minutes**:

    <com.alexfu.countdownview.widget.CountDownView
    	android:id="@+id/countdownview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        custom:showHour="true"
        custom:showMin="true"/>

You can also set text colors for digits and units:

    <com.alexfu.countdownview.widget.CountDownView
    	android:id="@+id/countdownview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        custom:showHour="true"
        custom:showMin="true"
        custom:numberColor="@color/black"
        custom:unitColor="@color/grey"/>

In code:

    CountDownView cdv = (CountDownView) findViewById(R.id.countdownview);
    cdv.setInitialTime(30000); // Initial time of 30 seconds.
    cdv.start();
    cdv.stop();
    cdv.reset();

# TODO

* Implement an event listener when timer is done/reaches zero.
* Add support for different text sizes.
* Add more TODO items.