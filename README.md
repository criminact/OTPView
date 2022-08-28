#  Customizable OTP Text View
[![](https://jitpack.io/v/criminact/OTPView.svg)](https://jitpack.io/#criminact/OTPView)

*Note - Help needed in maintenance, Please raise PRs for new features and bug fixes, I will review and merge them*

![](https://github.com/criminact/OTPView/blob/develop/github-assets/otp-view.png)

## How to use the library in your project
##### Step 1. Please paste this code in your ***settings.gradle*** file
```java
dependencyResolutionManagement {
	repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

##### Step 2. Paste the below code in your ***build.gradle:app*** file
```java
dependencies {
    implementation 'com.github.criminact:OTPView:1.0.0'
}
```

##### Step 3. Use the OTP text view in your xml file
```java
<com.noobcode.otpview.OTPView
        android:id="@+id/otpView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:textColor="#FF000000" //Sets the Text Color and Bottom Line Color
        app:borderThickness="6" //Sets the Border Thickness, default is 1 (Integer)
        app:otpLength="6" //Sets the length of the OTP, default is 6 (Integer)
        app:spaceBetween="20" //Sets the space between the neighbouring Bottom Lines
/>
```

##### Step 4. Use the OTP listener callback to get the OTP and a completion callback
```java
var otpView = findViewById<OTPView>(R.id.otpView)

otpView.setOTPListener(object : OTPView.OTPListener {
    override fun onOTPCompleted(otp: String) {
	Toast.makeText(this@MainActivity, "OTP Completed", Toast.LENGTH_LONG).show()
	//compare the OTP submitted by user to your generated one. Handle success and failure accordingly
    }

}
```

## Author
##### Maintained by [Raj Hada](https://github.com/criminact/ "Raj Hada")

## Contribution
##### Bug reports and Feature requests/implementations are welcome
