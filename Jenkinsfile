pipeline {
    agent {
            docker {
                image 'sivaprasadreddy/java17-agent'
                args '-u root --privileged --volume jenkins-docker-certs:/certs/client:ro -v /var/run/docker.sock:/var/run/docker.sock'
            }
          }

    triggers { pollSCM 'H/2 * * * *' }

    stages {

        stage('Build') {
            steps {
                //echo "DOCKER_HOST: ${DOCKER_HOST}"
                sh "docker info"
                sh './mvnw -ntp verify'
            }
        }
    }
}