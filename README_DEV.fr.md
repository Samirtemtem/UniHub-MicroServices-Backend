# UniHub - Documentation pour Développeurs

> 📚 **Navigation :** [← Retour à la documentation générale](./README.fr.md) | Cette documentation technique détaillée est destinée aux développeurs travaillant sur l'intégration ou l'extension des services UniHub.

Ce document fournit des informations détaillées sur les points d'accès API pour chaque microservice dans le système UniHub. Il inclut les formats de requête/réponse et des exemples pour faciliter le développement frontend et les tests d'API.

## Table des matières
- [Passerelle API](#passerelle-api)
- [Service Actualités](#service-actualités)
- [Service Événement](#service-événement)
- [Service Restaurant](#service-restaurant)
- [Service Spécialité](#service-spécialité)
- [Service Université](#service-université)

## Passerelle API

La passerelle API sert de point d'entrée pour toutes les requêtes client, les acheminant vers les microservices appropriés. Tous les points d'accès sont accessibles via cette passerelle.

- **URL de base**: `http://localhost:8082`

## Service Actualités

Gère les actualités et les annonces pour la plateforme.

- **URL de base**: `http://localhost:8085/news`
- **URL via passerelle**: `http://localhost:8082/news`

### Points d'accès

#### 1. Obtenir toutes les actualités

- **Chemin**: `/`
- **Méthode**: `GET`
- **Description**: Récupère une liste de tous les articles d'actualité.
- **Réponse**: Tableau d'articles d'actualité

```json
[
  {
    "idActualite": 1,
    "titreActualite": "Ouverture d'un nouveau campus",
    "descriptionActualite": "Actualités passionnantes concernant l'ouverture de notre nouveau campus le mois prochain...",
    "dateActualite": "2023-11-20T10:30:00.000+00:00",
    "imageActualite": "campus_opening.jpg"
  },
  {
    "idActualite": 2,
    "titreActualite": "Salon de l'emploi à venir",
    "descriptionActualite": "Rejoignez-nous pour le salon annuel de l'emploi avec les meilleurs employeurs...",
    "dateActualite": "2023-11-25T09:00:00.000+00:00",
    "imageActualite": "career_fair.jpg"
  }
]
```

#### 2. Obtenir une actualité par ID

- **Chemin**: `/getOneActualite/{id}`
- **Méthode**: `GET`
- **Description**: Récupère un article d'actualité spécifique par son ID.
- **Paramètres**:
  - `id` (paramètre de chemin): ID de l'article d'actualité
- **Réponse**: Un seul article d'actualité

```json
{
  "idActualite": 1,
  "titreActualite": "Ouverture d'un nouveau campus",
  "descriptionActualite": "Actualités passionnantes concernant l'ouverture de notre nouveau campus le mois prochain...",
  "dateActualite": "2023-11-20T10:30:00.000+00:00",
  "imageActualite": "campus_opening.jpg"
}
```

#### 3. Ajouter une actualité

- **Chemin**: `/addActualite`
- **Méthode**: `POST`
- **Description**: Crée un nouvel article d'actualité.
- **Type de contenu**: `application/json`
- **Corps de la requête**:

```json
{
  "titreActualite": "Inscription au programme d'été",
  "descriptionActualite": "L'inscription au programme d'été est maintenant ouverte. Rejoignez-nous pour un été passionnant d'apprentissage et d'activités.",
  "dateActualite": "2023-12-01T08:00:00.000+00:00"
}
```

- **Réponse**: Article d'actualité créé avec ID

```json
{
  "idActualite": 3,
  "titreActualite": "Inscription au programme d'été",
  "descriptionActualite": "L'inscription au programme d'été est maintenant ouverte. Rejoignez-nous pour un été passionnant d'apprentissage et d'activités.",
  "dateActualite": "2023-12-01T08:00:00.000+00:00",
  "imageActualite": null
}
```

#### 4. Mettre à jour une actualité

- **Chemin**: `/updateActualite`
- **Méthode**: `PUT`
- **Description**: Met à jour un article d'actualité existant.
- **Type de contenu**: `application/json`
- **Corps de la requête**:

```json
{
  "idActualite": 3,
  "titreActualite": "Mise à jour: Inscription au programme d'été",
  "descriptionActualite": "Date limite d'inscription prolongée pour le programme d'été. Rejoignez-nous pour un été passionnant d'apprentissage et d'activités.",
  "dateActualite": "2023-12-05T08:00:00.000+00:00"
}
```

- **Réponse**: Article d'actualité mis à jour

```json
{
  "idActualite": 3,
  "titreActualite": "Mise à jour: Inscription au programme d'été",
  "descriptionActualite": "Date limite d'inscription prolongée pour le programme d'été. Rejoignez-nous pour un été passionnant d'apprentissage et d'activités.",
  "dateActualite": "2023-12-05T08:00:00.000+00:00",
  "imageActualite": "previous_image.jpg"
}
```

#### 5. Supprimer une actualité

- **Chemin**: `/deleteActualite/{id}`
- **Méthode**: `DELETE`
- **Description**: Supprime un article d'actualité par son ID.
- **Paramètres**:
  - `id` (paramètre de chemin): ID de l'article d'actualité à supprimer
- **Réponse**: Message de succès

```json
"Actualité supprimée avec succès"
```

#### 6. Télécharger une image pour une actualité

- **Chemin**: `/uploadImage/{id}`
- **Méthode**: `POST`
- **Description**: Télécharge une image pour un article d'actualité.
- **Type de contenu**: `multipart/form-data`
- **Paramètres**:
  - `id` (paramètre de chemin): ID de l'article d'actualité
  - `fileImage` (données de formulaire): Fichier image à télécharger
- **Réponse**: Article d'actualité mis à jour avec les informations de l'image

```json
{
  "idActualite": 3,
  "titreActualite": "Inscription au programme d'été",
  "descriptionActualite": "L'inscription au programme d'été est maintenant ouverte. Rejoignez-nous pour un été passionnant d'apprentissage et d'activités.",
  "dateActualite": "2023-12-01T08:00:00.000+00:00",
  "imageActualite": "1701432567890-summer_program.jpg"
}
```

#### 7. Partager une actualité sur Facebook

- **Chemin**: `/shareFb/{id}`
- **Méthode**: `POST`
- **Description**: Partage un article d'actualité sur Facebook.
- **Paramètres**:
  - `id` (paramètre de chemin): ID de l'article d'actualité à partager
- **Réponse**: Message de statut

```json
"Message de statut publié avec succès."
```

## Service Événement

Gère les événements et activités du campus.

- **URL de base**: `http://localhost:8087/event`
- **URL via passerelle**: `http://localhost:8082/event`

### Points d'accès

#### 1. Obtenir tous les événements

- **Chemin**: `/events`
- **Méthode**: `GET`
- **Description**: Récupère une liste de tous les événements.
- **Réponse**: Tableau d'événements

```json
[
  {
    "idEvent": 1,
    "nomEvent": "Salon annuel de l'emploi",
    "descriptionEvent": "Rencontrez des représentants d'entreprises de premier plan",
    "dateDebEvent": "2023-12-10T09:00:00.000+00:00",
    "dateFinEvent": "2023-12-10T17:00:00.000+00:00",
    "lieuEvent": "Campus universitaire, Hall A",
    "imageEvent": "career_fair.jpg"
  },
  {
    "idEvent": 2,
    "nomEvent": "Orientation des étudiants",
    "descriptionEvent": "Événement d'accueil pour les nouveaux étudiants",
    "dateDebEvent": "2023-12-15T10:00:00.000+00:00",
    "dateFinEvent": "2023-12-15T15:00:00.000+00:00",
    "lieuEvent": "Auditorium universitaire",
    "imageEvent": "orientation.jpg"
  }
]
```

#### 2. Obtenir un événement par ID

- **Chemin**: `/getOneEvent/{id}`
- **Méthode**: `GET`
- **Description**: Récupère un événement spécifique par son ID.
- **Paramètres**:
  - `id` (paramètre de chemin): ID de l'événement
- **Réponse**: Un seul événement

```json
{
  "idEvent": 1,
  "nomEvent": "Salon annuel de l'emploi",
  "descriptionEvent": "Rencontrez des représentants d'entreprises de premier plan",
  "dateDebEvent": "2023-12-10T09:00:00.000+00:00",
  "dateFinEvent": "2023-12-10T17:00:00.000+00:00",
  "lieuEvent": "Campus universitaire, Hall A",
  "imageEvent": "career_fair.jpg"
}
```

#### 3. Ajouter un événement

- **Chemin**: `/addEvent`
- **Méthode**: `POST`
- **Description**: Crée un nouvel événement.
- **Type de contenu**: `application/json`
- **Corps de la requête**:

```json
{
  "nomEvent": "Atelier Technologique",
  "descriptionEvent": "Atelier pratique sur les dernières technologies",
  "dateDebEvent": "2023-12-20T13:00:00.000+00:00",
  "dateFinEvent": "2023-12-20T16:00:00.000+00:00",
  "lieuEvent": "Laboratoire informatique Bâtiment B"
}
```

- **Réponse**: Événement créé avec ID

```json
{
  "idEvent": 3,
  "nomEvent": "Atelier Technologique",
  "descriptionEvent": "Atelier pratique sur les dernières technologies",
  "dateDebEvent": "2023-12-20T13:00:00.000+00:00",
  "dateFinEvent": "2023-12-20T16:00:00.000+00:00",
  "lieuEvent": "Laboratoire informatique Bâtiment B",
  "imageEvent": null
}
```

#### 4. Mettre à jour un événement

- **Chemin**: `/updateEvent`
- **Méthode**: `PUT`
- **Description**: Met à jour un événement existant.
- **Type de contenu**: `application/json`
- **Corps de la requête**:

```json
{
  "idEvent": 3,
  "nomEvent": "Atelier Technologique Avancé",
  "descriptionEvent": "Atelier pratique sur les technologies avancées et leurs applications",
  "dateDebEvent": "2023-12-22T13:00:00.000+00:00",
  "dateFinEvent": "2023-12-22T17:00:00.000+00:00",
  "lieuEvent": "Laboratoire informatique Bâtiment B, Salle 204"
}
```

- **Réponse**: Événement mis à jour

```json
{
  "idEvent": 3,
  "nomEvent": "Atelier Technologique Avancé",
  "descriptionEvent": "Atelier pratique sur les technologies avancées et leurs applications",
  "dateDebEvent": "2023-12-22T13:00:00.000+00:00",
  "dateFinEvent": "2023-12-22T17:00:00.000+00:00",
  "lieuEvent": "Laboratoire informatique Bâtiment B, Salle 204",
  "imageEvent": "workshop.jpg"
}
```

#### 5. Supprimer un événement

- **Chemin**: `/deleteEvent/{id}`
- **Méthode**: `DELETE`
- **Description**: Supprime un événement par son ID.
- **Paramètres**:
  - `id` (paramètre de chemin): ID de l'événement à supprimer
- **Réponse**: Pas de contenu (204)

#### 6. Télécharger une image pour un événement

- **Chemin**: `/events/uploadImage/{id}`
- **Méthode**: `POST`
- **Description**: Télécharge une image pour un événement.
- **Type de contenu**: `multipart/form-data`
- **Paramètres**:
  - `id` (paramètre de chemin): ID de l'événement
  - `fileImage` (données de formulaire): Fichier image à télécharger
- **Réponse**: Événement mis à jour avec les informations de l'image

```json
{
  "idEvent": 3,
  "nomEvent": "Atelier Technologique Avancé",
  "descriptionEvent": "Atelier pratique sur les technologies avancées et leurs applications",
  "dateDebEvent": "2023-12-22T13:00:00.000+00:00",
  "dateFinEvent": "2023-12-22T17:00:00.000+00:00",
  "lieuEvent": "Laboratoire informatique Bâtiment B, Salle 204",
  "imageEvent": "1701433567890-tech_workshop.jpg"
}
```

#### 7. Obtenir des événements triés par date (ordre croissant)

- **Chemin**: `/sortedByDateAsc`
- **Méthode**: `GET`
- **Description**: Récupère tous les événements triés par date de début en ordre croissant.
- **Réponse**: Tableau d'événements triés par date

```json
[
  {
    "idEvent": 1,
    "nomEvent": "Salon annuel de l'emploi",
    "descriptionEvent": "Rencontrez des représentants d'entreprises de premier plan",
    "dateDebEvent": "2023-12-10T09:00:00.000+00:00",
    "dateFinEvent": "2023-12-10T17:00:00.000+00:00",
    "lieuEvent": "Campus universitaire, Hall A",
    "imageEvent": "career_fair.jpg"
  },
  {
    "idEvent": 2,
    "nomEvent": "Orientation des étudiants",
    "descriptionEvent": "Événement d'accueil pour les nouveaux étudiants",
    "dateDebEvent": "2023-12-15T10:00:00.000+00:00",
    "dateFinEvent": "2023-12-15T15:00:00.000+00:00",
    "lieuEvent": "Auditorium universitaire",
    "imageEvent": "orientation.jpg"
  },
  {
    "idEvent": 3,
    "nomEvent": "Atelier Technologique Avancé",
    "descriptionEvent": "Atelier pratique sur les technologies avancées et leurs applications",
    "dateDebEvent": "2023-12-22T13:00:00.000+00:00",
    "dateFinEvent": "2023-12-22T17:00:00.000+00:00",
    "lieuEvent": "Laboratoire informatique Bâtiment B, Salle 204",
    "imageEvent": "tech_workshop.jpg"
  }
]
```

#### 8. Obtenir des événements triés par date (ordre décroissant)

- **Chemin**: `/sortedByDateDesc`
- **Méthode**: `GET`
- **Description**: Récupère tous les événements triés par date de début en ordre décroissant.
- **Réponse**: Tableau d'événements triés par date (les plus récents d'abord)

```json
[
  {
    "idEvent": 3,
    "nomEvent": "Atelier Technologique Avancé",
    "descriptionEvent": "Atelier pratique sur les technologies avancées et leurs applications",
    "dateDebEvent": "2023-12-22T13:00:00.000+00:00",
    "dateFinEvent": "2023-12-22T17:00:00.000+00:00",
    "lieuEvent": "Laboratoire informatique Bâtiment B, Salle 204",
    "imageEvent": "tech_workshop.jpg"
  },
  {
    "idEvent": 2,
    "nomEvent": "Orientation des étudiants",
    "descriptionEvent": "Événement d'accueil pour les nouveaux étudiants",
    "dateDebEvent": "2023-12-15T10:00:00.000+00:00",
    "dateFinEvent": "2023-12-15T15:00:00.000+00:00",
    "lieuEvent": "Auditorium universitaire",
    "imageEvent": "orientation.jpg"
  },
  {
    "idEvent": 1,
    "nomEvent": "Salon annuel de l'emploi",
    "descriptionEvent": "Rencontrez des représentants d'entreprises de premier plan",
    "dateDebEvent": "2023-12-10T09:00:00.000+00:00",
    "dateFinEvent": "2023-12-10T17:00:00.000+00:00",
    "lieuEvent": "Campus universitaire, Hall A",
    "imageEvent": "career_fair.jpg"
  }
]
```

## Service Restaurant

Gère les restaurants et les menus universitaires.

- **URL de base**: `http://localhost:8083/restaurant`
- **URL via passerelle**: `http://localhost:8082/restaurant`

### Points d'accès

#### 1. Obtenir tous les restaurants

- **Chemin**: `/restaurants`
- **Méthode**: `GET`
- **Description**: Récupère une liste de tous les restaurants.
- **Réponse**: Tableau de restaurants

```json
[
  {
    "idRestaurant": 1,
    "nomRestaurant": "Cantine Principale",
    "capaciteRestaurant": 200,
    "emplacementRestaurant": "Bâtiment Central",
    "imageResto": "main_canteen.jpg"
  },
  {
    "idRestaurant": 2,
    "nomRestaurant": "Café Étudiant",
    "capaciteRestaurant": 100,
    "emplacementRestaurant": "Aile Est",
    "imageResto": "student_cafe.jpg"
  }
]
```

#### 2. Obtenir un restaurant par ID

- **Chemin**: `/restaurants/{id}`
- **Méthode**: `GET`
- **Description**: Récupère un restaurant spécifique par son ID.
- **Paramètres**:
  - `id` (paramètre de chemin): ID du restaurant
- **Réponse**: Un seul restaurant

```json
{
  "idRestaurant": 1,
  "nomRestaurant": "Cantine Principale",
  "capaciteRestaurant": 200,
  "emplacementRestaurant": "Bâtiment Central",
  "imageResto": "main_canteen.jpg"
}
```

#### 3. Ajouter un restaurant

- **Chemin**: `/restaurants`
- **Méthode**: `POST`
- **Description**: Crée un nouveau restaurant.
- **Type de contenu**: `application/json`
- **Corps de la requête**:

```json
{
  "nomRestaurant": "Bistro Technologique",
  "capaciteRestaurant": 75,
  "emplacementRestaurant": "Département Informatique"
}
```

- **Réponse**: Restaurant créé avec ID

```json
{
  "idRestaurant": 3,
  "nomRestaurant": "Bistro Technologique",
  "capaciteRestaurant": 75,
  "emplacementRestaurant": "Département Informatique",
  "imageResto": null
}
```

#### 4. Mettre à jour un restaurant

- **Chemin**: `/restaurants/{id}`
- **Méthode**: `PUT`
- **Description**: Met à jour un restaurant existant.
- **Paramètres**:
  - `id` (paramètre de chemin): ID du restaurant à mettre à jour
- **Type de contenu**: `application/json`
- **Corps de la requête**:

```json
{
  "nomRestaurant": "Bistro Tech & Innovation",
  "capaciteRestaurant": 85,
  "emplacementRestaurant": "Département Informatique, Étage 2"
}
```

- **Réponse**: Restaurant mis à jour

```json
{
  "idRestaurant": 3,
  "nomRestaurant": "Bistro Tech & Innovation",
  "capaciteRestaurant": 85,
  "emplacementRestaurant": "Département Informatique, Étage 2",
  "imageResto": "tech_bistro.jpg"
}
```

#### 5. Supprimer un restaurant

- **Chemin**: `/restaurants/{id}`
- **Méthode**: `DELETE`
- **Description**: Supprime un restaurant par son ID.
- **Paramètres**:
  - `id` (paramètre de chemin): ID du restaurant à supprimer
- **Réponse**: Pas de contenu (204)

#### 6. Filtrer les restaurants par capacité minimale

- **Chemin**: `/restaurants/filter/capacity/{minCapacity}`
- **Méthode**: `GET`
- **Description**: Récupère les restaurants avec une capacité minimale spécifiée.
- **Paramètres**:
  - `minCapacity` (paramètre de chemin): Capacité minimale requise
- **Réponse**: Tableau de restaurants filtré

```json
[
  {
    "idRestaurant": 1,
    "nomRestaurant": "Cantine Principale",
    "capaciteRestaurant": 200,
    "emplacementRestaurant": "Bâtiment Central",
    "imageResto": "main_canteen.jpg"
  },
  {
    "idRestaurant": 2,
    "nomRestaurant": "Café Étudiant",
    "capaciteRestaurant": 100,
    "emplacementRestaurant": "Aile Est",
    "imageResto": "student_cafe.jpg"
  }
]
```

#### 7. Télécharger une image pour un restaurant

- **Chemin**: `/restaurants/upload/{id}`
- **Méthode**: `POST`
- **Description**: Télécharge une image pour un restaurant.
- **Type de contenu**: `multipart/form-data`
- **Paramètres**:
  - `id` (paramètre de chemin): ID du restaurant
  - `file` (données de formulaire): Fichier image à télécharger
- **Réponse**: Restaurant mis à jour avec les informations de l'image

```json
{
  "idRestaurant": 3,
  "nomRestaurant": "Bistro Tech & Innovation",
  "capaciteRestaurant": 85,
  "emplacementRestaurant": "Département Informatique, Étage 2",
  "imageResto": "1701435678901-tech_bistro.jpg"
}
```

## Service Spécialité

Gère les spécialités et les formations universitaires.

- **URL de base**: `http://localhost:8084/speciality`
- **URL via passerelle**: `http://localhost:8082/speciality`

### Points d'accès

#### 1. Obtenir toutes les spécialités

- **Chemin**: `/specialities`
- **Méthode**: `GET`
- **Description**: Récupère une liste de toutes les spécialités.
- **Réponse**: Tableau de spécialités

```json
[
  {
    "idSpeciality": 1,
    "nomSpeciality": "Informatique",
    "descriptionSpeciality": "Spécialité en informatique et technologies de l'information",
    "imageSpeciality": "informatics.jpg"
  },
  {
    "idSpeciality": 2,
    "nomSpeciality": "Biologie",
    "descriptionSpeciality": "Spécialité en biologie et sciences de la vie",
    "imageSpeciality": "biology.jpg"
  }
]
```

#### 2. Obtenir une spécialité par ID

- **Chemin**: `/specialities/{id}`
- **Méthode**: `GET`
- **Description**: Récupère une spécialité spécifique par son ID.
- **Paramètres**:
  - `id` (paramètre de chemin): ID de la spécialité
- **Réponse**: Un seul article de spécialité

```json
{
  "idSpeciality": 1,
  "nomSpeciality": "Informatique",
  "descriptionSpeciality": "Spécialité en informatique et technologies de l'information",
  "imageSpeciality": "informatics.jpg"
}
```

#### 3. Ajouter une spécialité

- **Chemin**: `/specialities`
- **Méthode**: `POST`
- **Description**: Crée une nouvelle spécialité.
- **Type de contenu**: `application/json`
- **Corps de la requête**:

```json
{
  "nomSpeciality": "Génie Civil",
  "descriptionSpeciality": "Spécialité en génie civil et construction",
  "imageSpeciality": "civil_engineering.jpg"
}
```

- **Réponse**: Spécialité créée avec ID

```json
{
  "idSpeciality": 3,
  "nomSpeciality": "Génie Civil",
  "descriptionSpeciality": "Spécialité en génie civil et construction",
  "imageSpeciality": "civil_engineering.jpg"
}
```

#### 4. Mettre à jour une spécialité

- **Chemin**: `/specialities/{id}`
- **Méthode**: `PUT`
- **Description**: Met à jour une spécialité existante.
- **Paramètres**:
  - `id` (paramètre de chemin): ID de la spécialité à mettre à jour
- **Type de contenu**: `application/json`
- **Corps de la requête**:

```json
{
  "nomSpeciality": "Génie Civil Avancé",
  "descriptionSpeciality": "Spécialité en génie civil et construction avancée",
  "imageSpeciality": "advanced_civil_engineering.jpg"
}
```

- **Réponse**: Spécialité mis à jour

```json
{
  "idSpeciality": 3,
  "nomSpeciality": "Génie Civil Avancé",
  "descriptionSpeciality": "Spécialité en génie civil et construction avancée",
  "imageSpeciality": "advanced_civil_engineering.jpg"
}
```

#### 5. Supprimer une spécialité

- **Chemin**: `/specialities/{id}`
- **Méthode**: `DELETE`
- **Description**: Supprime une spécialité par son ID.
- **Paramètres**:
  - `id` (paramètre de chemin): ID de la spécialité à supprimer
- **Réponse**: Pas de contenu (204)

#### 6. Télécharger une image pour une spécialité

- **Chemin**: `/specialities/upload/{id}`
- **Méthode**: `POST`
- **Description**: Télécharge une image pour une spécialité.
- **Type de contenu**: `multipart/form-data`
- **Paramètres**:
  - `id` (paramètre de chemin): ID de la spécialité
  - `file` (données de formulaire): Fichier image à télécharger
- **Réponse**: Spécialité mis à jour avec les informations de l'image

```json
{
  "idSpeciality": 3,
  "nomSpeciality": "Génie Civil Avancé",
  "descriptionSpeciality": "Spécialité en génie civil et construction avancée",
  "imageSpeciality": "1701436789012-advanced_civil_engineering.jpg"
}
```

## Service Université

Gère les informations et les services universitaires.

- **URL de base**: `http://localhost:8086/university`
- **URL via passerelle**: `http://localhost:8082/university`

### Points d'accès

#### 1. Obtenir toutes les universités

- **Chemin**: `/universities`
- **Méthode**: `GET`
- **Description**: Récupère une liste de toutes les universités.
- **Réponse**: Tableau d'universités

```json
[
  {
    "idUniversity": 1,
    "nomUniversity": "Université de Technologie de Belfort-Montbéliard",
    "villeUniversity": "Belfort",
    "imageUniversity": "utbm.jpg"
  },
  {
    "idUniversity": 2,
    "nomUniversity": "Université de Technologie de Nancy",
    "villeUniversity": "Nancy",
    "imageUniversity": "utn.jpg"
  }
]
```

#### 2. Obtenir une université par ID

- **Chemin**: `/universities/{id}`
- **Méthode**: `GET`
- **Description**: Récupère une université spécifique par son ID.
- **Paramètres**:
  - `id` (paramètre de chemin): ID de l'université
- **Réponse**: Un seul article d'université

```json
{
  "idUniversity": 1,
  "nomUniversity": "Université de Technologie de Belfort-Montbéliard",
  "villeUniversity": "Belfort",
  "imageUniversity": "utbm.jpg"
}
```

#### 3. Ajouter une université

- **Chemin**: `/universities`
- **Méthode**: `POST`
- **Description**: Crée une nouvelle université.
- **Type de contenu**: `application/json`
- **Corps de la requête**:

```json
{
  "nomUniversity": "Université de Technologie de Metz",
  "villeUniversity": "Metz",
  "imageUniversity": "utm.jpg"
}
```

- **Réponse**: Université créée avec ID

```json
{
  "idUniversity": 3,
  "nomUniversity": "Université de Technologie de Metz",
  "villeUniversity": "Metz",
  "imageUniversity": "utm.jpg"
}
```

#### 4. Mettre à jour une université

- **Chemin**: `/universities/{id}`
- **Méthode**: `PUT`
- **Description**: Met à jour une université existante.
- **Paramètres**:
  - `id` (paramètre de chemin): ID de l'université à mettre à jour
- **Type de contenu**: `application/json`
- **Corps de la requête**:

```json
{
  "nomUniversity": "Université de Technologie de Metz",
  "villeUniversity": "Metz",
  "imageUniversity": "utm.jpg"
}
```

- **Réponse**: Université mis à jour

```json
{
  "idUniversity": 3,
  "nomUniversity": "Université de Technologie de Metz",
  "villeUniversity": "Metz",
  "imageUniversity": "utm.jpg"
}
```

#### 5. Supprimer une université

- **Chemin**: `/universities/{id}`
- **Méthode**: `DELETE`
- **Description**: Supprime une université par son ID.
- **Paramètres**:
  - `id` (paramètre de chemin): ID de l'université à supprimer
- **Réponse**: Pas de contenu (204)

#### 6. Télécharger une image pour une université

- **Chemin**: `/universities/upload/{id}`
- **Méthode**: `POST`
- **Description**: Télécharge une image pour une université.
- **Type de contenu**: `multipart/form-data`
- **Paramètres**:
  - `id` (paramètre de chemin): ID de l'université
  - `file` (données de formulaire): Fichier image à télécharger
- **Réponse**: Université mis à jour avec les informations de l'image

```json
{
  "idUniversity": 3,
  "nomUniversity": "Université de Technologie de Metz",
  "villeUniversity": "Metz",
  "imageUniversity": "1701437890123-utm.jpg"
}
```