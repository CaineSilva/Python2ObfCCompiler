Cette archive contient : 
 - La documentation complète du code (au format html) dans le 
   dossier ./doc.
 - Le code source dans le dossier ./src
 - Des fichiers python pour tester le code dans le dossier 
   ./test_files
 - Un script d'exécution ./run

Le dossier ./.idea et le fichier ./Python2ObfCCompiler.iml sont utile 
si vous souhaitez ouvrir le projet avec l'IDE IntelliJ.

###################################
| Le projet : Python2ObfCCompiler |
###################################

Ce projet a été proposé dans le cadre de la formation d'ingénieur 
de l'ENSTA Bretagne et a été réalisé par Caine SILVA et Riwan CUINAT.

Le but de ce projet est de convertir un code python en :
 - un code C
 - un code C obfusqué

###########################################
| Le langage de départ : Un python allégé |
###########################################

Bien entendu, nous avons travaillé sur un sous langage du
python au vu du temps imparti pour le projet.
Nous considérons :
 - Les variables (entières, flottantes ou chaîne de caractère)
   Celles-ci ne doivent pas changer de type au cours de l'exécution du programme.
 - Les fonctions : On peut définir autant de fonctions que l'on veut mais pas
   de définition imbriquées de fonction.

Nous ne considérons pas :
 - Les types composés (listes, tuples ...). Y compris pour les fonctions, le
   type renvoyé doit être simple (ou void).
 - L'objet en général (pas de méthodes, pas de classes)

Enfin, les chaines de caractères sont très mal gérées par le programme.
On peut stocker une chaine de caractère dans une variable mais une fois celle
ci définie, on ne peut pas la modifier.
La fonction print de python est prise en charge, à condition qu'on en lui donne
qu'un argument et que celui-ci soit une chaine de caractère. (pas de "coucou"+a)
avec a une variable.

NB : Pour typer les arguments d'une fonction, on recherche les apels de celle-ci
dans le corps du programme. Ainsi, une fonction qui est défini mais qui n'est pas appelée
ou qui est appelé au sein d'une autre fonction uniquement ne sera pas corecteemtn identifiée.
Dans ce cas, la conversion n'aboutira pas.

###################################
| Comment exécuter le programme ? |
###################################

Il suffit de taper dans un terminal :
    ./run ./monFichier
pour lancer l'exécution du programme sur le fichier python ./monFichier.py.
Les fichier .c de sortie sont sauvegardé à coté du fichier source. Ils sont ensuit compilé
avec gcc par le script ./run puis exécuté.
Il est impératif de donner un chemin relatif lors de l'appel de ./run et de toujours
spécifier (pas de ./run /tmp/monFichier ou de ./run monFichier).
Enfin, il faut exécuter le script dans son repertoire (toujours ./run arg).

Le script nécessite l'installation de java (sudo apt-get install default-jdk) 
et de gcc (sudo apt-get install gcc).

Des exemples de codes python sont fournis dans le dossier ./test_files

Pour l'exécution directement avec l'IDE, n'oubliez pas de spécifier dans la 
configuration de lancement l'argument contenant le nom (sans extension) du fichier à 
convertir.

########################
| Les fichiers de test |
########################

Le dossier ./test_files contient trois fichiers ".py".
 - factorielle.py : On définit trois fonctions calculant la factorielle,
   on les exécute toutes les 3 avec le même argument et on vérifie que le 
   résultat est le même. Si c'est le cas on affiche "Great it works !".
   Sinon, on affiche "Not working :("
 - fibo.py : On calcule le neuvième nombre de la suite de fibonnacci
   récursivement et on affiche ok.
 - test.py : Un ensemble de fonctions et de commandes python sans logique
   particulière visant à tester les boucles, les opérations, les conditions
   les déclarations de fonctions ...

############
| Contacts |
############

Pour plus d'informations ou en cas de bugs, n'hésitez pas à me contacter
sur caine.silva@gmx.com ou caine.silva@ensta-bretagne.org