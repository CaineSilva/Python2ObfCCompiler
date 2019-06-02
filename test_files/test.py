########################################################### 
def fact(n): 
    """fact(n): calcule la factorielle de n (entier >= 0)""" 
    if n<2 : 
        return 1 
    else : 
        return n*fact(n-1)
fact(2)
ageDeEricPraline = 50 
ageDeHenryWensleydale = 20 # Test 
if 0 > ageDeEricPraline > 150 or ageDeEricPraline > ageDeHenryWensleydale: 
    print("l'age de Eric Praline est incorrect")
s = 0 
for i in range(1,11): 
    s += i**2

def fonction_carre(x): 
    return x * x 

def fonction_cube(x): 
    return x**3
n=0 
fonction_carre(n)
fonction_cube(n)
while n<6: 
    print("zut") 
    n = n+1
a = 3
b = 8
c = (a < b)
d = (a > b)
a = 3
while a : # équivalent à : < while a != 0: > 
    if a == 1 : 
        print("Vous avez choisi un :") 
        print("le premier, l'unique, l'unité ..." )
    elif a == 2 : 
        print("Vous préférez le deux :") 
        print("la paire, le couple, le duo ...") 
    elif a == 3 : 
        print("Vous optez pour le plus grand des trois :") 
        print("le trio, la trinité, le triplet ...")
    else : 
        print("L'exercice est terminé.")
    a -= 1
def compteur3() : 
    i = 0 
    while i < 3: 
        i = i + 1 
        print("bonjour") 
compteur3() 
compteur3()
def compteur(stop) :
     i = 0
    while i < stop : 
        print(''_'') 
        i = i + 1 
compteur(4) 
compteur(2)
def compteur_complet(start, stop, step) : 
    i = start 
    while i < stop : 
        print(''_'') 
        i = i + step 
compteur_complet(1, 7, 2)

def cube(n) :
    return n*n*n
cube(1)
def volume_sphere(r) : 
    return 4 / 3 * 3.14 * cube(r)
r = 4
volume_sphere(r)