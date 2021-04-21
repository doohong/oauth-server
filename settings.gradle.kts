rootProject.name = "oauth"

include("authorization")
include("resource")

for (project in rootProject.children) {
    project.projectDir = file("subprojects/${project.name}")
    assert(project.projectDir.isDirectory)
}