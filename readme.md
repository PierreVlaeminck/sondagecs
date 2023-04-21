## Projet Client Service de gestion de Sondages

### 1 - Description du projet

Création du client Web, celui-ci passe par un Web service pour récupérer les données à afficher sur le site,  
il est possible via ce projet de créer, modifier ou supprimer des sondages.

### 2 - Installation du projet :

#### Logiciel requis :
- Intellij
- Google Chrome
- Aucune configuration particulière requise au niveau matériel
- 
#### Lancement du projet :
Vous aurez besoin pour que ce projet fonctionne d'avoir déjà récupéré la partie WEB service à l'adresse GitHub cité plus haut,
il faudra alors le lancer en premier, pour ensuite lancer la partie client contenu dans ce Repositories.  
Vous pouvez venir le modifier dans le fichier "application.properties", il suffit pour cela d'ajouter :  
server.port="numéro de port".  
Pour acceder au projet passé par l'URL suivante : http://localhost:8093

### 3 - Pages du site :
Ce projet ne contient que le service coté client, vous aurez besoin de la partie web service qui se trouve sur mon GitHub,
voici le Repositories concerné :  
https://github.com/PierreVlaeminck/sondagesw

**index.html** : affichage de tous les sondages présent dans votre base de donnée.  
**sondages.html** : affiche les informations d'un seul sondage, avec la possibilité de modifier ou supprimer le sondage.  
**form.html** : permet la création d'un nouveau sondage qui se sauvegarde dans la base de donnée, 
**celui-ci** est aussi repris lorsque l'on clique sur modifier et affiche toutes les informations du sondage actuel,
que l'on peut alors modifier.

### 4 - Outils de réalisation :
Code réalisé avec : Intellij  
Langage utilisés : HTML, CSS, JAVA  
Outils collaboratifs : GitHub  
Framework utilisé : Spring Boot  
Navigateur utilisé : Google Chrome

#### Bibliothèques utilisées :
- Spring Boot DevTools
- Spring Web
- Thymeleaf

Merci d'avoir pris le temp de lire le ReadMe.