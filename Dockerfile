FROM jenkins/jenkins:2.147-alpine
USER jenkins
# skip the jenkins wizard setup
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

# set the environment variables for the default admin user (see init.groovy.d/default.user.groovy)
ENV JENKINS_USER admin
ENV JENKINS_PASS admin

# initialise Jenkins using the configuration-as-code plugin
COPY casc_configs/* /var/jenkins_home/casc_configs/
ENV CASC_JENKINS_CONFIG /var/jenkins_home/casc_configs
RUN /usr/local/bin/plugins.sh configuration-as-code:latest

# default groovy initialisation: set default user
COPY init.groovy.d/* /usr/share/jenkins/ref/init.groovy.d/