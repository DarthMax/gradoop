/*
 * This file is part of Gradoop.
 *
 * Gradoop is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Gradoop is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Gradoop. If not, see <http://www.gnu.org/licenses/>.
 */

package org.gradoop.utils.statistics;

import org.apache.flink.api.common.ProgramDescription;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.java.tuple.Tuple3;
import org.gradoop.examples.AbstractRunner;
import org.gradoop.flink.model.impl.operators.matching.common.statistics.GraphStatisticsReader;
import org.gradoop.flink.model.impl.operators.statistics.DistinctEdgePropertiesByLabel;
import org.gradoop.flink.model.impl.operators.statistics.DistinctVertexPropertiesByLabel;

/**
 * Computes {@link DistinctEdgePropertiesByLabel} for a given logical graph.
 */
public class DistinctVertexPropertiesByLabelRunner extends AbstractRunner implements ProgramDescription {

  /**
   * args[0] - path to input directory
   * args[1] - input format (json, csv)
   * args[2] - path to output directory
   *
   * @param args arguments
   * @throws Exception if something goes wrong
   */
  public static void main(String[] args) throws Exception {
    new DistinctVertexPropertiesByLabel()
      .execute(readLogicalGraph(args[0], args[1]))
      .map(value -> Tuple3.of(value.f0.f0, value.f0.f1, value.f1))
      .returns(new TypeHint<Tuple3<String, String, Long>>() { })
      .writeAsCsv(
        appendSeparator(args[2]) + GraphStatisticsReader.FILE_DISTINCT_VERTEX_PROPERTIES_BY_LABEL,
        System.lineSeparator(), GraphStatisticsReader.TOKEN_SEPARATOR)
      .setParallelism(1);

    getExecutionEnvironment().execute("Statistics: Distinct vertex properties by label");
  }

  @Override
  public String getDescription() {
    return DistinctVertexPropertiesByLabelRunner.class.getName();
  }
}
