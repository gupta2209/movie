// ***** This file is automatically generated from SequenceSubscript.java.jpp

package daikon.derive.binary;

import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.checkerframework.checker.lock.qual.GuardSatisfied;
import org.checkerframework.checker.interning.qual.Interned;
import java.util.logging.Logger;
import daikon.*;
import daikon.derive.*;
import org.plumelib.util.Intern;
import org.plumelib.util.UtilPlume;

public final class SequenceScalarSubscript extends BinaryDerivation {
  // We are Serializable, so we specify a version to allow changes to
  // method signatures without breaking serialization.  If you add or
  // remove fields, you should change this number to the current date.
  static final long serialVersionUID = 20020122L;

  // Variables starting with dkconfig_ should only be set via the
  // daikon.config.Configuration interface.
  /** Boolean. True iff SequenceScalarSubscript derived variables should be generated. */
  public static boolean dkconfig_enabled = true;

  /** General debug tracer. */
  public static final Logger debug = Logger.getLogger("daikon.derive.binary.SequenceScalarSubscript");

  // base1 is the sequence
  // base2 is the scalar
  public VarInfo seqvar(@GuardSatisfied SequenceScalarSubscript this) {
    return base1;
  }

  public VarInfo sclvar(@GuardSatisfied SequenceScalarSubscript this) {
    return base2;
  }

  public VarInfo seq_enclosing_var() {
    VarInfo result = seqvar().enclosing_var;
    // When using old dtrace file format, this can fail.
    assert result != null : "@AssumeAssertion(nullness): foo[] has enclosing_var foo";
    return result;
  }

  // Indicates whether the subscript is an index of valid data or a limit
  // (one element beyond the data of interest).
  // Value is -1 or 0.
  public final int index_shift;

  SequenceScalarSubscript(VarInfo vi1, VarInfo vi2, boolean less1) {
    super(vi1, vi2);
    if (less1) {
      index_shift = -1;
    } else {
      index_shift = 0;
    }
    debug.fine("SequenceScalarSubscript(" + vi1 + ", " + vi2 + ", " + less1 + ") => " + this);
  }

  @SideEffectFree
  @Override
  public String toString(@GuardSatisfied SequenceScalarSubscript this) {
    String shift = "";
    if (index_shift < 0) {
      shift = "" + index_shift;
    } else if (index_shift > 0) {
      shift = "+" + index_shift;
    }
    return (UtilPlume.replaceString(seqvar().name(), "[]", "") + "[" + sclvar().name() + shift + "]");
  }

  @Override
  public ValueAndModified computeValueAndModifiedImpl(ValueTuple full_vt) {
    int mod1 = base1.getModified(full_vt);
    if (mod1 == ValueTuple.MISSING_NONSENSICAL) {
      return ValueAndModified.MISSING_NONSENSICAL;
    }
    int mod2 = base2.getModified(full_vt);
    if (mod2 == ValueTuple.MISSING_NONSENSICAL) {
      return ValueAndModified.MISSING_NONSENSICAL;
    }
    Object val1 = base1.getValue(full_vt);
    if (val1 == null) {
      return ValueAndModified.MISSING_NONSENSICAL;
    }
    long[] val1_array = (long[]) val1;
    int val2 = base2.getIndexValue(full_vt) + index_shift;
    if ((val2 < 0) || (val2 >= val1_array.length)) {
      // if (!missing_array_bounds)
      //   System.out.println ("out of bounds" + base1.name() + " "
      //                      + base2.name() + " @" + base1.ppt.name());
      missing_array_bounds = true;
      return ValueAndModified.MISSING_NONSENSICAL;
    }
    long val = val1_array[val2];
    int mod =
        (((mod1 == ValueTuple.UNMODIFIED) && (mod2 == ValueTuple.UNMODIFIED))
         ? ValueTuple.UNMODIFIED
         : ValueTuple.MODIFIED);
    return new ValueAndModified(Intern.internedLong(val), mod);
  }

  @Override
  protected VarInfo makeVarInfo() {
    return VarInfo.make_subscript(seqvar(), sclvar(), index_shift);
  }

  @Pure
  @Override
  public boolean isSameFormula(Derivation other) {
    return (other instanceof SequenceScalarSubscript)
      && (((SequenceScalarSubscript) other).index_shift == this.index_shift);
  }

  /** Returns the ESC name for sequence subscript. */
  @SuppressWarnings("nullness")
  @SideEffectFree
  @Override
  public String esc_name(String index) {
    if (seqvar().isPrestate()) {
      return String.format(
          "\\old(%s[%s])",
          seqvar().enclosing_var.postState.esc_name(),
          inside_esc_name(sclvar(), true, index_shift));
    } else {
      return String.format(
          "%s[%s%s]",
          seqvar().enclosing_var.esc_name(), sclvar().esc_name(), shift_str(index_shift));
    }
  }

  /** Returns the JML name for sequence subscript. */
  @SuppressWarnings("nullness")
  @Override
  public String jml_name(String index) {
    String get_element = "daikon.Quant.getElement_int";
    if (seqvar().file_rep_type.baseIsHashcode()) {
      get_element = "daikon.Quant.getElement_Object";
    } else if (seqvar().file_rep_type.baseIsBoolean()) {
      get_element = "daikon.Quant.getElement_boolean";
    }
    if (seqvar().isPrestate()) {
      return String.format(
          "\\old(%s(%s, %s))",
          get_element,
          seqvar().enclosing_var.postState.jml_name(),
          inside_jml_name(sclvar(), true, index_shift));
    } else {
      return String.format(
          "%s(%s, %s%s)",
          get_element,
          seqvar().enclosing_var.jml_name(),
          sclvar().jml_name(),
          shift_str(index_shift));
    }
  }

  /** Returns the CSHARPCONTRACT name for sequence subscript. */
  @SuppressWarnings("nullness")
  @SideEffectFree
  @Override
  public String csharp_name(String index) {
    String[] split = seqvar().csharp_array_split();
    if (seqvar().isPrestate()) {
      return String.format(
          "Contract.OldValue(%s[%s]%s)",
          seqvar().enclosing_var.postState.csharp_name(),
          inside_csharp_name(sclvar(), true, index_shift),
          split[1]);
    } else {
      return String.format(
          "%s[%s%s]%s", split[0], sclvar().csharp_name(), shift_str(index_shift), split[1]);
    }
  }

  /** Return the simplify name for sequence subscript. */
  @SuppressWarnings("nullness")
  @SideEffectFree
  @Override
  public String simplify_name() {
    String subscript = sclvar().simplify_name();
    if (index_shift < 0) {
      subscript = String.format("(- %s %d)", subscript, -index_shift);
    } else if (index_shift > 0) {
      subscript = String.format("(+ %s %d)", subscript, index_shift);
    }

    return String.format(
        "(select (select elems %s) %s)", seqvar().enclosing_var.simplify_name(), subscript);
  }

  /** Adds one to the default complexity if index_shift is not 0. */
  @Override
  public int complexity() {
    return super.complexity() + ((index_shift != 0) ? 1 : 0);
  }
}
