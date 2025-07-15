pipeline {
    agent any

    stages {
        stage('Build with Maven') {
            steps {
                bat '"C:\\Program Files\\Apache\\maven-3.9.10\\bin\\mvn.cmd" clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t room-service .'
            }
        }

        stage('Stop Old Container') {
            steps {
                bat 'docker stop room-service || echo "Container not running"'
                bat 'docker rm room-service || echo "Container not existing"'
            }
        }

        stage('Run Docker Container') {
            steps {
                bat 'docker run -d -p 8081:8081 --name room-service room-service'
            }
        }
    }

    post {
        failure {
            echo '❌ Deployment failed'
        }
        success {
            echo '✅ Deployment successful'
        }
    }
}
