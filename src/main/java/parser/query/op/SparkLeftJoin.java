package parser.query.op;

import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.sparql.algebra.op.OpLeftJoin;
import com.hp.hpl.jena.sparql.expr.Expr;

import org.apache.spark.rdd.RDD;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import model.rdf.executionresults.IntermediateResultsModel;
import parser.query.expression.ExprCompiler;
import parser.query.expression.op.IExpression;
import sparql.SparkFacade;
import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class SparkLeftJoin implements SparkOp {

  private static final String TAG = "LeftJoin";
  private final OpLeftJoin op;
  private final PrefixMapping prefixes;

  public SparkLeftJoin(OpLeftJoin op, PrefixMapping prefixes) {
    this.op = op;
    this.prefixes = prefixes;
  }

  @Override
  public void execute() {
    if (op.getLeft() != null && op.getRight() != null) {
      int leftID = op.getLeft().hashCode();
      RDD<SolutionMapping> leftRes = IntermediateResultsModel
          .getInstance().getResultRDD(leftID);

      int rightID = op.getRight().hashCode();
      RDD<SolutionMapping> rightRes = IntermediateResultsModel
          .getInstance().getResultRDD(rightID);

      // Intersection between the variables, gives the join variables
      Set<String> joinVars = getJoinVars(
          IntermediateResultsModel.getInstance().getResultVariables(
              op.getLeft().hashCode()),
          IntermediateResultsModel.getInstance().getResultVariables(
              op.getRight().hashCode()));

      // Union of the variables are the resulting variables
      Set<String> resultVars = new HashSet<>(IntermediateResultsModel
                                                 .getInstance()
                                                 .getResultVariables(op.getLeft().hashCode()));
      resultVars.addAll(IntermediateResultsModel.getInstance()
                            .getResultVariables(op.getRight().hashCode()));

      RDD<SolutionMapping> filteredRes = null;

      // Filter inside Optional
      if (op.getExprs() != null) {
        Set<IExpression> expressions = new HashSet<>();
        Iterator<Expr> iterator = op.getExprs().iterator();
        while (iterator.hasNext()) {
          Expr current = iterator.next();

          ExprCompiler translator = new ExprCompiler(prefixes);
          expressions.add(translator.translate(current));
        }
        filteredRes = SparkFacade.filter(rightRes, expressions);

      }

      RDD<SolutionMapping> leftJoined;
      if (filteredRes != null) {
        leftJoined = SparkFacade.optional(new ArrayList<String>(
            joinVars), leftRes, filteredRes);
      } else {
        leftJoined = SparkFacade.optional(new ArrayList<String>(
            joinVars), leftRes, rightRes);
      }
      IntermediateResultsModel.getInstance().removeResult(leftID);
      IntermediateResultsModel.getInstance().removeResult(rightID);

      IntermediateResultsModel.getInstance().putResult(op.hashCode(),
                                                       leftJoined, resultVars);
    }
  }

  private Set<String> getJoinVars(Set<String> left, Set<String> right) {
    left.retainAll(right);
    return left;
  }

  @Override
  public String getTag() {
    return TAG;
  }
}
