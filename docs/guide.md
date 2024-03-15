# Compte-rendu

Emilien Cosson

## Créer le projet Java avec Gradle

```bash
cd VehiclesRentalAgency
mkdir src
cd src
gradle init 	#type:application ; build script DSL:Groovy ; project name: VehiclesRentalAgency
```


## Ajouter les outils

### AssertJ

Dans le fichier `build.gradle`, ajouter dans `dependancies`,

`testImplementation 'org.assertj:assertj-core:3.6.1'`

### Mockito

Dans le fichier `build.gradle`, ajouter dans `dependancies`,

- `testImplementation 'org.mockito:mockito-core:4.+'`
- `testImplementation 'org.mockito:mockito-junit-jupiter:4.+'`
- `testImplementation 'org.mockito:mockito-inline:4.6.1'`

### JaCoCo

- ajouter `id 'jacoco'` dans `plugins`
- ajouter `jacoco {
    toolVersion = "0.8.11"
}`

## Configurer JaCoCo

Ajouter dans le fichier `build.gradle`

```
jacocoTestReport {
       finalizedBy jacocoTestCoverageVerification
    reports {
        xml.required = true
        html.required = true
        xml.destination = file("../../test/jacoco-reports/xml")
        html.destination = file("../../test/jacoco-reports/html")
    }
}

jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = 1.00
            }
        }
    }
}

test {
    finalizedBy jacocoTestReport
}

check.dependsOn test
```

## Configurer les tests

Ajouter dans le fichier `build.gradle`

```
task testUtil(type: Test) {
    useJUnitPlatform()
    include 'fr.em_ilien.util.*'
}

task testAgency(type: Test) {
    useJUnitPlatform()
    include 'fr.em_ilien.agency.*'
}
```

### Tester

Les commandes pour tester l'application sont :
- `gradle test` pour tester tout le logiciel
- `gradle testAgency` pour lancer toutes les classes de test dans le package agency
- `gradle testUtil` pour lancer toutes les classes de test dans le package util

### Consulter les rapports

Les rapports xml et html sont à la racine de repository git dans le dossier test/jacoco-reports. [Le rapport html](../test/jacoco-reports/html/index.html) peut être consulté depuis un navigateur web.

## GitHub Actions

Pour mettre en place un script GitHub Actions qui vérifie que tous les tests passent et que la couverture minimale de code est de 100% à chaque fois qu'un commit est effectué, il faut créer à la racine du projet un fichier `.github/workflows/gradle.yml` avec le contenu suivant :
```
name: Java CI with Gradle

on:
  push:
    branches: [ "main" ]
  pull_request:
    branches: [ "main" ]

jobs:
  build:
    runs-on: ubuntu-latest
    permissions:
      contents: read
    steps:
    - uses: actions/checkout@v4
    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'

    # Configure Gradle for optimal use in GiHub Actions, including caching of downloaded dependencies.
    # See: https://github.com/gradle/actions/blob/main/setup-gradle/README.md
    - name: Setup Gradle
      uses: gradle/actions/setup-gradle@417ae3ccd767c252f5661f1ace9f835f9654f2b5 # v3.1.0
      
    - name: Make gradlew executable
      run: chmod +x ./src/gradlew
      
    - name: Build with Gradle Wrapper
      run: |
        cd src
        ./gradlew build
```