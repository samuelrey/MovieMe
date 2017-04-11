package com.movie.me.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;


@NodeEntity(label="User")
public class User {
	@GraphId
	private Long id;

    @Property(name="username")
    private String username;

	@Property(name="name")
	private String name;

	@Property(name="photo")
	private String photo;

	@JsonIgnore
	@Property(name="email")
	private String email;

	@Relationship(type="likes")
	private Set<Movie> moviesLiked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
		return photo;
    }

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Movie> getMoviesLiked() {
		return moviesLiked;
	}

	public void setMoviesLiked(Set<Movie> moviesLiked) {
		this.moviesLiked = moviesLiked;
	}

	@Override
	public String toString() {
		return this.email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (!username.equals(user.username)) return false;
		if (!name.equals(user.name)) return false;
		if (!photo.equals(user.photo)) return false;
		return email.equals(user.email);
	}

}
