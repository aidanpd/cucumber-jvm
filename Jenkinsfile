pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
		echo BRANCH_NAME
		echo COMMIT_ID
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
