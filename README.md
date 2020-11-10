## Maven connections in simple Android project
```Kotlin
allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
## Maven connections in KMM project
```Kotlin
allprojects {
    repositories {
        ...
        maven(url = "https://jitpack.io/)
    }
}
```

## Then connect implementations
```Kotlin
dependencies {
	        implementation 'com.github.hv0rost:TheCloudServerLibrary:Tag'
	}
```
