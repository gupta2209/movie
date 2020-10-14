gcc -gdwarf-2 -no-pie rakesh.c -o rakesh
kvasir-dtrace ./rakesh < testcases1.txt
java -cp $DAIKONDIR/daikon.jar daikon.Daikon      --config_option daikon.derive.Derivation.disable_derived_variables=true      daikon-output/rakesh.decls daikon-output/rakesh.dtrace  > output.txt
python invarient_parse.py > const.txt