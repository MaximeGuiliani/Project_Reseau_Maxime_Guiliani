# Project_Reseau_Maxime_Guiliani

Reseau_Project

Project accessible sur github : https://github.com/MaximeGuiliani/Project_Reseau_Maxime_Guiliani.git
MicroBlog avec une base de donné qui stocke tout les messages, une autre qui stocke les tag et une autres les utilisateur.
Sur ce blog on peut voir toutes les requetes sur le server et faire de nombreuse requetes avec un client.

On peut lancer un server et faire les requete suivantes:

-receiveid : rid (recevoir les ids d'une recherche spécifique).
-publish : pub (publier un message).
-receivemsg : msg (recevoir un message selon un id).
-reply : reply (repondre a un message).
-republish : rt (republier un message ).

En cours :
Connecte qui permet a un client de se connecter et de recevoir tout les messages des personne qu'il suit

Dans Le dossier Project:

Pour utiliser le server :

    java -cp .:sqlite-jdbc-3.36.0.3.jar src.TCPServer

Pour compiler :

    javac src/_.java;javac src/RequestHandler/_.java;javac src/request/_.java;javac src/clients/_.java;

Pour lancer un client :

    java src.TCPClient localhost 12345
