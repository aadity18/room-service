pipeline {
    agent any

    tools {
        maven 'Maven_3' // Make sure 'Maven_3' is defined in Jenkins Global Tool Configuration
    }

    environment {
        IMAGE_NAME = 'room-service'
        CONTAINER_NAME = 'room-service'
        PORT = '8081'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/aadity18/room-service.git'
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t %IMAGE_NAME% ."
            }
        }

        stage('Stop Old Container') {
            steps {
                bat "docker stop %CONTAINER_NAME% || echo No container to stop"
                bat "docker rm %CONTAINER_NAME% || echo No container to remove"
            }
        }

        stage('Run Docker Container') {
            steps {
                bat "docker run -d -p %PORT%:%PORT% --name %CONTAINER_NAME% %IMAGE_NAME%"
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
