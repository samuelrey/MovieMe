package com.movie.me.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "Movie")
public class Movie {
    @GraphId
    private Long id;

    @Property(name = "imdbid")
    private String imdbid;

    @Property(name = "title")
    private String title;

    @Property(name = "rating")
    private Double rating;

    @Property(name = "rated")
    private String rated;

    @Property(name = "released")
    private Integer releaseDate;

    @Property(name = "plot")
    private String plot;

    @Property(name = "writer")
    private String writer;

    @Property(name = "director")
    private String director;

    @Property(name = "actors")
    private String actors;

    @Property(name = "genre")
    private String genre;

    @Property(name = "runtime")
    private Integer runtime;

    @Property(name = "poster")
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

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleasedate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", imdbid='" + imdbid + '\'' +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", rated='" + rated + '\'' +
                ", releaseDate=" + releaseDate +
                ", plot='" + plot + '\'' +
                ", writer='" + writer + '\'' +
                ", director='" + director + '\'' +
                ", actors='" + actors + '\'' +
                ", genre='" + genre + '\'' +
                ", runtime=" + runtime +
                ", poster='" + poster + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (Double.compare(movie.rating, rating) != 0) return false;
        if (!releaseDate.equals(movie.releaseDate)) return false;
        if (!runtime.equals(movie.runtime)) return false;
        if (imdbid != null ? !imdbid.equals(movie.imdbid) : movie.imdbid != null) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (rated != null ? !rated.equals(movie.rated) : movie.rated != null) return false;
        if (plot != null ? !plot.equals(movie.plot) : movie.plot != null) return false;
        if (writer != null ? !writer.equals(movie.writer) : movie.writer != null) return false;
        if (director != null ? !director.equals(movie.director) : movie.director != null) return false;
        if (actors != null ? !actors.equals(movie.actors) : movie.actors != null) return false;
        if (genre != null ? !genre.equals(movie.genre) : movie.genre != null) return false;
        return poster != null ? poster.equals(movie.poster) : movie.poster == null;
    }

    public static class MovieBuilder {
        private String imdbid;
        private String title;
        private double rating;
        private String rated;
        private int releaseDate;
        private String plot;
        private String writer;
        private String director;
        private String actors;
        private String genre;
        private int runtime;
        private String poster;

        public Movie build() {
            Movie movie = new Movie();
            movie.setTitle(title);
            movie.setActors(actors);
            movie.setImdbid(imdbid);
            movie.setRated(rated);
            movie.setRating(rating);
            movie.setReleasedate(releaseDate);
            movie.setPlot(plot);
            movie.setWriter(writer);
            movie.setDirector(director);
            movie.setGenre(genre);
            movie.setRuntime(runtime);
            movie.setPoster(poster);
            return movie;
        }

        public MovieBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public MovieBuilder withImdbid(String imdbid) {
            this.imdbid = imdbid;
            return this;
        }

        public MovieBuilder withRating(double rating) {
            this.rating = rating;
            return this;
        }

        public MovieBuilder withRated(String rated) {
            this.rated = rated;
            return this;
        }

        public MovieBuilder withReleaseDate(int releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public MovieBuilder withPlot(String plot) {
            this.plot = plot;
            return this;
        }

        public MovieBuilder withWriter(String writer) {
            this.writer = writer;
            return this;
        }

        public MovieBuilder withDirector(String director) {
            this.director = director;
            return this;
        }

        public MovieBuilder withActors(String actors) {
            this.actors = actors;
            return this;
        }

        public MovieBuilder withGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public MovieBuilder withRuntime(int runtime) {
            this.runtime = runtime;
            return this;
        }

        public MovieBuilder withPoster(String poster) {
            this.poster = poster;
            return this;
        }
    }
}
