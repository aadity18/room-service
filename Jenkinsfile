pipeline {
    agent any

    environment {
        IMAGE_NAME = "room-service"
        CONTAINER_NAME = "room-service-container"
        PORT = "8081"
    }

    stages {
        stage('Clone Code') {
            steps {
                git 'https://github.com/aadity18/room-service.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Stop Old Container') {
            steps {
                sh """
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true
                """
            }
        }

        stage('Run Docker Container') {
            steps {
                sh "docker run -d -p ${PORT}:${PORT} --name ${CONTAINER_NAME} ${IMAGE_NAME}"
            }
        }
    }

    post {
        success {
            echo "🎉 Room Service deployed successfully on port ${PORT}"
        }
        failure {
            echo "❌ Deployment failed"
        }
    }
}
