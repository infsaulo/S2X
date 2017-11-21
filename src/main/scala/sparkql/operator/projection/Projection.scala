package sparql.operator.projection

import constants.Const
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import sparql.operator.result.util.SolutionMapping

/**
  * Execute a projection on the solution mappings
  *
  * @author Thorsten Berberich
  */
trait Projection {

  /**
    * Execute the projection
    */
  protected def projection(vars: Broadcast[java.util.Set[String]],
                           results: RDD[SolutionMapping]): RDD[SolutionMapping] = {
    if (results == null) {
      return null
    }

    val resultProjected: RDD[SolutionMapping] = results.map(result => {
      result.project(vars.value)
      result
    })

    resultProjected.persist(Const.STORAGE_LEVEL)
    results.unpersist(true)
    resultProjected
  }

}