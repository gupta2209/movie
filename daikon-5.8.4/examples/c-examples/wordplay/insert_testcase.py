
def convert(x):
    if x[-1]==')':
        x=x[:-1]
    if x[-1]==')':
        x=x[:-1]
    x=x[2:]
    y=int(x,16)
    return y

def toSigned32(n):
    n = n & 0xffffffff
    return n | (-(n & 0x80000000))

var_dic=dict()

f=open("reinitiate.txt","r")
f1=f.readlines()
f.close()

l=0
for s in f1:
    s=s[:-1]
    print s
    var_dic[s]=l
    l=l+1

f=open("TestCase.txt","r")
f1=f.readlines()
f.close()


values=dict()
var=""
for s in f1:
    h=s.split()
    if(h[0]=='(define-fun'):
        var=h[1]
    else:
        if var not in values.keys():
            values[var]=list()
        for x in h:
            if x[0]=="#":
                values[var].append( convert(x) )

var_values=dict()
for x in values.keys():
    if x not in var_dic.keys():
        continue
    index=var_dic[x]
    val=0

    val=( 2**0+ 2**8+ 2**16 + 2**24  ) *( values[x][0] )
    g=len(values[x])
    for h in range(1,g,2):
        values[x][h]=8*values[x][h]
        val=val-( values[x][0] * (2** values[x][h] ) ) +( (2** values[x][h] ) *( values[x][h+1] ) )
    var_values[index]=toSigned32(val)
    print( x , val,toSigned32(val) )

for x in range( len(var_values) ):
    print var_values[x],
         
      

