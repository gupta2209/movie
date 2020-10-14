// ***** This file is automatically generated from Numeric.java.jpp

package daikon.inv.binary.twoSequence;

import org.checkerframework.checker.signature.qual.ClassGetName;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.checkerframework.checker.lock.qual.GuardSatisfied;
import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.checker.nullness.qual.Nullable;
import static daikon.inv.Invariant.asInvClass;

import daikon.*;
import daikon.Quantify.QuantFlags;
import daikon.derive.binary.*;
import daikon.inv.*;
import daikon.inv.binary.twoScalar.*;
import daikon.inv.binary.twoString.*;
import daikon.inv.unary.scalar.*;
import daikon.inv.unary.sequence.*;
import daikon.suppress.*;
import java.util.*;
import org.plumelib.util.UtilPlume;
import typequals.prototype.qual.NonPrototype;
import typequals.prototype.qual.Prototype;

/**
 * Baseclass for binary numeric invariants.
 *
 * Each specific invariant is implemented in a subclass (typically, in this file). The subclass must
 * provide the methods instantiate(), check(), and format(). Symmetric functions should define
 * is_symmetric() to return true.
 */
public abstract class PairwiseString extends TwoSequenceString {

  // We are Serializable, so we specify a version to allow changes to
  // method signatures without breaking serialization.  If you add or
  // remove fields, you should change this number to the current date.
  static final long serialVersionUID = 20060609L;

  protected PairwiseString(PptSlice ppt, boolean swap) {
    super(ppt);
    this.swap = swap;
  }

  protected PairwiseString(boolean swap) {
    super();
    this.swap = swap;
  }

  /** Returns true if it is ok to instantiate a numeric invariant over the specified slice. */
  @Override
  public boolean instantiate_ok(VarInfo[] vis) {

    ProglangType type1 = vis[0].file_rep_type;
    ProglangType type2 = vis[1].file_rep_type;
    if (!type1.baseIsString() || !type2.baseIsString()) {
      return false;
    }

    return true;
  }

  @Pure
  @Override
  public boolean isExact() {
    return true;
  }

  @Override
  public String repr(@GuardSatisfied PairwiseString this) {
    return getClass().getSimpleName() + ": " + format()
      + (swap ? " [swapped]" : " [unswapped]");
  }

  /**
   * Returns a string in the specified format that describes the invariant.
   *
   * The generic format string is obtained from the subclass specific get_format_str(). Instances of
   * %varN% are replaced by the variable name in the specified format.
   */
  @SideEffectFree
  @Override
  public String format_using(@GuardSatisfied PairwiseString this, OutputFormat format) {

    if (ppt == null) {
      return (String.format("proto ppt [class %s] format %s", getClass(),
                             get_format_str(format)));
    }
    String fmt_str = get_format_str(format);

    String v1;
    String v2;
        if (format.isJavaFamily()) {

            return format_unimplemented(format);

        } else if (format == OutputFormat.CSHARPCONTRACT) {

            fmt_str = UtilPlume.replaceString(fmt_str, "%var1%", var1().csharp_name());
            fmt_str = UtilPlume.replaceString(fmt_str, "%var2%", var2().csharp_name());
            return fmt_str;

        }

      if (format == OutputFormat.ESCJAVA) {
        String[] form = VarInfo.esc_quantify(var1(), var2());
        fmt_str = form[0] + "(" + fmt_str + ")" + form[3];
        v1 = form[1];
        v2 = form[2];
      } else if (format == OutputFormat.SIMPLIFY) {
        String[] form = VarInfo.simplify_quantify(QuantFlags.element_wise(),
                                                   var1(), var2());
        fmt_str = form[0] + " " + fmt_str + " " + form[3];
        v1 = form[1];
        v2 = form[2];
      } else {
        v1 = var1().name_using(format);
        v2 = var2().name_using(format);
        if (format == OutputFormat.DAIKON) {
          fmt_str += " (elementwise)";
        }
      }

    // Note that we do not use String.replaceAll here, because that's
    // inseparable from the regex library, and we don't want to have to
    // escape v1 with something like
    // v1.replaceAll("([\\$\\\\])", "\\\\$1")
    fmt_str = UtilPlume.replaceString(fmt_str, "%var1%", v1);
    fmt_str = UtilPlume.replaceString(fmt_str, "%var2%", v2);

    // if (false && (format == OutputFormat.DAIKON)) {
    //   fmt_str = "[" + getClass() + "]" + fmt_str + " ("
    //          + var1().get_value_info() + ", " + var2().get_value_info() +  ")";
    // }
    return fmt_str;
  }

