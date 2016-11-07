package server.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.info.Score;

import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ScoreDao implements Dao<Score> {
    private static final Logger log = LogManager.getLogger(ScoreDao.class);

    public ScoreDao(){
        try (Connection con = DbConnector.getConnection();
                Statement stm = con.createStatement()) {
             stm.executeQuery("CREATE TABLE IF NOT EXISTS scores ( "+
                            "userName  VARCHAR(255)  NOT NULL   PRIMARY KEY, "+
                            "score     INTEGER       NOT NULL);");
        }
        catch (Exception e){
            log.error("Failed to create ScoreDao!");
        }
    }

    @Override
    public List<Score> getAll() {
        List<Score> persons = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT * FROM scores;");
            while (rs.next()) {
                persons.add(mapToScore(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        }

        return persons;
    }

    @Override
    public List<Score> getAllWhere(String ... conditions) {
        List<Score> persons = new ArrayList<>();
        String totalCondition = Joiner.on(" AND ").join(Arrays.asList(conditions));
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT * FROM scores WHERE "+totalCondition+";");
            while (rs.next()) {
                persons.add(mapToScore(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        }
        return persons;
    }

    public List<Score> getAllWithCondition(String ... conditions) {
        List<Score> persons = new ArrayList<>();
        String totalCondition = Joiner.on(" AND ").join(Arrays.asList(conditions));
        try (Connection con = DbConnector.getConnection();
                Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT * FROM scores "+totalCondition+";");
            while (rs.next()) {
                persons.add(mapToScore(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        }
        return persons;
    }

    @Override
    public void insert(Score score) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute(String.format("INSERT INTO scores (score, userName) VALUES (%d, '%s');",
                    score.getScore(), score.getUserName()));
        } catch (SQLException e) {
            log.error("Failed to add score {}", score);
        }
    }

    public void delete(@NotNull String userName) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute("DELETE FROM scores where userName = '"+userName+"';");
        } catch (SQLException e) {
            log.error("Failed to delete scores of {}", userName);
        }
    }

    private static Score mapToScore(ResultSet rs) throws SQLException {
        return new Score(rs.getString("userName"), rs.getInt("score"));
    }
}
