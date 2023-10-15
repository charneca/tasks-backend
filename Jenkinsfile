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
                withSonarQubeEnv('SONAR_LOCAL'){
                    bat "${SONAR_SCANNER}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=805e08d7960953bf6432b36bcc1fa358d7c34f48 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**, **/src/tests/**,**/model/**,**Application.java"
                }
            }
        }
    }
}