  /** Calls the function specific equal check and returns the correct status. */

  @Override
  public InvariantStatus check_modified(String[] x, String[] y,
                                        int count) {
    if (x.length != y.length) {
      if (Debug.logOn()) {
        log("Falsified - x length = %s y length = %s", x.length, y.length);
      }
      return InvariantStatus.FALSIFIED;
    }

    if (Debug.logDetail()) {
      log("testing values %s, %s", Arrays.toString (x),
           Arrays.toString(y));
    }

    try {
      for (int i = 0; i < x.length; i++) {
        if (!eq_check(x[i], y[i])) {
          if (Debug.logOn()) {
            log("Falsified - x[%s]=%s y[%s]=%s", i, x[i], i, y[i]);
          }
          return InvariantStatus.FALSIFIED;
        }
      }
      return InvariantStatus.NO_CHANGE;
    } catch (Exception e) {
      if (Debug.logOn()) {
        log("Falsified - exception %s", e);
      }
      return InvariantStatus.FALSIFIED;
    }
  }

  /**
   * Checks to see if this invariant is over subsequences and if the same relationship holds over
   * the full sequence. This is obvious if it does. For example 'x[foo..] op y[bar..]' would be
   * obvious if 'x[] op y[]' This can't fully be handled as a suppression since a suppression needs
   * to insure that foo == bar as well. But that is not a requirement here (the fact that 'x[] op
   * y[]' implies that foo == bar when x[] and y[] are not missing).
   */
  public @Nullable DiscardInfo is_subsequence(VarInfo[] vis) {

    VarInfo v1 = var1(vis);
    VarInfo v2 = var2(vis);

    // Make sure each var is a sequence subsequence
    if (!v1.isDerived() || !(v1.derived instanceof SequenceStringSubsequence)) {
      return null;
    }
    if (!v2.isDerived() || !(v2.derived instanceof SequenceStringSubsequence)) {
      return null;
    }

    @NonNull SequenceStringSubsequence der1 = (SequenceStringSubsequence) v1.derived;
    @NonNull SequenceStringSubsequence der2 = (SequenceStringSubsequence) v2.derived;

    // Both of the indices must be either from the start or up to the end.
    // It is not necessary to check that they match in any other way since
    // if the supersequence holds, that implies that the sequences are
    // of the same length.  Thus any subsequence that starts from the
    // beginning or finishes at the end must end or start at the same
    // spot (or it would have been falsified when it didn't)
    if (der1.from_start != der2.from_start) {
      return null;
    }

    // Look up this class over the sequence variables
    Invariant inv = find(getClass(), der1.seqvar(), der2.seqvar());
    if (inv == null) {
      return null;
    }
    return new DiscardInfo(this, DiscardCode.obvious, "Implied by "
                           + inv.format());
  }

