package com.coderskitchen.gradle_jooq_flayway_example;

import org.flywaydb.core.Flyway;

/**
 * Created by Peter on 25.04.2016.
 */
public class Application {
  public static void main(String[] args) {
    String userName = "root";
    String password = "";
    String url = "jdbc:h2:file:./exampleDatabase";

    Flyway flyway = new Flyway();
    flyway.setDataSource(url, userName, password);
    flyway.migrate();

  }
}
