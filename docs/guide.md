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

Dans le fichier build.gradle

- ajouter dans dependancies : testImplementation 'org.assertj:assertj-core:3.6.1'
- ajouter dans dependancies : 
	- testImplementation 'org.mockito:mockito-core:4.+'
	- testImplementation 'org.mockito:mockito-junit-jupiter:4.+'
	- testImplementation 'org.mockito:mockito-inline:4.6.1'

Pour ajouter Jacoco :
	- ajouter "id 'jacoco'" dans plugins
	- ajouter :
jacoco {
    toolVersion = "0.8.11"
}

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