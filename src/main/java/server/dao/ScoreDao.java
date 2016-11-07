package server.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.info.Leaderboard;

import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ScoreDao implements Dao<Leaderboard> {
    private static final Logger log = LogManager.getLogger(ScoreDao.class);

    public ScoreDao(){
        try (Connection con = DbConnector.getConnection();
                Statement stm = con.createStatement()) {
             stm.executeQuery("CREATE TABLE IF NOT EXISTS Leaderboard ( "+
                            "userName  VARCHAR(255)  NOT NULL   PRIMARY KEY, "+
                            "score     INTEGER       NOT NULL);");
        }
        catch (Exception e){
            log.error("Failed to create ScoreDao!");
        }
    }

    @Override
    public List<Leaderboard> getAll() {
        List<Leaderboard> persons = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT * FROM Leaderboard;");
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
    public List<Leaderboard> getAllWhere(String ... conditions) {
        List<Leaderboard> persons = new ArrayList<>();
        String totalCondition = Joiner.on(" AND ").join(Arrays.asList(conditions));
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT * FROM Leaderboard WHERE "+totalCondition+";");
            while (rs.next()) {
                persons.add(mapToScore(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        }
        return persons;
    }

    public List<Leaderboard> getAllWithCondition(String ... conditions) {
        List<Leaderboard> persons = new ArrayList<>();
        String totalCondition = Joiner.on(" AND ").join(Arrays.asList(conditions));
        try (Connection con = DbConnector.getConnection();
                Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT * FROM Leaderboard "+totalCondition+";");
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
    public void insert(Leaderboard score) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute(String.format("INSERT INTO Leaderboard (score, userName) VALUES (%d, '%s');",
                    score.getScore(), score.getUserName()));
        } catch (SQLException e) {
            log.error("Failed to add score {}", score);
        }
    }

    public void delete(@NotNull String userName) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute("DELETE FROM Leaderboard where userName = '"+userName+"';");
        } catch (SQLException e) {
            log.error("Failed to delete Leaderboard of {}", userName);
        }
    }

    private static Leaderboard mapToScore(ResultSet rs) throws SQLException {
        return new Leaderboard(rs.getString("userName"), rs.getInt("score"));
    }
}
