import org.jenkins.plugins.lockableresources.LockableResource
import org.jenkins.plugins.lockableresources.LockableResourceManager

manager = LockableResourceManager.get()

def defineLock(name, description) {
    manager.createResource(name)
    response = manager.fromName(name)
    response.description = description
    println "Lock ${name} created"
}

this.defineLock("TEST_LOCKABLE_RESOURCE") //e.g. use for locking an ansible session

manager.save()
