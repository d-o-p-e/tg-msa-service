pipeline {
    agent any
    tools {
        jdk 'jdk-17'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
    }
    stages {
        stage('Login & Configuration') {
            steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                sh 'curl -LO "https://storage.googleapis.com/kubernetes-release/release/v1.20.5/bin/linux/amd64/kubectl"'
                sh 'chmod u+x ./kubectl'
            }
        }

        stage('Check Pipelines') {
            steps {
                script {
                    def changedFiles = sh(script: 'git diff --name-only HEAD~1', returnStdout: true).trim().split('\n')
                    withKubeConfig([credentialsId: 'kubeconfig', serverUrl: 'https://192.168.219.108:6443']) {
                        if (changedFiles.any { it.startsWith("campaign") }) {
                            dir('campaign') {
                                try {
                                    sh '../kubectl apply -f k8s.yaml'
                                    sh "../kubectl rollout restart deployment/tg-campaign -n campaign"
                                    slackSend message: "Campaign Service Deployed - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
                                }
                                catch (err) {
                                    slackSend message: "Campaign Service Failed - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
                                }
                            }
                        }
                        if (changedFiles.any { it.startsWith("community") }) {
                            dir('community') {
                                try {
                                    sh '../kubectl apply -f k8s.yaml'
                                    sh "../kubectl rollout restart deployment/tg-community -n community"
                                    slackSend message: "Community Service Deployed - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
                                }
                                catch (err) {
                                    slackSend message: "Community Service Failed - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
                                }
                            }
                        }
                        if (changedFiles.any { it.startsWith("user") }) {
                            dir('user') {
                                try {
                                    sh '../kubectl apply -f k8s.yaml'
                                    sh "../kubectl rollout restart deployment/tg-user -n user"
                                    slackSend message: "User Service Deployed - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
                                }
                                catch (err) {
                                    slackSend message: "User Service Failed - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            sh 'docker logout'
        }
        success {
            slackSend message: "MSA Pipeline Finished - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
        }
        failure {
            slackSend message: "MSA Pipeline Crashed - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
        }
    }
}