  @Pure
  @Override
  public @Nullable DiscardInfo isObviousDynamically(VarInfo[] vis) {

    DiscardInfo super_result = super.isObviousDynamically(vis);
    if (super_result != null) {
      return super_result;
    }

      // any elementwise relation across subsequences is made obvious by
      // the same relation across the original sequence
      DiscardInfo result = is_subsequence(vis);
      if (result != null) {
        return result;
      }

    // Check for invariant specific obvious checks.  The obvious_checks
    // method returns an array of arrays of antecedents.  If all of the
    // antecedents in an array are true, then the invariant is obvoius.
    InvDef[][] obvious_arr = obvious_checks(vis);
    obvious_loop:
    for (int i = 0; i < obvious_arr.length; i++) {
      InvDef[] antecedents = obvious_arr[i];
      StringBuilder why = null;
      for (int j = 0; j < antecedents.length; j++) {
        Invariant inv = antecedents[j].find();
        if (inv == null) {
          continue obvious_loop;
        }
        if (why == null) {
          why = new StringBuilder(inv.format());
        } else {
          why.append(" and ");
          why.append(inv.format());
        }
      }
      return new DiscardInfo(this, DiscardCode.obvious, "Implied by " + why);
    }

    return null;
  }

  /**
   * Returns an invariant that is true when the size(v1) == size(v2). There are a number of
   * possible cases for an array:
   *
   * <pre>
   *    x[]         - entire array, size usually available as size(x[])
   *    x[..(n-1)]  - size is n
   *    x[..n]      - size is n+1
   *    x[n..]      - size is size(x[]) - n
   *    x[(n+1)..]  - size is size(x[]) - (n+1)
   * </pre>
   *
   * Each combination of the above must be considered in creating the equality invariant. Not all
   * possibilities can be handled. Null is returned in that case. In the following table, s stands
   * for the size
   *
   * <pre>
   *                    x[]     x[..(n-1)]  x[..n]  x[n..]    x[(n+1)..]
   *                  --------- ----------  ------  ------    ----------
   *    y[]           s(y)=s(x)   s(y)=n
   *    y[..(m-1)]        x         m=n
   *    y[..m]            x         x         m=n
   *    y[m..]            x         x          x     m=n &and;
   *                                                s(y)=s(x)
   *    y[(m+1)..]        x         x          x        x       m=n &and;
   *                                                           s(y)=s(x)
   * </pre>
   * NOTE: this is not currently used. Many (if not all) of the missing table cells above could be
   * filled in with linear binary invariants (eg, m = n + 1).
   */
  public @Nullable InvDef array_sizes_eq(VarInfo v1, VarInfo v2) {

    VarInfo v1_size = get_array_size(v1);
    VarInfo v2_size = get_array_size(v2);

    // If we can find a size variable for each side build the invariant
    if ((v1_size != null) && (v2_size != null)) {
      return (new InvDef(v1_size, v2_size, IntEqual.class));
    }

    // If either variable is not derived, there is no possible invariant
    // (since we covered all of the direct size comparisons above)
    if ((v1.derived == null) || (v2.derived == null)) {
      return null;
    }

    // Get the sequence subsequence derivations
    SequenceStringSubsequence v1_ss = (SequenceStringSubsequence) v1.derived;
    SequenceStringSubsequence v2_ss = (SequenceStringSubsequence) v2.derived;

    // If both are from_start and have the same index_shift, just compare
    // the variables
    if (v1_ss.from_start && v2_ss.from_start
        && (v1_ss.index_shift == v2_ss.index_shift)) {
      return (new InvDef(v1_ss.sclvar(), v2_ss.sclvar(), IntEqual.class));
    }

    return null;
  }

  /**
   * Returns a variable that corresponds to the size of v. Returns null if no such variable exists.
   *
   * There are two cases that are not handled: x[..n] with an index shift and x[n..].
   */
  public @Nullable VarInfo get_array_size(VarInfo v) {

    assert v.rep_type.isArray();

    if (v.derived == null) {
      return (v.sequenceSize());
    } else if (v.derived instanceof SequenceStringSubsequence) {
      SequenceStringSubsequence ss = (SequenceStringSubsequence) v.derived;
      if (ss.from_start && (ss.index_shift == -1)) {
        return (ss.sclvar());
      }
    }

    return null;
  }

  /**
   * Return a format string for the specified output format. Each instance of %varN% will be
   * replaced by the correct name for varN.
   */
  public abstract String get_format_str(@GuardSatisfied PairwiseString this, OutputFormat format);

