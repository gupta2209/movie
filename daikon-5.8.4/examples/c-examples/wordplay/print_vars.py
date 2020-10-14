import sys
import re
def extract_var(st):
	# st.replace("\t","")
	# st.replace(" \n","")
	# st.replace("\n","")
	# st.replace("    ","")
	l = st.split()
	if "int" in l:
		if l[1] == "main" or l[1][:5] == "main(":
			return [-1]
		st = st.replace("\t","")
		st = st.replace(" ","")
		st = st.replace("\n","")
		st = st.replace(";","")
		st = st[3:]
		val = st.split("=")[0]
		return val.split(",")
	return [-1]

def find_no(x,s):
	result = re.findall(r'^'+x+' *=',s)
	if len(result):
		return 2*len(result)
	result = re.findall(r'[^a-zA-Z1-9_]'+x+' *=',s)
	if len(result):
		print(result)
		return 2*len(result)
	result = re.findall(r'[^a-zA-Z1-9_]'+x+'[^a-zA-Z1-9_]',s)
	return len(result)



args = sys.argv
if len(args) < 4:
	print "Arguements not supplied"
	exit()

f = open(args[1], "r")
f1 = f.readlines()
f.close()

var_list = []
l = 0
for s in f1:
	if l == int(args[2]):
		break;
	z = extract_var(s)
	if z != [-1]:
		var_list.extend(z)
	l = l+1



fin_var_list=[]
for x in var_list:
	if x[-1]=='\r':
		x=x[:-1]
	count=0
	l=0
	for s in f1:
		if l == int(args[2]):
			break;
		count =count+find_no(x,s)
		l=l+1
	print(x)
	print(x,count,len(x))
	if count >=2:
		fin_var_list.append(x)


var_list=fin_var_list

l = 0
f_out = ""

for s in f1:
	if l == int(args[2]):
		for s2 in var_list:
			f_out = f_out + "printf(\"%d \","+ s2 + ");\n"
		f_out = f_out + "printf(\"\\n\");\n"
	f_out = f_out + s
	l = l+1

# if int(args[3])==1:
# 	f = open("printed1.c", "w")
# else:
# 	f = open("printed2.c", "w")
# f.write(f_out)
# f.close()


f_out = ""
f_line = ""
for s in var_list:
	if int(args[3])==1:
		f_out = f_out + s + "21\n"
	else:
		f_out = f_out + s + "22\n"

for s in var_list:
	if int(args[3])==1:
		f_line = f_line + s + "1 "
	else:
		f_line = f_line + s + "2 "
f_line = f_line + "\n"

if int(args[3])==1:
	f = open("var_names_int1.txt", "w")
else:
	f = open("var_names_int2.txt", "w")

f.write(f_out)
f.close()

if int(args[3])==1:
	f = open("var_names_line1.txt", "w")
else:
	f = open("var_names_line2.txt", "w")

f.write(f_line)
f.close()