import io.typeflows.github.workflow.Job
import io.typeflows.github.workflow.RunsOn.Companion.UBUNTU_LATEST
import io.typeflows.github.workflow.Workflow
import io.typeflows.github.workflow.step.RunCommand
import io.typeflows.github.workflow.step.marketplace.Checkout
import io.typeflows.github.workflow.step.marketplace.SetupJava
import io.typeflows.github.workflow.trigger.Branches
import io.typeflows.github.workflow.trigger.Push
import io.typeflows.util.Builder
import org.http4k.typeflows.GithubActionConstants.CHECKOUT
import org.http4k.typeflows.GithubActionConstants.JAVA_VERSION
import org.http4k.typeflows.GithubActionConstants.JDK
import org.http4k.typeflows.GithubActionConstants.SETUP_JAVA

class BuildWorkflow : Builder<Workflow> {
    override fun build() = Workflow("build") {
        displayName = "Build"

        on += Push {
            branches = Branches.Only("master")
        }

        jobs += Job("build", UBUNTU_LATEST) {
            steps += Checkout(CHECKOUT)
            steps += SetupJava(JDK, JAVA_VERSION, SETUP_JAVA)
            steps += RunCommand($$"./gradlew -PeeMavenUser=${{ secrets.LTS_MAVEN_USER }} -PeeMavenPassword=${{ secrets.LTS_MAVEN_PASSWORD }} check") {
                name = "Build"
            }
        }
    }

}
