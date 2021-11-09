# kirpter

The purpose of this repo is to create a gradle plugin that supports loading kotlin compiler plugins in an easy way compatible with:

```kotlin
dependencies {
  myplugin(project(":myawesomesubmodule"))
}
``` 

Similar to how KSP works, but working for plugins that mutate the IR.

The idea is to create plugins like this one:

<https://github.com/korlibs/korge-next/pull/389>

In a way that can be declared as a gradle module and used in other modules in the same project, 
in addition to only require one artifact for the plugin, and not requiring extra gradle plugins like this one.
