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