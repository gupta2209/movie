 #!/usr/bin/env bash
 
echo Please enter 1st c file name
#read f1
# f1=./TestCases_Ours/Waka/hls_macc.c
f1=./TestCases_Ours/NoConstant/no_constant.c
echo Please enter 1st cut position
#read cu1
cu1=87		#noconstant
# cu1=90		#waka
echo Please enter 2nd c file name
#read f2
# f2=./TestCases_Ours/Waka/ccode_hls_macc.c
f2=./TestCases_Ours/NoConstant/ccode_hls_macc.c
echo Please enter 2nd cut position
#read cu2
cu2=638	  #no constant
# cu2=402  # waka
python print_vars.py $f1 $cu1 1
python print_vars.py $f2 $cu2 2

echo Please enter 1st testcase file name
#read f_name
f_name=test_case1
echo Please enter number of cases
read n_cases

gcc printed1.c
rm data_int1.txt
touch data_int1.txt


let a=0
while [ $a -lt $n_cases ]
do
	let a++
	var_val=$( sed "${a}q;d" $f_name )
	echo $var_val > l.txt
	./a.out < l.txt >> "data_int1.txt"
done

echo Please enter 2nd testcase file name
#read f_name
f_name=test_case1

gcc printed2.c
rm data_int2.txt
touch data_int2.txt
let a=0
while [ $a -lt $n_cases ]
do
	let a++
	var_val=$( sed "${a}q;d" $f_name )
	echo $var_val > l.txt
	./a.out < l.txt >> "data_int2.txt"
done

python nullspace.py

rm -r temp
mkdir temp