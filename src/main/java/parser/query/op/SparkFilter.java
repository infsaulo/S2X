package parser.query.op;

import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.sparql.algebra.op.OpFilter;
import com.hp.hpl.jena.sparql.expr.Expr;

import parser.query.expression.ExprCompiler;
import parser.query.expression.op.IExpression;
import sparql.SparkFacade;
import sparql.operator.result.util.SolutionMapping;

import org.apache.spark.rdd.RDD;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import model.rdf.executionresults.IntermediateResultsModel;

/**
 * @author Thorsten Berberich
 */
public class SparkFilter implements SparkOp {

  private final OpFilter op;
  private final Set<IExpression> expressions;
  private static final String TAG = "Filter";

  public SparkFilter(OpFilter op, PrefixMapping prefixes) {
    this.op = op;
    expressions = new HashSet<>();

    Iterator<Expr> iterator = op.getExprs().iterator();
    while (iterator.hasNext()) {
      Expr current = iterator.next();
      ExprCompiler translator = new ExprCompiler(prefixes);
      expressions.add(translator.translate(current));
    }
  }

  @Override
  public void execute() {
    if (op.getSubOp() != null) {
      RDD<SolutionMapping> result = IntermediateResultsModel
          .getInstance().getResultRDD(op.getSubOp().hashCode());
      RDD<SolutionMapping> filteredRes = SparkFacade.filter(result,
                                                            this.expressions);

      IntermediateResultsModel.getInstance().putResult(
          op.hashCode(),
          filteredRes,
          IntermediateResultsModel.getInstance().getResultVariables(
              op.getSubOp().hashCode()));

      IntermediateResultsModel.getInstance().removeResult(
          op.getSubOp().hashCode());
    }
  }

  @Override
  public String getTag() {
    return TAG;
  }

}
