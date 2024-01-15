pipeline {
    agent {
        docker { image 'eclipse-temurin:17-jdk' }
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