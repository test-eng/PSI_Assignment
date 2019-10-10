# PSI
SP Assignment

Introduction
------

- This project was implemented following [this SP Group's assignment](https://gist.github.com/winston/016a6a33000fec800f52938fef4a3149)
- Languague: Kotlin
- Framework: Android
- Architecture: MVVM
- Test: Unit Test with JUnit only cover [HomeViewModelTest.kt](https://github.com/pivincii/PSI/blob/master/app/src/test/java/com/psi/home/presentation/HomeViewModelTest.kt)
- Time to finish: Around 10 hours (Exclude writting README)


Building Instructions
------

This version only support debug build and test:

Build
```sh
./gradlew clean assembleDebug testDebug
```

Install

```sh
adb install -r app/build/outputs/apk/debug/app-debug.apk
```


TODO
------

#### In Architecture/Design: 
- [ ] Using custom ViewModel. Remove extend from the half baked Google ViewModel solution
- [ ] Extracting MapView as interface
- [ ] Implementing `saveInstanceState` and `onRestoreInstanceState`

#### In Security:
- [ ] Hiding Google Map Developer Key

#### In Testing
- [ ] Unit Test: Cover more code
- [ ] UI Test: Setup UI Testing

#### In UI/UX:
- [ ] Making the display index more meaning full (Eg: Set the color to read when the index is higher than standard)
- [ ] Rounding the number
- [ ] Making Dropdown List UI, Marker UI fancy

#### In Scale:
- [ ] Multiple modules?
- [ ] Lazy putting marker, only display market in the area that user bounding to
