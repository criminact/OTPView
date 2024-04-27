#  Customizable OTP Text View
[![Jitpack >](https://jitpack.io/v/criminact/OTPView.svg)](https://jitpack.io/#criminact/OTPView)

*Note - Help needed in maintenance, Please raise PRs for new features and bug fixes, I will review and merge them*

![Box Layout >](https://github.com/criminact/OTPView/blob/develop/github-assets/image%20(2).png)
![Line Layout >](https://github.com/criminact/OTPView/blob/develop/github-assets/image%20(3).png)
![Success >](https://github.com/criminact/OTPView/blob/develop/github-assets/image%20(1).png)
![Failure >](https://github.com/criminact/OTPView/blob/develop/github-assets/image.png)

## How to use the library in your project
##### Step 1. Please paste this code in your ***settings.gradle*** file
```java
pluginManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

##### Step 2. Paste the below code in your ***build.gradle:app*** file
```java
dependencies {
    implementation 'com.github.criminact:OTPView:Tag'
}
```

##### Step 3. Use the OTP text view in your xml file
```java
<com.noobcode.otpview.OTPView
        android:id="@+id/otpView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:textColor="@color/white"
        app:borderThickness="2"
        app:otpLength="6"
        app:otpLayoutColor="@color/teal_200"
        app:otpLayoutType="0"
        app:spaceBetween="16"
    />
```

##### Step 4. Use the OTP listener callback to get the OTP and a completion callback
```java
var otpView = findViewById<OTPView>(R.id.otpView)

otpView.setOTPListener(object : OTPView.OTPListener {
    override fun onOTPCompleted(otp: String) {
	Toast.makeText(this@MainActivity, "OTP Completed", Toast.LENGTH_LONG).show()
	//compare the OTP submitted by user to your generated one. Handle success and failure accordingly
	if(otp == "123456"){
            otpView.showSuccess()
        }else{
            otpView.showFailure()
        }
    }

}
```

## Author
### Maintained by [Raj Hada](https://github.com/criminact/ "Raj Hada")

## Contribution
### Bug reports and Feature requests/implementations are welcome
