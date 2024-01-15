pipeline {
    agent {
        docker { image 'jenkins/ssh-agent:alpine-jdk17' }
      }

    triggers { pollSCM 'H/2 * * * *' }

    stages {

        stage('Build') {
            steps {
                echo "DOCKER_HOST: ${DOCKER_HOST}"
                sh './mvnw -ntp verify'
            }
        }
    }
}