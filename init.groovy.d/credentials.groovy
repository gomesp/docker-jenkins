import com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey
import com.cloudbees.plugins.credentials.CredentialsScope
import com.cloudbees.plugins.credentials.SystemCredentialsProvider
import com.cloudbees.plugins.credentials.common.StandardCredentials
import com.cloudbees.plugins.credentials.domain.Domain
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl
import hudson.util.Secret
import org.apache.commons.fileupload.FileItem
import org.jenkinsci.plugins.plaincredentials.impl.FileCredentialsImpl
import org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl

credentialsStore = SystemCredentialsProvider.instance.store

currentCredentialsList = credentialsStore.domain.collectMany {
    domain -> credentialsStore.getCredentials(domain).collect { credential -> credential.id }
}

def addGlobalCredentials(StandardCredentials credentials) {
    // Don't override existing entries
    if (currentCredentialsList.contains(credentials.id)) {
        println "Skipping ${credentials.id}. Already present"
    } else {
        credentialsStore.addCredentials(Domain.global(), credentials)
        println "Credential added: ${credentials.id}."
    }
}

def addUserNamePassword(id, username, description) {
    def credential = new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL, id, description, username, "empty")
    this.addGlobalCredentials(credential)
}

def addSshUserAndKey(id, username, description) {
    def keySource = new BasicSSHUserPrivateKey.DirectEntryPrivateKeySource("empty")
    def passphrase = ""
    def credential = new BasicSSHUserPrivateKey(CredentialsScope.GLOBAL, id, username, keySource, passphrase, description)
    this.addGlobalCredentials(credential)
}

def addSecretText(id, description) {
    def credential = new StringCredentialsImpl(CredentialsScope.GLOBAL, id, description, Secret.fromString("empty"))
    this.addGlobalCredentials(credential)
}

def addSecretFile(id, filename, description) {
    def data = "empty"
    def fileItem = [ getName: { return filename }, get: { return data.getBytes() } ] as FileItem
    def credential = new FileCredentialsImpl(CredentialsScope.GLOBAL, id, description, fileItem, filename, data)
    this.addGlobalCredentials(credential)
}

this.addUserNamePassword("example-credential-id", "user", "Sample User")

credentialsStore().save()