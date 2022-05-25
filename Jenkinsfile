pipeline {
    options { disableConcurrentBuilds() }
    agent { label 'master' }
    environment {
        SLACK_CHANNEL = '#jenkins'
      }
    stages{
        stage('clean gradlew') {
            steps {
                sh './gradlew clean -Dorg.gradle.java.home=/usr/lib/jvm/java-11-openjdk-amd64'
            }
        }
        stage('Build'){
            environment {
                appDistributionReleaseNoteString = "BuildNumber : #${BUILD_NUMBER} \nBranchName : $BRANCH_NAME"
            }
            parallel{
                stage('develop Build'){
                    when {
                        expression {
                             return BRANCH_NAME =~ 'develop'
                        }
                    }
                    stages{
                        stage('build') {
                            steps {
                                sh './gradlew --profile --rerun-tasks :app:assembleRelease -Dorg.gradle.java.home=/usr/lib/jvm/java-11-openjdk-amd64'
                            }
                        }
                        stage('AppDistribution') {
                            steps {
                                build job : 'Firebase_App_Distribution', parameters: [ string( name: 'nodeLabelName', value: 'master'), string ( name: 'appFilePath', value: "${WORKSPACE}/app/build/outputs/apk/dev/release/app-dev-release-unsigned.apk" ) ,string( name : "appDistributionId", value : "1:900609572656:android:9a720b847a64cdbd3a12f5" ) , string( name : "testGroup", value :"Android_Tester_Dev" ), string ( name : "des_notes", value : appDistributionReleaseNoteString ) ]
                            }
                        }
                    }
                }
                stage('QA Build'){
                    when {
                        expression {
                            return BRANCH_NAME =~ 'release'
                        }
                    }
                    stages{
                        stage('build') {
                            steps {
                                sh './gradlew --profile --rerun-tasks :app:assembleQARelease -Dorg.gradle.java.home=/usr/lib/jvm/java-11-openjdk-amd64'
                            }
                        }
                        stage('AppDistribution') {
                            steps {
                                build job : 'Firebase_App_Distribution', parameters: [ string( name: 'nodeLabelName', value: 'master'), string ( name: 'appFilePath', value: "${WORKSPACE}/app/build/outputs/apk/qa/release/app-qa-release.apk" ) ,string( name : "appDistributionId", value : "1:900609572656:android:9c857672d201ab4a3a12f5" ) , string( name : "testGroup", value :"Android_Tester_QA" ), string ( name : "des_notes", value : appDistributionReleaseNoteString ) ]
                            }
                        }
                    }
                }
                stage('Product Build'){
                    when { branch 'main'}
                    stages{
                        stage('build') {
                            stages{
                                stage('apk build'){
                                    steps{
                                        sh './gradlew --profile --rerun-tasks :app:assembleProductRelease -Dorg.gradle.java.home=/usr/lib/jvm/java-11-openjdk-amd64'
                                    }
                                }
                                stage('bundle build'){
                                    steps {
                                        sh './gradlew --profile --rerun-tasks :app:bundleProductRelease -Dorg.gradle.java.home=/usr/lib/jvm/java-11-openjdk-amd64'
                                    }
                                }
                            }
                        }

                        stage('release') {
                            steps {
                                archiveArtifacts artifacts : "app/build/outputs/apk/**/*.apk"
                                archiveArtifacts artifacts : "app/build/outputs/bundle/**/*.aab"
                            }
                        }
                    }
                }
            }
        }
    }
    post {
            success {
                slackSend (channel: SLACK_CHANNEL, color: '#38BB86', message: "SUCCESSFUL: [${env.NODE_LABELS}] - Job ${env.JOB_NAME} [${env.BUILD_NUMBER}] after ${currentBuild.durationString.split(" and")[0]}  (<${env.BUILD_URL}|Open>)")
            }
            failure {
                slackSend (channel: SLACK_CHANNEL, color: '#A10002', message: "FAILED: [${env.NODE_LABELS}] - Job ${env.JOB_NAME} [${env.BUILD_NUMBER}] after ${currentBuild.durationString.split(" and")[0]} (<${env.BUILD_URL}|Open>)")
            }
        }
}
