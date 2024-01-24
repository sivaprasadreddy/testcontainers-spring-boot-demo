pipeline {
    agent any

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