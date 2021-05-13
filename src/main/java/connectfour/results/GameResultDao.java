package connectfour.results;

import connectfour.utils.jpa.GenericJpaDao;

import javax.persistence.Persistence;
import java.util.List;

public class GameResultDao extends GenericJpaDao<GameResult> {


    private static GameResultDao instance;

    /**
     * Constructs a {@code GenericJpaDao} object.
     */
    private GameResultDao() {
        super(GameResult.class);
    }

    public static GameResultDao getInstance() {
        if (instance == null) {
            instance = new GameResultDao();
            instance.setEntityManager(Persistence.createEntityManagerFactory("jpa-persistence-unit-1").createEntityManager());
        }
        return instance;
    }

    public List<GameResult> findBestFirstN() {
        return entityManager.createQuery("SELECT * FROM GameResult ORDER BY created;", GameResult.class)
                .getResultList();
    }
}
