//
// Generated by JTB 1.1.2
//

package jtb.cparser.syntaxtree;

// Grammar production:
// f0 -> Enumerator()
// f1 -> ( "," Enumerator() )*
public class EnumeratorList implements Node {
  static final long serialVersionUID = 20050923L;

   public Enumerator f0;
   public NodeListOptional f1;

   public EnumeratorList(Enumerator n0, NodeListOptional n1) {
      f0 = n0;
      f1 = n1;
   }

   public void accept(jtb.cparser.visitor.Visitor v) {
      v.visit(this);
   }
}
