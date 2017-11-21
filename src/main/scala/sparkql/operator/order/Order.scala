package sparql.operator.order

import constants.Const
import com.hp.hpl.jena.graph.Node
import com.hp.hpl.jena.sparql.util.NodeFactoryExtra
import org.apache.spark.rdd.RDD
import sparql.operator.result.util.SolutionMapping

import scala.reflect._

/**
  * Order the solution mappings
  *
  * @author Thorsten Berberich
  */
trait Order {

  /**
    * Order the solution mappings by the given variable and the correct order
    */
  protected def orderBy(result: RDD[SolutionMapping], variable: String,
                        asc: Boolean): RDD[SolutionMapping] = {
    if (result == null) {
      return null;
    }

    val sorted = result.sortBy(solution => {
      solution.getValueToField(variable)
    }, asc, 2)(new LexicalOrdering, classTag[String])

    result.unpersist(true)
    sorted.persist(Const.STORAGE_LEVEL)
  }

}

/**
  * Class which is used for lexical ordering
  */
class LexicalOrdering extends Ordering[String] {

  def compare(a: String, b: String): Int = {
    try {
      val left: Node = NodeFactoryExtra.parseNode(a);
      val leftInt: Integer = left.getLiteral().getValue().asInstanceOf[Integer];
      val right: Node = NodeFactoryExtra.parseNode(b);
      val rightInt: Integer = right.getLiteral().getValue().asInstanceOf[Integer];

      return (leftInt - rightInt)
    } catch {
      case e: Exception => a.compareTo(b)
    }
  }

}