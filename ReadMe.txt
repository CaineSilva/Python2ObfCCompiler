Cette archive contient : 
 - La documentation compl�te du code (au format html) dans le 
   dossier ./doc.
 - Le code source dans le dossier ./src
 - Des fichiers python pour tester le code dans le dossier 
   ./test_files
 - Un script d'ex�cution ./run

Le dossier ./.idea et le fichier ./Python2ObfCCompiler.iml sont utile 
si vous souhaitez ouvrir le projet avec l'IDE IntelliJ.

###################################
| Le projet : Python2ObfCCompiler |
###################################

Ce projet a �t� propos� dans le cadre de la formation d'ing�nieur 
de l'ENSTA Bretagne et a �t� r�alis� par Caine SILVA et Riwan CUINAT.

Le but de ce projet est de convertir un code python en :
 - un code C
 - un code C obfusqu�

###########################################
| Le langage de d�part : Un python all�g� |
###########################################

Bien entendu, nous avons travaill� sur un sous langage du
python au vu du temps imparti pour le projet.
Nous consid�rons :
 - Les variables (enti�res, flottantes ou cha�ne de caract�re)
   Celles-ci ne doivent pas changer de type au cours de l'ex�cution du programme.
 - Les fonctions : On peut d�finir autant de fonctions que l'on veut mais pas
   de d�finition imbriqu�es de fonction.

Nous ne consid�rons pas :
 - Les types compos�s (listes, tuples ...). Y compris pour les fonctions, le
   type renvoy� doit �tre simple (ou void).
 - L'objet en g�n�ral (pas de m�thodes, pas de classes)

Enfin, les chaines de caract�res sont tr�s mal g�r�es par le programme.
On peut stocker une chaine de caract�re dans une variable mais une fois celle
ci d�finie, on ne peut pas la modifier.
La fonction print de python est prise en charge, � condition qu'on en lui donne
qu'un argument et que celui-ci soit une chaine de caract�re. (pas de "coucou"+a)
avec a une variable.

NB : Pour typer les arguments d'une fonction, on recherche les apels de celle-ci
dans le corps du programme. Ainsi, une fonction qui est d�fini mais qui n'est pas appel�e
ou qui est appel� au sein d'une autre fonction uniquement ne sera pas corecteemtn identifi�e.
Dans ce cas, la conversion n'aboutira pas.

###################################
| Comment ex�cuter le programme ? |
###################################

Il suffit de taper dans un terminal :
    ./run ./monFichier
pour lancer l'ex�cution du programme sur le fichier python ./monFichier.py.
Les fichier .c de sortie sont sauvegard� � cot� du fichier source. Ils sont ensuit compil�
avec gcc par le script ./run puis ex�cut�.
Il est imp�ratif de donner un chemin relatif lors de l'appel de ./run et de toujours
sp�cifier (pas de ./run /tmp/monFichier ou de ./run monFichier).
Enfin, il faut ex�cuter le script dans son repertoire (toujours ./run arg).

Le script n�cessite l'installation de java (sudo apt-get install default-jdk) 
et de gcc (sudo apt-get install gcc).

Des exemples de codes python sont fournis dans le dossier ./test_files

Pour l'ex�cution directement avec l'IDE, n'oubliez pas de sp�cifier dans la 
configuration de lancement l'argument contenant le nom (sans extension) du fichier � 
convertir.

########################
| Les fichiers de test |
########################

Le dossier ./test_files contient trois fichiers ".py".
 - factorielle.py : On d�finit trois fonctions calculant la factorielle,
   on les ex�cute toutes les 3 avec le m�me argument et on v�rifie que le 
   r�sultat est le m�me. Si c'est le cas on affiche "Great it works !".
   Sinon, on affiche "Not working :("
 - fibo.py : On calcule le neuvi�me nombre de la suite de fibonnacci
   r�cursivement et on affiche ok.
 - test.py : Un ensemble de fonctions et de commandes python sans logique
   particuli�re visant � tester les boucles, les op�rations, les conditions
   les d�clarations de fonctions ...

############
| Contacts |
############

Pour plus d'informations ou en cas de bugs, n'h�sitez pas � me contacter
sur caine.silva@gmx.com ou caine.silva@ensta-bretagne.org