pipeline {
    agent {
        docker {
            image 'sivaprasadreddy/java17-agent'
            // args '--network host'
        }
      }

    triggers { pollSCM 'H/2 * * * *' }

    stages {

        stage('Build') {
            steps {
                echo "DOCKER_HOST: ${DOCKER_HOST}"
                sh 'which docker'
                sh 'docker info'
                sh './mvnw -ntp verify'
            }
        }
    }
}