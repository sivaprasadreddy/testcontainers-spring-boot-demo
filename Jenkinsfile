pipeline {
    agent {
        docker {
            image 'sivaprasadreddy/java17-agent'
            args '--network host -e DOCKER_HOST=tcp://docker:2376 -e DOCKER_CERT_PATH=/certs/client -e DOCKER_TLS_VERIFY=1'
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