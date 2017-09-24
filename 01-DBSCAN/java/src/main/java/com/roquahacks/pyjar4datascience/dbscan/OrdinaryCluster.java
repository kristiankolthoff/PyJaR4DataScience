package com.roquahacks.pyjar4datascience.dbscan;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class OrdinaryCluster<T> implements Cluster<T>{

	private int id;
	private List<ClusterElement<T>> elements;
	
	public OrdinaryCluster(int id) {
		this.id = id;
	}

	@Override
	public boolean addElement(ClusterElement<T> element) {
		return elements.add(element);
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public Stream<ClusterElement<T>> stream() {
		return elements.stream();
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public Iterator<T> iterator() {
		return this.elements.stream()
				.map(element -> {return element.getElement();})
				.iterator();
	}

	@Override
	public boolean hasNext() {
		return elements.iterator().hasNext();
	}

	@Override
	public ClusterElement<T> next() {
		return elements.iterator().next();
	}

	@Override
	public void apply(Consumer<ClusterElement<T>> transform) {
		
	}
	
}
