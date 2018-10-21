import org.jenkinsci.plugins.workflow.libs.GlobalLibraries
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration
import org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever
import jenkins.plugins.git.GitSCMSource
import jenkins.model.*

def globalLibraries = GlobalLibraries.get()
def libConfigName = 'jenkins-pipeline-library'

libraryConfigExists = globalLibraries.find { lib -> lib.name == libConfigName }

if (libraryConfigExists) {
    println "${libConfigName} already defined"
} else {
    def libraryGitRepo = new GitSCMSource("https://gitlab.com/repo.git")
    libraryGitRepo.with {
        credentialsId = 'Gitlab_ServiceAccount'
        traits = [new BranchDiscoveryTrait()]
    }

    libraryConfig = new LibraryConfiguration(libConfigName, new SCMSourceRetriever(libraryGitRepo))
    libraryConfig.with {
        defaultVersion = 'master'
        implicit = false
        allowVersionOverride = true
        includeInChangesets = true
    }

    globalLibraries.getLibraries().add(libraryConfig)
    globalLibraries.save()
}