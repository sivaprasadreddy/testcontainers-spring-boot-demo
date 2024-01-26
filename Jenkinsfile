pipeline {
    agent {
        docker {
            image 'eclipse-temurin:17.0.9_9-jdk-jammy'
            args '--network host -v /var/run/docker.sock:/var/run/docker.sock'
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
