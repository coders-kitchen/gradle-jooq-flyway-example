package com.coderskitchen.gradle_jooq_flayway_example;

import com.coderskitchen.example.public_.Tables;
import com.coderskitchen.example.public_.tables.records.RaceDateRecord;
import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;

/**
 * Created by Peter on 25.04.2016.
 */
public class Application {

  private static final String USER_NAME = "root";
  private static final String PASSWORD = "";
  private static final String URL = "jdbc:h2:file:./exampleDatabase";

  public static void main(String[] args) {
    createOrMigrateDatabase();
    playWithJOOQ();
  }

  private static void playWithJOOQ() {
    try (Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
      DSLContext dslContext = DSL.using(conn, SQLDialect.H2);
      createData(dslContext);
      queryData(dslContext);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void createData(DSLContext dslContext) {
    dslContext.insertInto(Tables.RACE_DATE)
              .columns(Tables.RACE_DATE.RACE_DATE_, Tables.RACE_DATE.RACE_PLACE)
              .values(new Date(2016, 1, 12), "Lake Baldeney, Essen")
              .values(new Date(2016, 3, 12), "Möhnetalsperre, Möhnesee")
              .execute();
  }

  private static void queryData(DSLContext dslContext) {
    Result<RaceDateRecord> records = dslContext.selectFrom(Tables.RACE_DATE)
                                               .fetch();
    for (RaceDateRecord record : records) {
      System.out.println(record);
    }
  }

  private static void createOrMigrateDatabase() {
    Flyway flyway = new Flyway();
    flyway.setDataSource(URL, USER_NAME, PASSWORD);
    flyway.migrate();
  }
}
