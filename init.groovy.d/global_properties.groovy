import hudson.EnvVars
import hudson.slaves.EnvironmentVariablesNodeProperty
import hudson.slaves.NodeProperty
import hudson.slaves.NodePropertyDescriptor
import hudson.util.DescribableList
import jenkins.model.jenkins

Jenkins jenkins = Jenkins.instance

DescribableList<NodeProperty<?>, NodePropertyDescriptor> globalNodeProperties = Jenkins.instance.getGlobalNodeProperties()
List <EnvironmentVariablesNodeProperty> envVarsNodePropertyList = globalNodeProperties.getAll(EnvironmentVariablesNodeProperty.class)

EnvVars envVars

if (envVarsNodePropertyList == null || envVarsNodePropertyList.size() == 0) {
    def newEnvVarsNodeProperty = new EnvironmentVariablesNodeProperty()
    globalNodeProperties.add (newEnvVarsNodeProperty)
    envVars = newEnvVarsNodeProperty.getEnvVars()
} else {
    envVars = envVarsNodePropertyList.get(0).getEnvVars()
}

envVars.put("GLOBAL_VARIABLE_KEY", "GLOBAL_VARIABLE_VALUE1")