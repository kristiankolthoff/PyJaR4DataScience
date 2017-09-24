package com.roquahacks.pyjar4datascience.dbscan.example;

import java.util.ArrayList;
import java.util.List;

import com.roquahacks.pyjar4datascience.dbscan.Cluster;
import com.roquahacks.pyjar4datascience.dbscan.ClusterResult;
import com.roquahacks.pyjar4datascience.dbscan.DBSCAN;

public class MovieClusterExample {

	public static void main(String[] args) {
		final float EPSILON = 38.4f;
		final int MIN_PTS = 4;
		List<Movie> movies = new ArrayList<>();
		DBSCAN<Movie> dbscan = new DBSCAN<>(EPSILON, MIN_PTS);
		ClusterResult<Movie> clusters = dbscan.cluster(movies);
		
		for(Cluster<Movie> cluster : clusters) {
			System.out.println("-------" + cluster.getId() + "----------");
			System.out.println(cluster.size());
		}
	}
}
