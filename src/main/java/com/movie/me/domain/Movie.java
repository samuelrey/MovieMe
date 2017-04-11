package com.movie.me.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label="Movie")
public class Movie {
	@GraphId
	private Long id;

	@Property(name="imdbid")
	private String imdbid;
	
	@Property(name="title")
	private String title;

	@Property(name="rating")
	private String rating;
	
	@Property(name="rated")
	private String rated;
	
	@Property(name="released")
	private String releaseDate;

	@Property(name="plot")
	private String plot;

	@Property(name="writer")
	private String writer;

	@Property(name="director")
	private String director;

	@Property(name="actors")
	private String actors;

	@Property(name="genre")
	private String genre;

	@Property(name="runtime")
	private String runtime;

	@Property(name="poster")
	private String poster;

	public String getImdbid() {
		return imdbid;
	}

	public void setImdbid(String imdbid) {
		this.imdbid = imdbid;
	}

	public String getRated() {
		return rated;
	}

	public void setRated(String rated) {
		this.rated = rated;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleasedate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String toString() {
		return this.title;
	}
}
