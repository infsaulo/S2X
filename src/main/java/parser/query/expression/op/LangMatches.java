package parser.query.expression.op;

import java.util.Locale;

import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class LangMatches extends AbstractExpr2 {

  /**
   *
   */
  private static final long serialVersionUID = -9095052722606393643L;

  public LangMatches(IExpression left, IExpression right) {
    super(left, right);
  }

  @Override
  public Boolean evaluate(SolutionMapping solution) {
    final String language = ((Lang) lExpr).getValue(solution);

    final String langLiteral = ((NodeValue) rExpr).getValue(solution);
    final StringBuilder toMatch = new StringBuilder(langLiteral);

    if (langLiteral.startsWith("\"")) {
      toMatch.deleteCharAt(0);
    }

    if (langLiteral.endsWith("\"")) {
      toMatch.deleteCharAt(toMatch.length() - 1);
    }

    toMatch.insert(0, '@');

    return language.endsWith(toMatch.toString())
           || language.endsWith(toMatch.toString().toLowerCase(Locale.US));
  }

}
