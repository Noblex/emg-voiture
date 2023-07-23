pipeline {
    agent any

    // Définir les variables d'environnement pour une réutilisation dans le pipeline
    environment {
        // Modifier ces variables en fonction de la configuration de votre projet
        MAVEN_HOME = tool 'Maven' // Assurez-vous d'avoir configuré Maven dans Jenkins en tant qu'outil "Maven"
        GLASSFISH_HOME = '/chemin/vers/votre/glassfish' // Remplacez par le chemin réel de votre installation de GlassFish
        APP_NAME = 'crud' // Remplacez par le nom de votre application (car crud)
        CREDENTIALS_ID = 'vos-identifiants-glassfish' // Remplacez par l'ID des identifiants Jenkins pour GlassFish
    }

    stages {
        stage('Checkout') {
            steps {
                // Récupérer le code depuis votre dépôt GitHub
                git 'https://github.com/Noblex/carcrud'
            }
        }

        stage('Build') {
            steps {
                // Compiler votre application Web Java Enterprise en utilisant Maven
                sh "${MAVEN_HOME}/bin/mvn clean package"
            }
        }

        stage('Deploy') {
            steps {
                // Désinstaller l'application si elle existe déjà dans GlassFish
                sh "${GLASSFISH_HOME}/bin/asadmin undeploy ${APP_NAME} || true"

                // Déployer le fichier WAR dans GlassFish
                sh "${GLASSFISH_HOME}/bin/asadmin deploy --user admin --passwordfile /chemin/vers/fichier-de-mot-de-passe ${WORKSPACE}/target/${APP_NAME}.war"

                // Attendre que le déploiement dans GlassFish se termine (ajuster la durée du sommeil si nécessaire)
                sleep time: 30, unit: 'SECONDS'
            }
        }
    }

    post {
        success {
            echo 'Déploiement réussi pour l\'application "car crud" !'
        }
        failure {
            echo 'Échec du déploiement pour l\'application "car crud" !'
        }
    }
}

// Note :
// 1. Assurez-vous de configurer Jenkins avec les identifiants nécessaires pour accéder au dépôt GitHub et au serveur GlassFish.
// 2. Créez un fichier de mot de passe avec les identifiants d'administration de GlassFish (utilisateur=admin, mot de passe=votre-mot-de-passe-admin) et remplacez '/chemin/vers/fichier-de-mot-de-passe' par le chemin réel du fichier de mot de passe dans l'étape de déploiement.
//    Exemple de commande pour créer le fichier de mot de passe : echo "AS_ADMIN_PASSWORD=votre-mot-de-passe-admin" > /chemin/vers/fichier-de-mot-de-passe
