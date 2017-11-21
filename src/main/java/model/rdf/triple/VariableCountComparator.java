package model.rdf.triple;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator to sort the {@link TriplePattern} according to their variable
 * count
 *
 * @author Thorsten Berberich
 */
public class VariableCountComparator implements Comparator<TriplePattern>, Serializable {

  private static final long serialVersionUID = -6095349882208001076L;

  @Override
  public int compare(TriplePattern o1, TriplePattern o2) {
    return getVarCount(o2) - getVarCount(o1);
  }

  private int getVarCount(TriplePattern tp) {
    int count = 0;

    if (TriplePatternUtils.isVariable(tp.getSubject())) {
      count++;
    }

    if (TriplePatternUtils.isVariable(tp.getPredicate())) {
      count++;
    }

    if (TriplePatternUtils.isVariable(tp.getObject())) {
      count++;
    }

    return count;
  }
}
