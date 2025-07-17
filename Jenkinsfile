pipeline {
    agent any

    tools {
        maven 'Maven_3'
    }

    environment {
        IMAGE_NAME     = 'room-service'
        CONTAINER_NAME = 'room-service'
        PORT           = '8081'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git 'https://github.com/aadity18/room-service.git'
            }
        }

        stage('Build with Maven') {
            steps {
                // Remove -DskipTests if you want to run tests
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Stop & Remove Old Container') {
            steps {
                script {
                    sh 'docker stop $CONTAINER_NAME || true'
                    sh 'docker rm $CONTAINER_NAME || true'
                }
            }
        }

        stage('Run New Container') {
            steps {
                sh 'docker run -d -p $PORT:$PORT --name $CONTAINER_NAME $IMAGE_NAME'
            }
        }
    }

    post {
        success {
            echo "✅ Deployment successful — $IMAGE_NAME is running on port $PORT"
        }
        failure {
            echo "❌ Deployment failed — check console output"
        }
    }
}
