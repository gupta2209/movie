import z3
from z3 import *
import sys


val = 0

def compareOut(str1, str2):
	# print("In compare out ==> ")
	# print("				str1 is:" + str1 + "::")
	# print("				str2 is:" + str2 + "::")

	check = in_vars +"\n(set-option :pp-bv-literals false)\n" +"(assert ( not ( = " + str1 + " " + str2 + " ) ) )" + "(check-sat)"
	# print("check is:" + check + "::")
	sol = Solver()
	sol.from_string(check)
	k = sol.check()
	# print str(k)
	# print("Out compare out")
	if str(k) == "unsat":
		return 1
	global val
	if val == 0:
		ooo = sol.model()
		f = open("Testcase.txt","a+")
		f.write(str(ooo) + "\n")
		f.close()
	val = 0
	return 0
def compare(str1,str2):
    check = in_vars+"(assert ( and( \n"+str1+"\n ) ( not ( \n"+ str2 +"\n ) ) ) )"+"(check-sat)"
    sol=Solver()
    sol.from_string(check)
    k=sol.check()

    if str(k) == "unsat":
        return 1

    ooo = sol.model().sexpr()
    f=open("TestCase.txt","a+")
    f.write(str(ooo)+"\n")
    f.close()
    return 0

def condOr(cond1, cond2):
	cond_final = "or (" + cond1 + " ) ( " + cond2 + " )"
	return cond_final

def condAnd(cond1,cond2):
    cond_final = "and (" + cond1 + " ) ( " + cond2 + " )"
    return cond_final

def values_eq(str1,str2):
    cond_final = "=  ("+str1+") ("+str2+") "
    return cond_final

def var_in_smt2(str):
    ret="concat  (select  "+str+" (_ bv3 32) ) (concat  (select  "+str+" (_ bv2 32) ) (concat  (select  "+str+" (_ bv1 32) ) (select  "+str+" (_ bv0 32) ) ) ) "
    return ret

def compareCond(str1, str2):
	if str1 == str2:
		return 1
	check = in_vars + "(assert ( not ( = ( " + str1 + " ) ( " + str2 + " ) ) ) )" + "(check-sat)"
	# print("\n\n\n\n\n\n")
	# print("str1 is:" + str1 + "::")
	# print("str2 is:" + str2 + "::")
	# print("check is:" + check + "::")
	# print("\n\n\n\n\n\n")
	sol = Solver()
	sol.from_string(check)
	k = sol.check()
	# print("Condition check:" + str(k))
	if str(k) == "unsat":
		# print("Returning 1")
		return 1
	return 0



f = open( "temp/klee_output_combine.txt" , "r")
f1 = f.readlines()
f.close()
conditions1 = list()
outputs1 = list()
outputs1_dict = dict()

var_name = ""
last_cond = ""
vars_list = list()
cond_val_map = dict()
cond_val_cnt = 0

for s in f1:
	s = s.replace(" \n", "")
	s = s.replace("\n", "")
	# print("For s:"+s)
	# print("var_name is:"+var_name+"\n\n\n\n\n")
	if len(s) > 2 and s[1] =='(' and s[-1] == ')':
		k = s[2:-2]
		last_cond = k
	elif s == " true":
		# conditions1 = ["true"]
		last_cond = "true"
	elif len(s) > 2 and s[0] =='(' and s[-1] ==')':
		k = s[0:-1]
		if last_cond in outputs1_dict.keys():
			outputs1_dict[last_cond][var_name]=k[1:-1]
		else:
			cond_val_cnt = cond_val_cnt+1
			# cond_val_map[last_cond]=cond_val_cnt
			outputs1_dict[last_cond]=dict()
			outputs1_dict[last_cond][var_name]=k[1:-1]
	elif len(s) > 2 and s[-2:] == ":=":
		var_name = s[:-2]
		if var_name not in vars_list:
			vars_list.append(var_name)



for c1 in outputs1_dict.keys():
	conditions1.append(c1)
	for var in vars_list:
		outputs1.append(outputs1_dict[c1][var])

print( len(conditions1) )

f = open("test.smt", "r")
f1 = f.readlines()
in_vars = str()

for s in f1:
	s = s.replace(" \n", "")
	s = s.replace("\n", "")
	if len(s) > 3 and s[1] == 'd' and s[2] == 'e':
		in_vars = in_vars + s
	elif len(s) > 3 and s[1] == 's' and s[2] == 'e':
		in_vars = in_vars + s
	else:
		break
f.close()

# print(in_vars)

f=open("temp/invarient_condition.txt","r")
invarient_condition=f.readlines()
f.close()


for cond in conditions1:
    cond_cur=""
    for var in vars_list:
		s=var_in_smt2(var)
		s=values_eq(s,outputs1_dict[cond][var])
		if cond_cur=="":
			cond_cur=s
		else:
			cond_cur=condAnd(cond_cur,s)
    cond_cur=condAnd(cond_cur,cond)
    index=1
    for s in invarient_condition:
		s = s.replace(" \n", "")
		s = s.replace("\n", "")
		s=s.strip()
		s=s[1:-1]
		if not compare(cond_cur,s):
			print(" not same file")
			print(index)
			print("not satified for invarient condition-",s)
			exit()
		index=index+1

print("files are same ================================================> correct invarient")

# 58