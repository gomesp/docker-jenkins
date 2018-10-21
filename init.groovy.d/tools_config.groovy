import hudson.model.JDK
import jenkins.model.Jenkins

Jenkins jenkins = Jenkins.instance

JDK.DescriptorImpl jdkDescriptor = jenkins.getDescriptor(hudson.model.JDK.class) 

JDK jdk18 = new JDK ("JDK 1.8", "/usr/lib/jvm/java-1.8.0")

jdkDescriptor.setInstallations(jdk18)
jdkDescriptor.save()

jenkins.save()
