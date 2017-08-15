package com.movie.me.beans;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@org.springframework.context.annotation.Configuration
@EnableNeo4jRepositories(basePackages = "com.movie.me.repository")
@Profile({"embedded", "test"})
public class Neo4jTestConfiguration extends Neo4jConfiguration {
    @Bean
    public Configuration getConfiguration() {
        Configuration config = new Configuration();
        config
                .driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver")
                .setURI("file:///opt/neo4j-community-3.1.2/data/databases/graph.db");
        return config;
    }

    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "com.movie.me.domain");
    }
}
