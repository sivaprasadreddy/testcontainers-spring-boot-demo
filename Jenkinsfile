pipeline {
    agent any

    triggers { pollSCM 'H/2 * * * *' }

    stages {

        stage('Build') {
            steps {
                sh './mvnw verify'
            }
        }
    }
}