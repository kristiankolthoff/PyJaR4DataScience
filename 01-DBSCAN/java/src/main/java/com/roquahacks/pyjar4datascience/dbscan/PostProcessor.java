package com.roquahacks.pyjar4datascience.dbscan;

import java.util.List;

public interface PostProcessor<T> {

	public void resolve(List<Cluster<T>> clusters, List<ClusterElement<T>> conflictElements);
	
	public void resolve(List<Cluster<T>> clusters, ClusterElement<T> conflictElement);
	
}
