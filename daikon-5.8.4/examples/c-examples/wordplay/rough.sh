awk '{ print "(declare-fun "substr($1,1,length($1)-2)"11 () (Array (_ BitVec 32) (_ BitVec 8) ) )"}' var_names_int1.txt


# klee_print_expr("in121:=",in111);
awk '{ print " klee_print_expr(\""substr($1,1,length($1)-2)"22:=\","substr($1,1,length($1)-2)"12);"}'