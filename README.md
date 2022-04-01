# Project Reseau Maxime Guiliani L3 INFO Groupe 3 LUMINY

Maxime Guiliani

Project accessible sur github : https://github.com/MaximeGuiliani/Project_Reseau_Maxime_Guiliani.git

Objectif du projet :

Faire un MicroBlog avec une base de donné qui stocke tout les messages, une autre qui stocke les tag et une autres les utilisateur.
Sur ce blog on peut voir toutes les requetes sur le server et faire de nombreuse requetes avec un client.

On peut aussi se connecter avec un username ,voir les message des perssones que l’on suit et s’abonner et se désabonner de certaine personne

Pour utiliser le Blog :

1. Lancer un serveur
2. Lancer un ou plusieur client

Dans Le dossier Project:

Pour compiler :

$ javac src/flux/_.java; javac src/_.java;javac src/RequestHandler/_.java;javac src/request/_.java;javac src/clients/\*.java;

Pour utiliser le server :

$ java -cp .:sqlite-jdbc-3.36.0.3.jar src.TCPServer

Pour lancer un client :

$ java -cp .:sqlite-jdbc-3.36.0.3.jar src.TCPClient localhost 12345
Avec un client on peut utiliser les requêtes suivantes :

publish : laisse un utilisateur publier un message

receiveID: on recoit les ids des messages qui ont les attributs donné

receiveMSG : reçoit le message qui a l'id donné

reply : envoye un message en réponse d'un autre message

rt : republie un message

connect : connecte un utilisateur sur une machine ou il recoit les messages des personnes qu'il suit (pour l'instant les messages ne sont pas triés)
il peux aussi s'abonner avec la requete sub
se desabonner avec la requete unsub

NOTE : Toutes les données sont stocké dans une base de donnée sqlite

Etat final du projet:
Toutes les requete marche .
Pour les flux on peut se connecter mais la requete pour dire à la personne conecté qu’il faut mettre a jour ses messages ne marches pas car la valeur de la socket stocké est null et on peut donc pas envoyer de donné au client.

Conclusion :

Le blog permet de voir tout les messages publié precedement ainsi que de voir les messages des personnes qu’on suit, on peut aussi ajouter des messages au server.
