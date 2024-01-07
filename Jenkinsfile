pipeline {
    agent any

    triggers { pollSCM 'H/1 * * * *' }

    stages {
        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }
    }
}