// ***** This file is automatically generated from SequencesIntersectionFactory.java.jpp

package daikon.derive.binary;

import org.checkerframework.checker.nullness.qual.Nullable;
import daikon.*;
import daikon.derive.Derivation;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

// This controls derivations which use the scalar as an index into the
// sequence, such as getting the element at that index or a subsequence up
// to that index.

public final class SequenceFloatIntersectionFactory extends BinaryDerivationFactory {

  /** Debug tracer. */
  private static final Logger debug =
    Logger.getLogger("daikon.derive.binary.SequenceFloatIntersectionFactory");

  @Override
  public BinaryDerivation @Nullable [] instantiate(VarInfo seq1, VarInfo seq2) {

    if (!SequenceFloatIntersection.dkconfig_enabled) {
      return null;
    }

    if ((seq1.rep_type != ProglangType.DOUBLE_ARRAY) || (seq2.rep_type != ProglangType.DOUBLE_ARRAY)) {
      return null;
    }

    // Intersect only sets with the same declared element type
    if (!seq1.type.base().equals(seq2.type.base())) {
      return null;
    }

    // For now, do nothing if the sequences are derived.
    //    if ((seq1.derived != null) || (seq2.derived != null))
    //  return null;

    // We allow double derivations of predicate slices because it may be interesting
    if ((seq1.derived != null)) {
      Derivation derivation = seq1.derived;
      if (!(derivation instanceof SequencesPredicate)) {
        return null;
      }
    }

    if ((seq2.derived != null)) {
      Derivation derivation = seq2.derived;
      if (!(derivation instanceof SequencesPredicate)) {
        return null;
      }
    }

    if (debug.isLoggable(Level.FINE)) {
      debug.fine("Instantiatiating " + seq1.name() + " and " + seq2.name());
      debug.fine("  in " + seq1.ppt.name());
    }

    return new BinaryDerivation[] {new SequenceFloatIntersection(seq1, seq2)};
  }
}
