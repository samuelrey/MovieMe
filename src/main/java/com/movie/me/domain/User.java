package com.movie.me.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.codec.digest.DigestUtils;
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

	@Property(name="photo")
	private String photo;

	@Property(name="email")
	private String email;

	@JsonIgnore
	@Property(name="password")
    private String password;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Movie> getMoviesLiked() {
		return moviesLiked;
	}

	public void setMoviesLiked(Set<Movie> moviesLiked) {
		this.moviesLiked = moviesLiked;
	}

    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", photo='" + photo + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (!username.equals(user.username)) return false;
		if (!photo.equals(user.photo)) return false;
		return email.equals(user.email);
	}

	public static class UserBuilder {
        private String username;
        private String photo;
        private String email;
        private String password;

        public UserBuilder() {
            password = "";
            photo = "";
        }

        public UserBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder withPhoto(String photo) {
            this.photo = photo;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPhoto(photo);
            user.setPassword(password);
            return user;
        }
    }
}
