pipeline {
    agent {
        docker {
            image 'sivaprasadreddy/java17-agent'
            args '-e DOCKER_HOST=tcp://docker:2376 -e DOCKER_CERT_PATH=/certs/client -e DOCKER_TLS_VERIFY=1 --volume jenkins-docker-certs:/certs/client:ro'
            // args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
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