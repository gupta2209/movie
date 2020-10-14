// ***** This file is automatically generated from EltwiseIntComparisons.java.jpp

package daikon.inv.unary.sequence;

import daikon.*;
import daikon.Quantify.QuantFlags;
import daikon.derive.*;
import daikon.derive.binary.*;
import daikon.inv.*;
import java.util.*;
import org.checkerframework.checker.interning.qual.Interned;
import org.checkerframework.checker.lock.qual.GuardSatisfied;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.plumelib.util.Intern;
import typequals.prototype.qual.NonPrototype;
import typequals.prototype.qual.Prototype;

  /**
   * Represents the invariant &ge; between adjacent elements
   * (x[i], x[i+1]) of a double sequence. Prints as
   * {@code x[] sorted by >=}.
   */

public class EltwiseFloatGreaterEqual extends EltwiseFloatComparison {
  // We are Serializable, so we specify a version to allow changes to
  // method signatures without breaking serialization.  If you add or
  // remove fields, you should change this number to the current date.
  static final long serialVersionUID = 20030822L;

  // Variables starting with dkconfig_ should only be set via the
  // daikon.config.Configuration interface.
  /** Boolean. True iff EltwiseIntComparison invariants should be considered. */
  public static boolean dkconfig_enabled = Invariant.invariantEnabledDefault;

  static final boolean debugEltwiseIntComparison = false;

  protected EltwiseFloatGreaterEqual(PptSlice ppt) {
    super(ppt);
  }

  protected @Prototype EltwiseFloatGreaterEqual() {
    super();
  }

  private static @Prototype EltwiseFloatGreaterEqual proto = new @Prototype EltwiseFloatGreaterEqual();

  /** Returns the prototype invariant for EltwiseFloatGreaterEqual */
  public static @Prototype EltwiseFloatGreaterEqual get_proto() {
    return proto;
  }

  /** returns whether or not this invariant is enabled */
  @Override
  public boolean enabled() {
    return dkconfig_enabled;
  }

  /** Non-equality EltwiseFloatGreaterEqual invariants are only valid on integral types. */
  @Override
  public boolean instantiate_ok(VarInfo[] vis) {

    if (!valid_types(vis)) {
      return false;
    }

    return true;
  }

  /** Instantiate the invariant on the specified slice. */
  @Override
  protected EltwiseFloatGreaterEqual instantiate_dyn(@Prototype EltwiseFloatGreaterEqual this, PptSlice slice) {
    return new EltwiseFloatGreaterEqual(slice);
  }

  @SideEffectFree
  @Override
  public EltwiseFloatGreaterEqual clone(@GuardSatisfied EltwiseFloatGreaterEqual this) {
    EltwiseFloatGreaterEqual result = (EltwiseFloatGreaterEqual) super.clone();
    return result;
  }

  @Override
  public String repr(@GuardSatisfied EltwiseFloatGreaterEqual this) {
    return "EltwiseFloatGreaterEqual" + varNames() + ": falsified=" + falsified;
  }

  @SideEffectFree
  @Override
  public String format_using(@GuardSatisfied EltwiseFloatGreaterEqual this, OutputFormat format) {
    if (format.isJavaFamily()) {
      return format_java_family(format);
    }

    if (format == OutputFormat.DAIKON) {
      return format_daikon();
    }
    if (format == OutputFormat.ESCJAVA) {
      return format_esc();
    }
    if (format == OutputFormat.CSHARPCONTRACT) {
      return format_csharp_contract();
    }
    if (format == OutputFormat.SIMPLIFY) {
      return format_simplify();
    }

    return format_unimplemented(format);
  }

  public String format_daikon(@GuardSatisfied EltwiseFloatGreaterEqual this) {
    if (debugEltwiseIntComparison) {
      System.out.println(repr());
    }

    return (var().name() + " sorted by >=");
  }

  public String format_esc(@GuardSatisfied EltwiseFloatGreaterEqual this) {
    String[] form = VarInfo.esc_quantify(false, var(), var());

      return form[0] + "((i+1 == j) ==> (" + form[1] + " >= " + form[2] + "))" + form[3];
  }

  public String format_java_family(@GuardSatisfied EltwiseFloatGreaterEqual this, OutputFormat format) {
    return "daikon.Quant.eltwiseGTE(" + var().name_using(format) + ")";
  }

  public String format_csharp_contract(@GuardSatisfied EltwiseFloatGreaterEqual this) {
    String[] split = var().csharp_array_split();
    return "Contract.ForAll(0, " + split[0] + ".Count()-1, i => " + split[0] + "[i]" + split[1] + " >= " + split[0] + "[i+1]" + split[1] + ")";
  }

  public String format_simplify(@GuardSatisfied EltwiseFloatGreaterEqual this) {
    String[] form = VarInfo.simplify_quantify(QuantFlags.adjacent(),
                                               var(), var());

    String comparator = ">=";

    return form[0] + "(" + comparator + " " + form[1] + " " + form[2] + ")"
      + form[3];
  }

  @Override
  @SuppressWarnings("UnnecessaryParentheses")  // generated code, parentheses are sometimes needed
  public InvariantStatus check_modified(double @Interned [] a, int count) {
    for (int i = 1; i < a.length; i++) {
      if (!Global.fuzzy.gte(a[i - 1], a[i])) {
        return InvariantStatus.FALSIFIED;
      }
    }
    return InvariantStatus.NO_CHANGE;
  }

  @Override
  public InvariantStatus add_modified(double @Interned [] a, int count) {
    return check_modified(a, count);
  }

  // Perhaps check whether all the arrays of interest have length 0 or 1.

  @Override
  protected double computeConfidence() {

    return 1 - Math.pow(.5, ppt.num_samples());
  }

