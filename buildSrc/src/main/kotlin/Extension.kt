import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.firebaseImplementation(dependency: Any) {
    add("firebaseImplementation", dependency)
}

fun DependencyHandler.huaweiImplementation(dependency: Any) {
    add("huaweiImplementation", dependency)
}