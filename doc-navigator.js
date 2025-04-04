/**
 * UniHub Documentation Navigator
 * 
 * Ce script facilite la navigation entre les différentes sections de documentation
 * d'UniHub en ajoutant des fonctionnalités de recherche et de navigation rapide.
 */

document.addEventListener('DOMContentLoaded', function() {
    // Configuration des liens de navigation
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

    // Ajouter la barre de navigation aux pages de documentation
    function insertNavigation() {
        // Détection de la page actuelle
        const currentPath = window.location.pathname;
        const isDevDoc = currentPath.includes('_DEV');
        const isFrenchDoc = currentPath.includes('.fr.');
        
        // Créer l'élément de navigation
        const navElement = document.createElement('div');
        navElement.className = 'doc-navigator';
        navElement.innerHTML = `
            <div class="doc-navigator-inner">
                <div class="doc-type-selector">
                    <button class="${!isDevDoc ? 'active' : ''}" data-doc-type="general">Documentation Générale</button>
                    <button class="${isDevDoc ? 'active' : ''}" data-doc-type="dev">Documentation Technique</button>
                </div>
                <div class="doc-lang-selector">
                    <button class="${!isFrenchDoc ? 'active' : ''}" data-lang="en">English</button>
                    <button class="${isFrenchDoc ? 'active' : ''}" data-lang="fr">Français</button>
                </div>
                <div class="doc-search">
                    <input type="text" placeholder="Rechercher dans la documentation..." id="doc-search-input">
                    <button id="doc-search-button">🔍</button>
                </div>
            </div>
        `;
        
        // Insérer en haut de la page
        document.body.insertBefore(navElement, document.body.firstChild);
        
        // Attacher les gestionnaires d'événements
        attachEventHandlers();
    }

    // Attacher les gestionnaires d'événements aux boutons de navigation
    function attachEventHandlers() {
        // Navigation entre types de documentation
        document.querySelectorAll('.doc-type-selector button').forEach(button => {
            button.addEventListener('click', function() {
                const docType = this.getAttribute('data-doc-type');
                const lang = document.querySelector('.doc-lang-selector button.active').getAttribute('data-lang');
                navigateToDoc(docType, lang);
            });
        });
        
        // Navigation entre langues
        document.querySelectorAll('.doc-lang-selector button').forEach(button => {
            button.addEventListener('click', function() {
                const lang = this.getAttribute('data-lang');
                const docType = document.querySelector('.doc-type-selector button.active').getAttribute('data-doc-type');
                navigateToDoc(docType, lang);
            });
        });
        
        // Recherche dans la documentation
        document.getElementById('doc-search-button').addEventListener('click', function() {
            searchDocs();
        });
        
        document.getElementById('doc-search-input').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                searchDocs();
            }
        });
    }

    // Naviguer vers le document spécifié
    function navigateToDoc(docType, lang) {
        window.location.href = docLinks[docType][lang];
    }

    // Rechercher dans la documentation
    function searchDocs() {
        const searchTerm = document.getElementById('doc-search-input').value.trim();
        if (searchTerm) {
            // Ici, vous pouvez implémenter une recherche plus avancée
            // Par exemple, en utilisant un index de recherche pré-construit
            
            // Pour cet exemple, nous effectuons une recherche simple dans la page actuelle
            window.find(searchTerm);
            
            // Analyser les résultats de recherche
            highlightSearchResults(searchTerm);
        }
    }

    // Mettre en évidence les résultats de recherche
    function highlightSearchResults(term) {
        // Réinitialiser les surbrillances précédentes
        document.querySelectorAll('.search-highlight').forEach(el => {
            el.outerHTML = el.innerHTML;
        });
        
        // Fonction récursive pour traiter les nœuds de texte
        function processNode(node) {
            if (node.nodeType === 3) { // Nœud texte
                const text = node.nodeValue;
                const regex = new RegExp(term, 'gi');
                
                if (regex.test(text)) {
                    const span = document.createElement('span');
                    span.innerHTML = text.replace(regex, match => `<span class="search-highlight">${match}</span>`);
                    node.parentNode.replaceChild(span, node);
                }
            } else if (node.nodeType === 1) { // Élément
                // Ne pas traiter les éléments de script, style, ou déjà surlignés
                if (!/^(script|style|noscript)$/i.test(node.tagName)) {
                    for (let i = 0; i < node.childNodes.length; i++) {
                        processNode(node.childNodes[i]);
                    }
                }
            }
        }
        
        // Commencer par le corps du document
        processNode(document.body);
    }

    // Ajouter des styles CSS pour le navigateur de documentation
    function addStyles() {
        const styleSheet = document.createElement('style');
        styleSheet.textContent = `
            .doc-navigator {
                position: sticky;
                top: 0;
                background: #f8f9fa;
                padding: 12px;
                border-bottom: 1px solid #e9ecef;
                z-index: 1000;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            
            .doc-navigator-inner {
                display: flex;
                justify-content: space-between;
                align-items: center;
                max-width: 1200px;
                margin: 0 auto;
            }
            
            .doc-type-selector, .doc-lang-selector {
                display: flex;
            }
            
            .doc-type-selector button, .doc-lang-selector button {
                background: #e9ecef;
                border: none;
                padding: 8px 16px;
                margin-right: 5px;
                border-radius: 4px;
                cursor: pointer;
                font-weight: 500;
            }
            
            .doc-type-selector button.active, .doc-lang-selector button.active {
                background: #0066cc;
                color: white;
            }
            
            .doc-search {
                display: flex;
            }
            
            .doc-search input {
                padding: 8px;
                border: 1px solid #ced4da;
                border-radius: 4px 0 0 4px;
                width: 240px;
            }
            
            .doc-search button {
                background: #0066cc;
                color: white;
                border: none;
                border-radius: 0 4px 4px 0;
                padding: 8px 12px;
                cursor: pointer;
            }
            
            .search-highlight {
                background-color: #ffff00;
                font-weight: bold;
            }
            
            @media (max-width: 768px) {
                .doc-navigator-inner {
                    flex-direction: column;
                    gap: 10px;
                }
                
                .doc-search {
                    width: 100%;
                }
                
                .doc-search input {
                    flex-grow: 1;
                }
            }
        `;
        document.head.appendChild(styleSheet);
    }

    // Initialiser le navigateur de documentation
    function init() {
        addStyles();
        insertNavigation();
    }

    // Lancer l'initialisation
    init();
}); 