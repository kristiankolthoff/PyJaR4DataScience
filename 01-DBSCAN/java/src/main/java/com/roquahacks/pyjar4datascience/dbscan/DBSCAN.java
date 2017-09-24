package com.roquahacks.pyjar4datascience.dbscan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class DBSCAN<T extends Matchable<T>> {

	private float epsilon;
	private int minPts;
	
	public DBSCAN(float epsilon, int minPts) {
		this.epsilon = epsilon;
		this.minPts = minPts;
	}

	public ClusterResult<T> cluster(List<? extends T> data) {
		final AtomicInteger idGenerator = new AtomicInteger(0);
		List<DClusterElement<T>> dataElements = getWrappedData(data);
		ClusterResult<T> clusters = new ClusterResult<>();
		for (int i = 0; i < dataElements.size(); i++) {
			DClusterElement<T> element = dataElements.get(i);
			Collection<DClusterElement<T>> neighbours = getNeighbours(element.getElement(), 
					dataElements, this.epsilon);
			if(neighbours.size() < this.minPts) {
				//TODO think about one cluster holding noise points only
				element.setType(ElementType.NOISE_POINT);
				element.setVisited(true);
			} else {
				Cluster<T> cluster = new OrdinaryCluster<>(idGenerator.getAndIncrement());
				expandCluster(element, neighbours, dataElements, cluster, this.epsilon, this.minPts);
				clusters.addCluster(cluster);
			}
		}
		return clusters;
		
	}
	
	private List<DClusterElement<T>> getWrappedData(List<? extends T> data) {
		List<DClusterElement<T>> elements = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			T point = data.get(i);
			elements.add(new DClusterElement<>(point));
		}
		return elements;
	}

	private void expandCluster(DClusterElement<T> point, Collection<DClusterElement<T>> neighbours, 
			List<DClusterElement<T>> data, Cluster<T> cluster, float epsilon, int minPts) {
		//TODO Add expansion timestamp to elements
		point.setType(ElementType.CORE_POINT);
		point.setVisited(true);
		cluster.addElement(point);
		for(DClusterElement<T> currentPoint : neighbours) {
			//Previously marked noise points may be reachable (BorderPoint) but
			//can not be marked properly because we already visited the point
			if(!currentPoint.isVisited()) {
				Collection<DClusterElement<T>> currentNeigbours = getNeighbours(point.getElement(), 
						data, epsilon);
				if(currentNeigbours.size() >= minPts) {
					neighbours.addAll(currentNeigbours);
					currentPoint.setType(ElementType.CORE_POINT);
				} else {
					currentPoint.setType(ElementType.BORDER_POINT);
				}
				cluster.addElement(currentPoint);
				currentPoint.setVisited(true);
			}
		}
	}
	
	private Collection<DClusterElement<T>> getNeighbours(T point, List<DClusterElement<T>> data, 
			float epsilon) {
		//Abstract the implementation of the queue and make it interchangeable
		Queue<DClusterElement<T>> queue = new PriorityQueue<>();
		for (int i = 0; i < data.size(); i++) {
			DClusterElement<T> element = data.get(i);
			T other = element.getElement();
			double dist = point.match(other);
			if(dist <= epsilon) queue.add(element);
		}
		return queue;
	}
	
	public float getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(float epsilon) {
		this.epsilon = epsilon;
	}

	public int getMinPts() {
		return minPts;
	}

	public void setMinPts(int minPts) {
		this.minPts = minPts;
	}
	
}
