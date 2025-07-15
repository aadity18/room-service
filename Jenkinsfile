pipeline {
    agent any

    tools {
        maven 'Maven_3'  // Make sure you have this tool configured in Jenkins
    }

    environment {
        DOCKER_IMAGE_NAME = 'room-service'
        CONTAINER_NAME = 'room-service'
        PORT = '8082'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/aadity18/room-service.git'
            }
        }

        stage('Build with Maven') {
            steps {
                bat '"C:\\Program Files\\Apache\\maven-3.9.10\\bin\\mvn.cmd" clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t ${DOCKER_IMAGE_NAME} ."
            }
        }

        stage('Stop Old Container') {
            steps {
                bat """
                    docker stop ${CONTAINER_NAME} || exit 0
                    docker rm ${CONTAINER_NAME} || exit 0
                """
            }
        }

        stage('Run Docker Container') {
            steps {
                bat "docker run -d -p ${PORT}:${PORT} --name ${CONTAINER_NAME} ${DOCKER_IMAGE_NAME}"
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
