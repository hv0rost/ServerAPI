## - Maven connections in simple Android project

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
## - Maven connections in simple KMM project
allprojects {
    repositories {
        ...
        maven(url = "https://jitpack.io/)
    }
}

## - Then connect implementations
dependencies {
	        implementation 'com.github.hv0rost:TheCloudServerLibrary:Tag'
	}
