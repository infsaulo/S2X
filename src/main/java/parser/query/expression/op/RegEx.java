package parser.query.expression.op;

import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class RegEx extends AbstractExpr2 {

  /**
   * Generated UID
   */
  private static final long serialVersionUID = -4408622036509672641L;

  public RegEx(IExpression left, IExpression right) {
    super(left, right);
  }

  @Override
  public Boolean evaluate(SolutionMapping solution) {
    // Only FILTER regex(?a, "ex:user") supported
    String left = "";
    String right = "";
    if (lExpr instanceof ExprVar) {
      left = solution.getValueToField(((ExprVar) lExpr).getVar());
    } else if (lExpr instanceof NodeValue) {
      left = ((NodeValue) lExpr).getValue(solution);
    }

    if (rExpr instanceof ExprVar) {
      right = solution.getValueToField(((ExprVar) rExpr).getVar());
    } else if (rExpr instanceof NodeValue) {
      right = ((NodeValue) rExpr).getValue(solution);
    }

    if (left == null || right == null) {
      return false;
    }

    // Remove trailing and leading quotes
    right = right.substring(1, right.length() - 1);

    return left.contains(right);
  }

}
