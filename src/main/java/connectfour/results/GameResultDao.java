package connectfour.results;

import connectfour.utils.jpa.GenericJpaDao;

import javax.persistence.Persistence;

public class GameResultDao extends GenericJpaDao<GameResult> {


    private static GameResultDao instance;

    /**
     * Constructs a {@code GenericJpaDao} object.
     */
    private GameResultDao() {
        super(GameResult.class);
    }

    public static GameResultDao getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new GameResultDao();
        instance.setEntityManager(Persistence.createEntityManagerFactory("jpa-persistence-unit-1").createEntityManager());
        return instance;
    }

/*    public List<GameResult> findBest(int n) {
        return entityManager.createQuery("SELECT r FROM GameResult r WHERE r.solved = true ORDER BY r.duration ASC, r.created DESC", GameResult.class)
                .setMaxResults(n)
                .getResultList();
    }*/
}
