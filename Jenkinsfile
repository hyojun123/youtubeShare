pipeline {
    agent any
    tools {nodejs "nodejs-14.17.3"}
    stages {
        stage('chmod') {
            steps {
                sh 'chmod +x mvnw'
            }
        }
        stage('Deployment') {
            parallel {
                stage('Dev') {
                    when {
                        anyOf {branch 'develop'}
                    }
                    steps {
                        sh './mvnw clean install jib:build -Djib.to.tags=dev.v$BUILD_NUMBER'
                    }
                }
                stage('Production') {
                    when {
                        anyOf {branch 'master'}
                    }
                    steps {
                        sh './mvnw clean install jib:build -Djib.to.tags=prod.v0.0.$BUILD_NUMBER'
                        sh 'sudo helm upgrade youtube-share /home/hjchoi/kuber/youtube-share/ --set image.tag="prod.v0.0.$BUILD_NUMBER"'
                    }
                }
            }
        }
    }
}