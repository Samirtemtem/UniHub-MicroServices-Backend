# Plateforme UniHub - Documentation des Microservices

Cette documentation fournit des informations sur tous les microservices de la plateforme UniHub, leurs points d'accès (endpoints) et des exemples de corps de requêtes pour les tests.

## Table des matières
- [Aperçu](#aperçu)
- [Services et Points d'Accès](#services-et-points-daccès)
  - [Service de Découverte Eureka](#service-de-découverte-eureka)
  - [Passerelle API](#passerelle-api)
  - [Service Université](#service-université)


## Documentation pour Développeurs

Pour les développeurs qui souhaitent des informations techniques plus détaillées sur les endpoints de chaque service, veuillez consulter la [Documentation développeur complète](./README_DEV.md). Cette documentation contient :

- Descriptions détaillées de tous les endpoints
- Formats exacts des requêtes et réponses
- Exemples complets de payload JSON
- Instructions pour le téléchargement de fichiers
- Informations sur les filtres et les fonctionnalités spéciales

## Aperçu

La plateforme UniHub se compose de plusieurs microservices gérés via Eureka pour la découverte de services et une passerelle API pour le routage centralisé. Vous trouverez ci-dessous la liste des services disponibles et leurs URL d'accès.

### URLs directes des services
1. Eureka: http://localhost:8761
2. Passerelle API: http://localhost:8082
3. Service Université: http://localhost:8084


### URLs via la passerelle API
Alternativement, vous pouvez utiliser la passerelle API pour accéder à n'importe quel service avec les URLs suivantes:
1. Service Université: http://localhost:8082/universite/**


## Services et Points d'Accès

### Service de Découverte Eureka
- **URL**: http://localhost:8761
- **Description**: Registre de services pour surveiller tous les microservices.

### Passerelle API
- **URL**: http://localhost:8082
- **Description**: Point d'entrée central qui achemine les requêtes vers le microservice approprié.

### Service Université
- **URL de Base**: http://localhost:8084/universite
- **URL via Passerelle**: http://localhost:8082/universite
- **Description**: Gère les informations des universités.

#### Points d'accès:
1. **Obtenir toutes les universités**
   - **Chemin**: `/universites`
   - **Méthode**: GET
   - **Description**: Récupère la liste de toutes les universités.

2. **Obtenir une université par ID**
   - **Chemin**: `/universite/{id}`
   - **Méthode**: GET
   - **Description**: Récupère une université par son ID.

3. **Ajouter une université**
   - **Chemin**: `/adduniversite`
   - **Méthode**: POST
   - **Description**: Crée une nouvelle université.
   - **Type de contenu**: `multipart/form-data`
   - **Paramètres**:
     - `universite`: Données de l'université sous forme de champs
     - `file`: Fichier image (optionnel)
   - **Exemple de corps de requête**:
     ```
     {
       "nom": "Université de Technologie",
       "adresse": "123 Rue de la Technologie",
       "description": "Une université leader dans les domaines technologiques"
     }
     ```

4. **Mettre à jour une université**
   - **Chemin**: `/universite`
   - **Méthode**: PATCH
   - **Description**: Met à jour une université existante.
   - **Type de contenu**: `multipart/form-data`
   - **Paramètres**:
     - `universite`: Données de l'université incluant l'ID
     - `file`: Fichier image (optionnel)
   - **Exemple de corps de requête**:
     ```
     {
       "id": 1,
       "nom": "Nom Mis à Jour de l'Université",
       "adresse": "456 Nouvelle Adresse",
       "description": "Description mise à jour de l'université"
     }
     ```

5. **Supprimer une université**
   - **Chemin**: `/universite/{id}`
   - **Méthode**: DELETE
   - **Description**: Supprime une université par son ID.

6. **Rechercher des universités**
   - **Chemin**: `/searchUnivarsite/{nomUniversite}`
   - **Méthode**: GET
   - **Description**: Recherche des universités par nom.


   



## Exécution des Services

Vous pouvez exécuter tous les services en utilisant Docker Compose:

```bash
docker-compose up -d
```

Ou exécuter des services individuels:

```bash
docker-compose up -d eureka mysql-db news gateway universite event specialite restaurant
``` 

## Ressources supplémentaires pour développeurs

Si vous travaillez sur l'intégration avec ces services ou si vous développez de nouvelles fonctionnalités, nous vous recommandons vivement de consulter notre [Documentation technique détaillée](./README_DEV.md) qui fournit des informations plus approfondies sur chaque endpoint, incluant:

- Exemples complets de requêtes et réponses JSON
- Structure exacte des objets pour chaque service
- Détails sur le téléchargement et la gestion des fichiers
- Guide pour les fonctionnalités avancées et les filtres

Pour toute question technique, veuillez contacter l'équipe de développement UniHub. 
