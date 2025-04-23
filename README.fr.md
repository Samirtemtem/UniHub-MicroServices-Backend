# Plateforme UniHub - Documentation des Microservices

Cette documentation fournit des informations sur tous les microservices de la plateforme UniHub, leurs points d'accès (endpoints) et des exemples de corps de requêtes pour les tests.

## Table des matières
- [Aperçu](#aperçu)
- [Services et Points d'Accès](#services-et-points-daccès)
  - [Service de Découverte Eureka](#service-de-découverte-eureka)
  - [Passerelle API](#passerelle-api)
  - [Service Université](#service-université)
  - [Service Restaurant](#service-restaurant)
  - [Service Événement](#service-événement)
  - [Service Actualités](#service-actualités)
  - [Service Spécialité](#service-spécialité) 

## Documentation pour Développeurs

Pour les développeurs qui souhaitent des informations techniques plus détaillées sur les endpoints de chaque service, veuillez consulter la [Documentation développeur complète](./README_DEV.fr.md). Cette documentation contient :

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
5. Service Restaurant: http://localhost:8090
8. Service Événement: http://localhost:8087
9. Service Spécialité: http://localhost:8083
10. Service Actualités: http://localhost:8085

### URLs via la passerelle API
Alternativement, vous pouvez utiliser la passerelle API pour accéder à n'importe quel service avec les URLs suivantes:
1. Service Université: http://localhost:8082/universite/**
2. Service Spécialité: http://localhost:8082/specialites/**
5. Service Événement: http://localhost:8082/event/**
6. Service Restaurant: http://localhost:8082/restaurants/**
7. Service Actualités: http://localhost:8082/news/**

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

### Service Restaurant
- **URL de Base**: http://localhost:8090/restaurants
- **URL via Passerelle**: http://localhost:8082/restaurants
- **Description**: Gère les restaurants du campus.

#### Points d'accès:
1. **Obtenir tous les restaurants**
   - **Chemin**: `/`
   - **Méthode**: GET
   - **Description**: Récupère la liste de tous les restaurants.

2. **Obtenir un restaurant par ID**
   - **Chemin**: `/{id}`
   - **Méthode**: GET
   - **Description**: Récupère un restaurant par son ID.

3. **Créer un restaurant**
   - **Chemin**: `/`
   - **Méthode**: POST
   - **Description**: Crée un nouveau restaurant.
   - **Type de contenu**: `application/json`
   - **Exemple de corps de requête**:
     ```
     {
       "nomRestaurant": "Bistro du Campus",
       "specialite": "Italien",
       "menu": "Pâtes, Pizza, Salade"
     }
     ```

4. **Mettre à jour un restaurant**
   - **Chemin**: `/{id}`
   - **Méthode**: PUT
   - **Description**: Met à jour un restaurant existant.
   - **Type de contenu**: `application/json`
   - **Exemple de corps de requête**:
     ```
     {
       "nomRestaurant": "Nom Mis à Jour du Bistro",
       "specialite": "Méditerranéen",
       "menu": "Éléments de menu mis à jour"
     }
     ```

5. **Supprimer un restaurant**
   - **Chemin**: `/{id}`
   - **Méthode**: DELETE
   - **Description**: Supprime un restaurant par son ID.

6. **Télécharger une image**
   - **Chemin**: `/uploadImage/{id}`
   - **Méthode**: POST
   - **Description**: Télécharge une image pour un restaurant.
   - **Type de contenu**: `multipart/form-data`
   - **Paramètres**:
     - `fileImage`: Fichier image

7. **Obtenir une image**
   - **Chemin**: `/getImage/{fileName}`
   - **Méthode**: GET
   - **Description**: Récupère une image par son nom de fichier.

8. **Générer un code QR**
   - **Chemin**: `/qrCode/{id}`
   - **Méthode**: GET
   - **Description**: Génère un code QR pour un restaurant avec des détails.

9. **Bonjour**
   - **Chemin**: `/hello`
   - **Méthode**: GET
   - **Description**: Renvoie un message de bienvenue.

### Service Événement
- **URL de Base**: http://localhost:8087/event
- **URL via Passerelle**: http://localhost:8082/event
- **Description**: Gère les événements du campus.

#### Points d'accès:
1. **Obtenir tous les événements**
   - **Chemin**: `/events`
   - **Méthode**: GET
   - **Description**: Récupère la liste de tous les événements.

2. **Obtenir un événement par ID**
   - **Chemin**: `/getOneEvent/{id}`
   - **Méthode**: GET
   - **Description**: Récupère un événement par son ID.

3. **Ajouter un événement**
   - **Chemin**: `/addEvent`
   - **Méthode**: POST
   - **Description**: Crée un nouvel événement.
   - **Type de contenu**: `application/json`
   - **Exemple de corps de requête**:
     ```
     {
       "nomEvent": "Conférence Tech 2023",
       "descriptionEvent": "Conférence technologique annuelle avec ateliers",
       "dateEvent": "2023-12-15",
       "locationEvent": "Auditorium Principal"
     }
     ```

4. **Mettre à jour un événement**
   - **Chemin**: `/updateEvent`
   - **Méthode**: PUT
   - **Description**: Met à jour un événement existant.
   - **Type de contenu**: `application/json`
   - **Exemple de corps de requête**:
     ```
     {
       "id": 1,
       "nomEvent": "Nom Mis à Jour de la Conférence",
       "descriptionEvent": "Description mise à jour de l'événement",
       "dateEvent": "2023-12-20",
       "locationEvent": "Nouvel Emplacement"
     }
     ```

5. **Supprimer un événement**
   - **Chemin**: `/deleteEvent/{id}`
   - **Méthode**: DELETE
   - **Description**: Supprime un événement par son ID.

6. **Télécharger une image**
   - **Chemin**: `/events/uploadImage/{id}`
   - **Méthode**: POST
   - **Description**: Télécharge une image pour un événement.
   - **Type de contenu**: `multipart/form-data`
   - **Paramètres**:
     - `fileImage`: Fichier image

7. **Obtenir des événements triés par date (ascendant)**
   - **Chemin**: `/sortedByDateAsc`
   - **Méthode**: GET
   - **Description**: Récupère les événements triés par date en ordre ascendant.

8. **Obtenir des événements triés par date (descendant)**
   - **Chemin**: `/sortedByDateDesc`
   - **Méthode**: GET
   - **Description**: Récupère les événements triés par date en ordre descendant.

### Service Actualités
- **URL de Base**: http://localhost:8085/news
- **URL via Passerelle**: http://localhost:8082/news
- **Description**: Gère les articles d'actualités pour le campus.

#### Points d'accès:
1. **Obtenir toutes les actualités**
   - **Chemin**: `/`
   - **Méthode**: GET
   - **Description**: Récupère la liste de tous les articles d'actualités.

2. **Obtenir une actualité par ID**
   - **Chemin**: `/getOneActualite/{id}`
   - **Méthode**: GET
   - **Description**: Récupère un article d'actualité par son ID.

3. **Ajouter une actualité**
   - **Chemin**: `/addActualite`
   - **Méthode**: POST
   - **Description**: Crée un nouvel article d'actualité.
   - **Type de contenu**: `application/json`
   - **Exemple de corps de requête**:
     ```
     {
       "titre": "Nouveau Bâtiment sur le Campus",
       "description": "L'université ouvre un nouveau bâtiment technologique",
       "date": "2023-11-30"
     }
     ```

4. **Mettre à jour une actualité**
   - **Chemin**: `/updateActualite`
   - **Méthode**: PUT
   - **Description**: Met à jour un article d'actualité existant.
   - **Type de contenu**: `application/json`
   - **Exemple de corps de requête**:
     ```
     {
       "id": 1,
       "titre": "Titre Mis à Jour de l'Actualité",
       "description": "Contenu mis à jour de l'actualité",
       "date": "2023-12-01"
     }
     ```

5. **Supprimer une actualité**
   - **Chemin**: `/deleteActualite/{id}`
   - **Méthode**: DELETE
   - **Description**: Supprime un article d'actualité par son ID.

6. **Télécharger une image**
   - **Chemin**: `/uploadImage/{id}`
   - **Méthode**: POST
   - **Description**: Télécharge une image pour un article d'actualité.
   - **Type de contenu**: `multipart/form-data`
   - **Paramètres**:
     - `fileImage`: Fichier image

7. **Partager sur Facebook**
   - **Chemin**: `/shareFb/{id}`
   - **Méthode**: POST
   - **Description**: Partage un article d'actualité sur Facebook.

### Service Spécialité
- **URL de Base**: http://localhost:8083/specialites
- **URL via Passerelle**: http://localhost:8082/specialites
- **Description**: Gère les spécialisations académiques.

#### Points d'accès:
1. **Obtenir toutes les spécialités**
   - **Chemin**: `/`
   - **Méthode**: GET
   - **Description**: Récupère la liste de toutes les spécialisations.

2. **Obtenir une spécialité par ID**
   - **Chemin**: `/{id}`
   - **Méthode**: GET
   - **Description**: Récupère une spécialisation par son ID.

3. **Ajouter une spécialité**
   - **Chemin**: `/`
   - **Méthode**: POST
   - **Description**: Crée une nouvelle spécialisation.
   - **Type de contenu**: `application/json`
   - **Exemple de corps de requête**:
     ```
     {
       "nom": "Informatique",
       "description": "Étude des principes informatiques et du développement logiciel"
     }
     ```

4. **Mettre à jour une spécialité**
   - **Chemin**: `/`
   - **Méthode**: PUT
   - **Description**: Met à jour une spécialisation existante.
   - **Type de contenu**: `application/json`
   - **Exemple de corps de requête**:
     ```
     {
       "id": 1,
       "nom": "Nom Mis à Jour de la Spécialisation",
       "description": "Description mise à jour de la spécialisation"
     }
     ```

5. **Supprimer une spécialité**
   - **Chemin**: `/{id}`
   - **Méthode**: DELETE
   - **Description**: Supprime une spécialisation par son ID.

6. **Télécharger un PDF**
   - **Chemin**: `/uploadPdf/{id}`
   - **Méthode**: POST
   - **Description**: Télécharge un document PDF (ex: programme d'études) pour une spécialisation.
   - **Type de contenu**: `multipart/form-data`
   - **Paramètres**:
     - `file`: Fichier PDF

7. **Télécharger une image**
   - **Chemin**: `/uploadImage/{id}`
   - **Méthode**: POST
   - **Description**: Télécharge une image pour une spécialisation.
   - **Type de contenu**: `multipart/form-data`
   - **Paramètres**:
     - `fileImage`: Fichier image

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

Si vous travaillez sur l'intégration avec ces services ou si vous développez de nouvelles fonctionnalités, nous vous recommandons vivement de consulter notre [Documentation technique détaillée](./README_DEV.fr.md) qui fournit des informations plus approfondies sur chaque endpoint, incluant:

- Exemples complets de requêtes et réponses JSON
- Structure exacte des objets pour chaque service
- Détails sur le téléchargement et la gestion des fichiers
- Guide pour les fonctionnalités avancées et les filtres

Pour toute question technique, veuillez contacter l'équipe de développement UniHub. 