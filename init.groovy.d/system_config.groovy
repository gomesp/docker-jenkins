import hudson.security.csrf.DefaultCrumbIssuer
import jenkins.model.Jenkins
import jenkins.model.JenkinsLocationConfiguration
import jenkins.CLI
import jenkins.security.s2m.AdminWhitelistRule
import org.kohsuke.stapler.StaplerProxy
import hudson.tasks.Mailer
import hudson.plugins.locale.PluginImpl

println("System configuration")

println("Configuring Remoting (JNLP4 only, no Remoting CLI)")
CLI.get().enabled = false
Jenkins.instance.agentProtocols = new HashSet<String>(["JNLP4-connect"])
Jenkins.instance.getExtensionList(StaplerProxy.class)
    .get(AdminWhitelistRule.class)
    .masterKillSwitch = false

println("Checking the CSRF protection")
if (Jenkins.instance.crumbIssuer == null) {
    println "CSRF protection is disabled, Enabling the default Crumb Issuer"
    Jenkins.instance.crumbIssuer = new DefaultCrumbIssuer(true)
}

println("Configuring Quiet Period")
Jenkins.instance.quietPeriod = 0

println("Configuring Email global settings")
JenkinsLocationConfiguration.get().adminAddress = "noreply@example.org"
Mailer.descriptor().defaultSuffix = "@noreply.example.org"

println("Configuring Locale")
PluginImpl localePlugin = (PluginImpl)Jenkins.instance.getPlugin("locale")
localePlugin.systemLocale = "en_US"
localePlugin.@ignoreAcceptLanguage=true