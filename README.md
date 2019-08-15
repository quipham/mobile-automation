## npm packages
```
npm install -g appium
```

## install Android Studio, set ANDROID_HOME
```

/sdk
path -> /sdk/tools
path -> /sdk/platform-tools
```

## for MAC edit - nano ~./bash_profile
```
export ANDROID_HOME=/Users/$USER/Library/Android/sdk/
export PATH=$PATH:$ANDROID_HOME
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/platform-tools
```

## Test case
##### 1/ Open CGV App then Login
##### 2/ Select The First Movie showing on Home screen
##### 3/ Select The First showtime and cinema on list
##### 4/ Try To select randomly seat at bottom line (Will fail if no available seat)
##### 5/ Confirm The Ticket order and verify Billing Info

## Running the test
- This build is config to running only on MAC because of directory absolute path
- Make sure you have connected device (Check by "adb devices" command)
- Due to no Test APK so the Android Driver Init by the other way, It may lead to Test failure due to device specific (Recommend use SAMSUNG phone and non root device)
- Execute Maven Command
```
mvn clean test
```
Note:
- If maven can't build project and run the test the issue maybe in `setting.xml` file on your local `m2repository`. You can run directly by TestNG with file `BasicInteraction.xml` on package `src/test/java/com/example/booking/xml` (right click on this file to open menu> click on RUN)


## Viewing Test report
- Open file `*/reports/{DateTimeExecute}.html` on any browser

