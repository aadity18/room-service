pipeline {
    agent any

    /* ↘  pick Maven_3 you added in “Global Tool Configuration” */
    tools {
        maven 'Maven_3'
    }

    /*  ↘  adjust only if you need a different name / port             */
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
                /*  remove -DskipTests if you want tests to run  */
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %IMAGE_NAME% .'
            }
        }

        stage('Stop & Remove Old Container') {
            steps {
                /*  returnStatus:true prevents a non‑zero exit code
                    from failing the pipeline if the container
                    does not yet exist.                           */
                script {
                    bat(script: 'docker stop %CONTAINER_NAME%', returnStatus: true)
                    bat(script: 'docker rm %CONTAINER_NAME%',   returnStatus: true)
                }
            }
        }

        stage('Run New Container') {
            steps {
                bat 'docker run -d -p %PORT%:%PORT% --name %CONTAINER_NAME% %IMAGE_NAME%'
            }
        }
    }

    post {
        success {
            echo "✅ Deployment successful — %IMAGE_NAME% is running on port %PORT%"
        }
        failure {
            echo "❌ Deployment failed — check console output"
        }
    }
}
