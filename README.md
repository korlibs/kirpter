# kotlin-ir-plugin-adapter-gradle

The purpose of this repo is to create a gradle plugin that supports loading kotlin compiler plugins in an easy way compatible with:

```kotlin
dependencies {
  myplugin(project(":myawesomesubmodule"))
}
``` 

Similar to how KSP works, but working for plugins that mutate the IR.
