pluginManagement {
    repositories {
        maven { setUrl("https://maven.aliyun.com/nexus/content/repositories/central")}
        maven { setUrl("https://maven.aliyun.com/nexus/content/groups/public/")}
        maven { setUrl("https://maven.aliyun.com/nexus/content/repositories/google")}
        maven { setUrl("https://maven.aliyun.com/nexus/content/repositories/jcenter")}
        maven { setUrl("https://maven.aliyun.com/nexus/content/repositories/gradle-plugin")}

        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { setUrl("https://maven.aliyun.com/nexus/content/repositories/central")}
        maven { setUrl("https://maven.aliyun.com/nexus/content/groups/public/")}
        maven { setUrl("https://maven.aliyun.com/nexus/content/repositories/google")}
        maven { setUrl("https://maven.aliyun.com/nexus/content/repositories/jcenter")}
        maven { setUrl("https://maven.aliyun.com/nexus/content/repositories/gradle-plugin")}


        maven { url = uri("https://maven.rokid.com/repository/maven-public/") }
        google()
        mavenCentral()
    }
}

rootProject.name = "My Application"
include(":app")
 