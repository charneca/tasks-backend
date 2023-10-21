pipeline{
    agent any
    stages{
        stage('Build backend') {
            steps{
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage('Unit Testes') {
            steps{
                bat 'mvn test'
            }
        }
        stage('Sonar Analise') {
            environment{
                scannerHome = tool 'SonarQube_SCANNER'
            }
        steps{
                withSonarQubeEnv('SONARQUBE_SERVER'){
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=805e08d7960953bf6432b36bcc1fa358d7c34f48 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/tests/**,**/model/**,**Application.java"
                }
            }
        }
        stage('Quality Gate') {
            steps{
                sleep(10)
                timeout(time: 1, unit:'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Deploy Backend') {
            steps{
                deploy adapters: [tomcat8(credentialsId: 'tomcat_credentials', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage('API Test') {
            steps{
                dir('api-test'){
                    git branch: 'main', credentialsId: 'git_credentials', url: 'https://github.com/charneca/tasks-api-test'
                    bat 'mvn test'
                }
            }
        }
        stage('Deploy Frontend') {
            steps{
                dir('frontend'){
                    git branch: 'master', credentialsId: 'git_credentials', url: 'https://github.com/charneca/tasks-frontend'
                    bat 'mvn clean package'
                    deploy adapters: [tomcat8(credentialsId: 'tomcat_credentials', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
                }
            }
        }
        stage('functional Test') {
            steps{
                dir('functional-test'){
                    git branch: 'main', credentialsId: 'git_credentials', url: 'https://github.com/charneca/tasks-functional-tests'
                    bat 'mvn test'
                }
            }
        }
    }
}