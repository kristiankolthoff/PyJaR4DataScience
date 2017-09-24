package com.roquahacks.pyjar4datascience.dbscan;

@FunctionalInterface
public interface Similarity<T> {

	public double getSimilarity(T t1, T t2);
}
