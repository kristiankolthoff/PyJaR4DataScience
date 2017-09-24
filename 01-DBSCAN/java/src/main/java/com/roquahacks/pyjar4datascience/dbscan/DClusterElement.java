package com.roquahacks.pyjar4datascience.dbscan;

public class DClusterElement<T> implements ClusterElement<T>{

	private T value;
	private boolean visited;
	private ElementType type;
	
	public DClusterElement(T value) {
		this.value = value;
		this.visited = false;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public ElementType getType() {
		return type;
	}
	public void setType(ElementType type) {
		this.type = type;
	}

	@Override
	public T getElement() {
		return value;
	}
	
}
