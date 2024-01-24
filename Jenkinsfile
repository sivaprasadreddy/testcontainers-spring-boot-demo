pipeline {
    agent {
        docker {
            image 'eclipse-temurin:17-jre-jammy'
            args '-u $(id -u ${USER}):$(id -g ${USER}) -v /var/run/docker.sock:/var/run/docker.sock'
        }
      }

    triggers { pollSCM 'H/2 * * * *' }

    stages {

        stage('Build') {
            steps {
                echo "DOCKER_HOST: ${DOCKER_HOST}"
                echo "USER: ${USER}"
                sh './mvnw -ntp verify'
            }
        }
    }
}