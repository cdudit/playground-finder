![logo](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRRU84fh4D2XCx63P0rWYQWgJa4CelbP99BkFSBLeggVqt2vcR2rbolf6UFoOMz17t2Btg&usqp=CAU "Logo Ynov")

# Playground Finder

Playground Finder est une application qui permet de trouver les aires de jeux proches de sa position dans la ville de Bordeaux.

## Développeurs sur le projet

[Gatien Didry](https://github.com/gatienddr) et [Clément Dudit](https://github.com/cdudit)

## Installation

Télécharger ce dépôt et lancer l'application via Android Studio. Pour que la fonctionnalité "Carte" soit disponible, il est nécessaire d'avoir une clé d'API Google Maps à mettre dans le fichier `local.properties` sous la forme

```
MAPS_API_KEY=XXX
```

## Fonctionnalités disponibles

- Affichage des aires de jeux sur une carte
- Affichage des aires de jeux dans une liste
- Sur la liste : filtrer les aires de jeux en fonction de l'âge minimum ou maximum ainsi que rechercher une aire de jeux par son nom
- Information sur chaque aire de jeux (Age minimum et maximum, surface, nombre de jeux)
- Mise en favoris d’une ou de plusieurs aires de jeux
- Sauvegarde des favoris dans les SharedPreferences
- Redirection vers l'itinéraire entre ma position et l’aire de jeu
- Envoi par SMS du lien Google Maps de l’aire de jeu
