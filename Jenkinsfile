pipeline {
    agent {
            docker {
                //image 'sivaprasadreddy/java17-agent'
                image 'eclipse-temurin:17-alpine'
                args '-u root -v /var/run/docker.sock:/var/run/docker.sock'
            }
          }

    triggers { pollSCM 'H/2 * * * *' }

    stages {

        stage('Build') {
            steps {
                //echo "DOCKER_HOST: ${DOCKER_HOST}"
                // sh "docker info"
                sh './mvnw -ntp verify'
            }
        }
    }
}