  /** Returns true if x and y don't invalidate the invariant. */
  public abstract boolean eq_check(String x, String y);

  /**
   * Returns an array of arrays of antecedents. If all of the antecedents in any array are true,
   * then the invariant is obvious.
   */
  public InvDef[][] obvious_checks(VarInfo[] vis) {
    return (new InvDef[][] {});
  }

  public static List<@Prototype Invariant> get_proto_all() {

    List<@Prototype Invariant> result = new ArrayList<>();

        result.add(SubString.get_proto(false));
        result.add(SubString.get_proto(true));

    // System.out.printf("%s get proto: %s%n", PairwiseString.class, result);
    return result;
  }

  // suppressor definitions, used by many of the classes below
  protected static NISuppressor

    var1_eq_var2    = new NISuppressor(0, 1, PairwiseStringEqual.class),
    var2_eq_var1    = new NISuppressor(0, 1, PairwiseStringEqual.class);

  //
  // Int and Float Numeric Invariants
  //

//
// Standard String invariants
//

  /**
   * Represents the substring invariant between corresponding elements of two sequences of String.
   * Prints as {@code x[] is a substring of y[]}.
   */
  public static class SubString extends PairwiseString {
    // We are Serializable, so we specify a version to allow changes to
    // method signatures without breaking serialization.  If you add or
    // remove fields, you should change this number to the current date.
    static final long serialVersionUID = 20081113L;

    protected SubString(PptSlice ppt, boolean swap) {
      super(ppt, swap);
    }

    protected SubString(boolean swap) {
      super(swap);
    }

    private static @Prototype SubString proto = new @Prototype SubString(false);
    private static @Prototype SubString proto_swap = new @Prototype SubString(true);

    /** Returns the prototype invariant. */
    public static @Prototype SubString get_proto(boolean swap) {
      if (swap) {
        return proto_swap;
      } else {
        return proto;
      }
    }

    // Variables starting with dkconfig_ should only be set via the
    // daikon.config.Configuration interface.
    /** Boolean. True iff SubString invariants should be considered. */
    public static boolean dkconfig_enabled = false;

    /** Returns whether or not this invariant is enabled. */
    @Override
    public boolean enabled() {
      return dkconfig_enabled;
    }

    @Override
    protected SubString instantiate_dyn(@Prototype SubString this, PptSlice slice) {
      return new SubString(slice, swap);
    }

    @Override
    public String get_format_str(@GuardSatisfied SubString this, OutputFormat format) {
      if (format == OutputFormat.DAIKON) {
        return "%var1% is a substring of %var2%";
      } else if (format.isJavaFamily()) {
        return "%var2%.contains(%var1%)";
      } else if (format == OutputFormat.CSHARPCONTRACT) {

           return "Contract.ForAll(0, %var2%.Count(), i => %var2%[i].Contains(%var1%[i]))";
      } else {
        return format_unimplemented(format);
      }
    }

    @Override
    public boolean eq_check(String x, String y) {
      return (y.contains(x));
    }

    /** Justified as long as there are samples. */
    @Override
    protected double computeConfidence() {
      if (ppt.num_samples() == 0) {
        return Invariant.CONFIDENCE_UNJUSTIFIED;
      }

      return Invariant.CONFIDENCE_JUSTIFIED;
    }

    /** Returns a list of non-instantiating suppressions for this invariant. */
    @Pure
    @Override
    public @NonNull NISuppressionSet get_ni_suppressions() {
      if (swap) {
        return suppressions_swap;
      } else {
        return suppressions;
      }
    }

    /** definition of this invariant (the suppressee) (unswapped) */
    private static NISuppressee suppressee = new NISuppressee(SubString.class, false);

    private static NISuppressionSet suppressions =
      new NISuppressionSet(
          new NISuppression[] {
              // v1 == v2 ==> v1 subsequence v2
              new NISuppression(var1_eq_var2, suppressee),
          });
    private static NISuppressionSet suppressions_swap = suppressions.swap();
  }

}
