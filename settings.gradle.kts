pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven ( "https://maven.usehover.com/releases")
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven ( "https://maven.usehover.com/releases")
    }
}

rootProject.name = "SaveSecondsApp"
include(":app")
 