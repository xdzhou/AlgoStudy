package com.loic.algo.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.GraphPathImpl;

/*
 * Dijkstra算法使用了广度优先搜索解决非负权有向图的单源最短路径问题，算法最终得到一个最短路径树。
 */
public class DijkstraAlgo implements ShortestPathAlgo<Integer>
{
	private WeightedGraph<Integer, DefaultWeightedEdge> mGraph;

	@Override
	public void setGraph(WeightedGraph<Integer, DefaultWeightedEdge> graph)
	{
		this.mGraph = graph;
	}

	@Override
	public GraphPath<Integer, DefaultWeightedEdge> getShortestPath(Integer startNode, Integer endNode)
	{
		Set<Integer> vertexSet = mGraph.vertexSet();
		// Distance from source to a node OR from a node to target
		double[] dist = new double[vertexSet.size()];
		//Previous node in optimal path
		int[] prev = new int[vertexSet.size()];
		//open list
		Set<Integer> openList = new HashSet<Integer>();
		for(Integer node : vertexSet)
		{
			dist[node] = (node == startNode) ? 0 : UNKNOWN_DIS;
			prev[node] = (node == startNode) ? startNode : -1;
		}
		openList.add(startNode);
		while(! openList.isEmpty())
		{
			Integer nodeWithMinDis = null;
			if(nodeWithMinDis == endNode)
			{
				break;
			}
			openList.remove(nodeWithMinDis);
			for(DefaultWeightedEdge edge: mGraph.edgesOf(nodeWithMinDis))
			{
				if(mGraph.getEdgeSource(edge) == nodeWithMinDis)
				{
					Integer targetNode = mGraph.getEdgeTarget(edge);
					double dis = dist[nodeWithMinDis] + mGraph.getEdgeWeight(edge);
					if(dist[targetNode] > dis)
					{
						dist[targetNode] = dis;
						prev[targetNode] = nodeWithMinDis;
					}
					openList.add(targetNode);
				}
			}
		}
		
		return new GraphPathImpl<Integer, DefaultWeightedEdge>(mGraph, startNode, endNode, null, dist[endNode]);
	}
}
