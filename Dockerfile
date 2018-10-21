FROM jenkins/jenkins:2.147-alpine

USER jenkins
# skip the jenkins wizard setup
ENV JAVA_OPTS "-Djenkins.install.runSetupWizard=false -server -XX:+AlwaysPreTouch"

# default groovy initialisation: set default user
COPY init.groovy.d/* /usr/share/jenkins/ref/init.groovy.d/

# initialise Jenkins using the configuration-as-code plugin
COPY plugins.txt /var/jenkins_home/ref/
RUN /usr/local/bin/install-plugins.sh < /var/jenkins_home/ref/plugins.txt

COPY casc_configs/* /var/jenkins_home/casc_configs/
ENV CASC_JENKINS_CONFIG /var/jenkins_home/casc_configs