# Guide d'Implémentation de la Navigation entre les Documentations UniHub

Ce guide explique comment implémenter un système de navigation efficace entre les différentes documentations d'UniHub (documentation générale et documentation développeur) sur un site web.

## Vue d'ensemble

Pour améliorer l'expérience utilisateur et faciliter la navigation entre les documentations, nous avons créé deux éléments principaux :

1. Une interface de navigation HTML/CSS (`navigation-example.html`)
2. Un script JavaScript pour la navigation interactive (`doc-navigator.js`)

Ces composants peuvent être intégrés dans un site web existant ou utilisés comme base pour créer une interface de documentation personnalisée.

## Comment implémenter la navigation

### 1. Intégrer les fichiers de documentation

Assurez-vous que les fichiers de documentation suivants sont disponibles dans votre projet :

- `README.md` - Documentation générale (en anglais)
- `README_DEV.md` - Documentation technique pour les développeurs (en anglais)
- `README.fr.md` - Documentation générale (en français, si disponible)
- `README_DEV.fr.md` - Documentation technique (en français, si disponible)

### 2. Ajouter les composants de navigation

#### Option 1 : Utiliser l'interface HTML/CSS prédéfinie

1. Copiez le contenu de `navigation-example.html` dans votre page de documentation
2. Adaptez les styles selon votre charte graphique

#### Option 2 : Utiliser le script JavaScript interactif

1. Incluez le fichier `doc-navigator.js` dans votre page HTML :

```html
<script src="doc-navigator.js"></script>
```

2. Le script ajoutera automatiquement une barre de navigation en haut de la page avec :
   - Sélecteur de type de documentation (générale/technique)
   - Sélecteur de langue (français/anglais)
   - Fonction de recherche dans la documentation

### 3. Personnaliser les liens de navigation

Dans le fichier `doc-navigator.js`, modifiez la section suivante selon vos besoins :

```javascript
const docLinks = {
    general: {
        fr: 'README.fr.md',
        en: 'README.md'
    },
    dev: {
        fr: 'README_DEV.fr.md',
        en: 'README_DEV.md'
    }
};
```

### 4. Intégrer à GitHub Pages (optionnel)

Si vous souhaitez héberger la documentation sur GitHub Pages :

1. Installez [docsify](https://docsify.js.org/) ou [mkdocs](https://www.mkdocs.org/)
2. Configurez l'outil pour convertir les fichiers Markdown en HTML
3. Incluez le script de navigation dans le template

Exemple de configuration pour docsify :

```html
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>UniHub Documentation</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta name="description" content="UniHub Documentation">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
  <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/docsify@4/lib/themes/vue.css">
</head>
<body>
  <div id="app"></div>
  <script>
    window.$docsify = {
      name: 'UniHub Documentation',
      repo: 'https://github.com/yourusername/UniHub',
      loadSidebar: true,
      subMaxLevel: 2,
      search: {
        maxAge: 86400000,
        paths: 'auto',
        placeholder: 'Recherche',
        noData: 'Aucun résultat',
        depth: 6
      }
    }
  </script>
  <script src="//cdn.jsdelivr.net/npm/docsify@4"></script>
  <script src="//cdn.jsdelivr.net/npm/docsify/lib/plugins/search.min.js"></script>
  <script src="doc-navigator.js"></script>
</body>
</html>
```

## Captures d'écran

### Navigation entre documentations
![Navigation entre documentations](https://via.placeholder.com/800x200/f8f9fa/0066cc?text=Navigation+entre+documentations)

### Recherche dans la documentation
![Recherche dans la documentation](https://via.placeholder.com/800x300/f8f9fa/0066cc?text=Recherche+dans+la+documentation)

## Bonnes pratiques

1. **Cohérence** : Utilisez les mêmes conventions de style et de structure dans toutes les documentations
2. **Responsive** : Assurez-vous que la navigation fonctionne bien sur les appareils mobiles
3. **Accessibilité** : Ajoutez des attributs ARIA appropriés pour l'accessibilité
4. **Performance** : Optimisez les scripts pour ne pas ralentir le chargement de la page

## Ressources supplémentaires

- [Site officiel de UniHub](https://example.com/unihub)
- [GitHub Repository](https://github.com/example/unihub)
- [Guide des contributeurs](https://example.com/unihub/contributing) 