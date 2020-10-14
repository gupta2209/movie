rm TestCase.txt
./create_var_cond.sh
clang -I ../../include -emit-llvm -c -g -O0 -Xclang -disable-O0-optnone combine_code.c
klee --write-smt2s combine_code.bc
python parse.py
