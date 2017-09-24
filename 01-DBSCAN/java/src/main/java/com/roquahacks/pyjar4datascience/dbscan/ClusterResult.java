package com.roquahacks.pyjar4datascience.dbscan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClusterResult<T> implements Iterable<Cluster<T>>{

	private List<Cluster<T>> clusters;

	public ClusterResult() {
		this.clusters = new ArrayList<>();
	}
	
	public int size() {
		return clusters.size();
	}
	
	public boolean addCluster(Cluster<T> cluster) {
		return clusters.add(cluster);
	}

	@Override
	public Iterator<Cluster<T>> iterator() {
		return clusters.iterator();
	}

	
}
