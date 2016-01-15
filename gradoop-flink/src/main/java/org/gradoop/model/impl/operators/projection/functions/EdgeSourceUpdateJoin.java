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

package org.gradoop.model.impl.operators.projection.functions;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.gradoop.model.api.EPGMEdge;
import org.gradoop.model.api.EPGMVertex;
import org.gradoop.model.impl.id.GradoopId;

/**
 * Joins an edges with a Tuple2 that contains the id of the original edge
 * source in its first field and the new edge source vertex in its second.
 * The output is an edge with updated source id.
 *
 * @param <V> EPGM vertex type
 * @param <E> EPGM edge type
 */
public class EdgeSourceUpdateJoin<V extends EPGMVertex, E extends EPGMEdge>
  implements JoinFunction<E, Tuple2<GradoopId, V>, E> {

  /**
   * {@inheritDoc}
   */
  @Override
  public E join(E e, Tuple2<GradoopId, V> vertexTuple) throws Exception {
    e.setSourceId(vertexTuple.f1.getId());
    return e;
  }
}
