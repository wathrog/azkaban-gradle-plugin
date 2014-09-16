azkaban-gradle-plugin
=====================
The Azkaban gradle plugin is an effort to ease the definition of jobs and flows
for the Linkedin made [Azkaban Scheduler](http://azkaban.github.io/)

It has been chosen to be part of Gradle as the definition of jobs and packaging
of libraries should be part of the build process.

## Usage
The plugin is composed of two parts:
* An extension to the [Gradle DSL](http://www.gradle.org/docs/current/dsl/)
* A set of three tasks
  * Generation of the `.job` files for uploading to Azkaban
  * A copy task for copying eventual `libs` and `scripts` to the distribution
  * A packaging task to create the final Zip file for uploading to Azkaban

To include the plugin in your build you should apply it by adding it to the
classpath of the build script and applying it like the following:
```gradle
buildscript {
  repositories{
    mavenLocal() //Currently not in maven central
  }
  dependencies {
    classpath 'org.wathrog:azkaban-gradle-plugin:0.0.1'
  }
}

apply plugin: 'azkaban'

```

### DSL Extension
The DSL extension can be used to define flows and their jobs in a convenient
format. Currently only the `command`, `flow`, `hadoopJava` and `noop` job types
are supported.

The syntax is as follwing:
```groovy
azkaban {
  destDir = <destination_directory> //Default: $project.buildDir/azkaban
  libDir = <libraries_directory> //(Optional) directory from which to copy the files to lib/
  scriptsDir = <scripts_dir> //Default: src/main/scripts source dir for the scripts/
  archiveDir = <archive_dir> //Default: $project.buildDir/azkabandist
                             //destination dir for the archive
  flows {
    ...
    <flow_name> {
      jobs {
        ...
        <job_name> {  
          type = <job_type> //See supported tyoes
          dependencies = <dependencies> //comma separated list of job names
          //Eventual type-specific properties such as 'command' or 'flow'
        }
        ...
      }
    }
    ...
  }
}

```
### Invoking the tasks
The tasks that the plugin adds to the build, once applied, are:
* `prepareStructure` that copies everything from `$libDir` to `$destDir/lib` and
from `$scriptsDir` to `$destDir/scripts`
* `createJobFiles` that create all the jobs and flows defined in the DSL in `$destDir`
* `createDistributionZip` packages everything from `$destDir` in a zip file in `$archiveDir`

### Example usage
Here is an example of usage in a `build.gradle` file:
```gradle
buildscript {
  dependencies {
    ...
    classpath 'org.wathrog:azkaban-gradle-plugin:0.0.1'
    ...
  }
}

...
apply plugin: 'azkaban'

azkaban {
  flows {
    ...
  }
}
...
```

Invoking `gradle createDistributionZip` will create the Zip file ready for Azkaban
