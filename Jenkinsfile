pipeline {
    agent {
        docker { image 'sivaprasadreddy/jenkinsagent' }
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