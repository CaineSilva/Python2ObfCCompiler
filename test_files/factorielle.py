def nothing():
    s = "Nothing"
    print(s)

def factorielle(n) :
    if n==1 or n==0 :
        return 0
    else :
        return factorielle(n-1)

def factorielle_while(n) :
    result = 1
    i = 1
    while i < n :
        result *= i+1
        i += 1
    return result

def factorielle_for(n) :
    result = 1
    for i in range(1,n) :
        result *= i+1
    return result

n=5
f1 = factorielle(n)
f2 = factorielle_for(n)
f3 = factorielle_while(n)
if f1 == f2 and f1 == f3 :
    print("Great it works !")
else :
    print("Not working :(")