  @Pure
  @Override
  public boolean isExact() {

    return false;
  }

  @Pure
  @Override
  public boolean isSameFormula(Invariant other) {
    return (other instanceof EltwiseFloatGreaterEqual);
  }

  // Not pretty... is there another way?
  // Also, reasonably complicated, need to ensure exact correctness, not sure if the
  // regression tests test this functionality

  @Pure
  @Override
  public boolean isExclusiveFormula(Invariant other) {
    // This whole approach is wrong in the case when the sequence can
    // ever consist of only one element.  For now, just forget
    // it. -SMcC
    if (true) {
      return false;
    }

    if (other instanceof EltwiseFloatComparison) {

      return (other instanceof EltwiseIntLessThan) || (other instanceof EltwiseFloatLessThan);
    }
    return false;
  }

  // Look up a previously instantiated invariant.
  public static @Nullable EltwiseFloatGreaterEqual find(PptSlice ppt) {
    assert ppt.arity() == 1;
    for (Invariant inv : ppt.invs) {
      if (inv instanceof EltwiseFloatGreaterEqual) {
        return (EltwiseFloatGreaterEqual) inv;
      }
    }
    return null;
  }

  // Copied from IntComparison.
  // public boolean isExclusiveFormula(Invariant other)
  // {
  //   if (other instanceof IntComparison) {
  //     return core.isExclusiveFormula(((IntComparison) other).core);
  //   }
  //   if (other instanceof IntNonEqual) {
  //     return isExact();
  //   }
  //   return false;
  // }

  /**
   * This function returns whether a sample has been seen by this Invariant that includes two or
   * more entries in an array. For a 0 or 1 element array a, a[] sorted by any binary operation is
   * "vacuously true" because no check is ever made since the binary operation requires two
   * operands. Thus although invariants of this type are true regarding 0 or 1 length arrays, they
   * are meaningless. This function is meant to be used in isObviousImplied() to prevent such
   * meaningless invariants from being printed.
   */
  @Override
  public boolean hasSeenNonTrivialSample() {
    ValueSet.ValueSetFloatArray vs = (ValueSet.ValueSetFloatArray) ppt.var_infos[0].get_value_set();
    return (vs.multi_arr_cnt() > 0);
  }

  @Pure
  @Override
  public @Nullable DiscardInfo isObviousDynamically(VarInfo[] vis) {
    DiscardInfo super_result = super.isObviousDynamically(vis);
    if (super_result != null) {
      return super_result;
    }

    if (!hasSeenNonTrivialSample()) {
      return new DiscardInfo(this, DiscardCode.obvious,
                             "No samples sequences of size >=2 were seen. Vacuously true.");
    }

    EltOneOfFloat eoo = EltOneOfFloat.find(ppt);
    if ((eoo != null) && eoo.enoughSamples() && (eoo.num_elts() == 1)) {
      return new DiscardInfo(this, DiscardCode.obvious, "The sequence contains all equal values.");
    }

    {
      // some relations imply others as follows: < -> <=, > -> >=
      // == -> <=, >=

      // This code lets through some implied invariants, here is how:
      // In the ESC, JML, Java modes of output, the invariants are guarded
      // before printing.  This guarding makes some of the invariants that
      // are searched for (example, NoDuplicates) unable to be found since it
      // won't find something of the form (a -> NoDuplicates).  This can
      // cause cases to exist, for example, the invariants (a -> b[] sorted
      // by !=), (a -> b[] has no duplicates) existing in the same ppt where
      // one is obviously implied by the other. I am not sure currently of
      // the best way with dealing with this, but I am going to allow these
      // other invariants to exist for now because they are not wrong, just
      // obvious.

      for (Invariant inv : ppt.invs) {

        if ((inv instanceof EltwiseIntGreaterThan) || (inv instanceof EltwiseFloatGreaterThan)) {
          String discardString = "The invariant holds for > which implies >=.";
          return new DiscardInfo(this, DiscardCode.obvious, discardString);
        } else if ((inv instanceof EltwiseIntEqual) || (inv instanceof EltwiseFloatEqual)) {
          String discardString = "The invariant holds for == which implies >=.";
          return new DiscardInfo(this, DiscardCode.obvious, discardString);
        }

      }

    }

    // sorted by (any operation) for an entire sequence -> sorted by that same
    // operation for a subsequence

    // also, sorted by < for entire -> sorted by <= for subsequence
    //       sorted by > for entire -> sorted by >= for subsequence

    Derivation deriv = vis[0].derived;

    if ((deriv instanceof SequenceScalarSubsequence) || (deriv instanceof SequenceFloatSubsequence)) {
      // Find the slice with the full sequence, check for an invariant of this type
      PptSlice sliceToCheck;

      if (deriv instanceof SequenceScalarSubsequence) {
        sliceToCheck = ppt.parent.findSlice(((SequenceScalarSubsequence)deriv).seqvar());
      } else {
        sliceToCheck = ppt.parent.findSlice(((SequenceFloatSubsequence)deriv).seqvar());
      }

      if (sliceToCheck != null) {
        for (Invariant inv : sliceToCheck.invs) {

          if ((inv instanceof EltwiseIntGreaterThan) || (inv instanceof EltwiseFloatGreaterThan)) {
             String discardString = "This is a subsequence of a sequence for which the > invariant holds.";
            return new DiscardInfo(this, DiscardCode.obvious, discardString);
          }

          if (inv.getClass().equals(getClass())) {
            String discardString = "This is a subsequence of a sequence for which the same invariant holds.";
            return new DiscardInfo(this, DiscardCode.obvious, discardString);
          }
        }
      }
    }

    return null;
  }
}
