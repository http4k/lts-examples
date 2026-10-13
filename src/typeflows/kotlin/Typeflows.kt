import io.typeflows.github.DotGitHub
import io.typeflows.github.TypeflowsGitHubRepo
import io.typeflows.github.visualisation.WorkflowVisualisations
import io.typeflows.github.workflow.Cron
import io.typeflows.github.workflow.Workflow
import io.typeflows.github.workflow.step.RunCommand
import io.typeflows.util.Builder
import org.http4k.typeflows.GithubActionConstants.JAVA_VERSION
import org.http4k.typeflows.Http4kProjectStandards
import org.http4k.typeflows.UpdateGradleProjectDependencies

class Typeflows : Builder<TypeflowsGitHubRepo> {
    override fun build() = TypeflowsGitHubRepo {
        dotGithub = DotGitHub {
            workflows += BuildWorkflow()
            workflows += withEeMavenCredentials(
                UpdateGradleProjectDependencies(
                    "update-dependencies",
                    Cron.of("0 08 * * 1"),
                    RunCommand("./gradlew check"),
                    "master",
                    "master",
                    JAVA_VERSION
                ).build()
            )

            files += WorkflowVisualisations(workflows)
        }

        files += Http4kProjectStandards()
    }
}

private fun withEeMavenCredentials(workflow: Workflow) = Workflow("update-dependencies") {
    displayName = workflow.displayName
    permissions = workflow.permissions
    on += workflow.on
    jobs += workflow.jobs
    env += "LTS_MAVEN_USER" to $$"${{ secrets.LTS_MAVEN_USER }}"
    env += "LTS_MAVEN_PASSWORD" to $$"${{ secrets.LTS_MAVEN_PASSWORD }}"
}
