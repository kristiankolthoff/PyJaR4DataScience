package com.roquahacks.pyjar4datascience.dbscan;

@FunctionalInterface
public interface ClusterMetric<T> {

	public double compute(Cluster<T> cluster);
}
