pipeline {
    agent any

    stages {
        stage('Build with Maven') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t room-service .'
            }
        }

        stage('Stop Old Container') {
            steps {
                bat 'docker stop room-service || exit 0'
                bat 'docker rm room-service || exit 0'
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
            echo "❌ Deployment failed"
        }
        success {
            echo "✅ Deployment successful"
        }
    }
}
