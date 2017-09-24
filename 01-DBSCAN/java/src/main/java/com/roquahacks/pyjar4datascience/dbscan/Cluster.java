package com.roquahacks.pyjar4datascience.dbscan;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface Cluster<T> extends Iterable<T>, Iterator<ClusterElement<T>>{

	public boolean addElement(ClusterElement<T> element);
	
	public int size();
	
	public Stream<ClusterElement<T>> stream();
	
	public int getId();
	
	public void apply(Consumer<ClusterElement<T>> transform);
	
}
