package com.adventure.ui.fx;

import com.adventure.event.EnemyEncounter;

public class BattleController implements BaseController {

    private SceneManager sceneManager;
    private GameSession session;
    private EnemyEncounter encounter;

    @Override
    public void init(SceneManager sceneManager, GameSession session) {
        this.sceneManager = sceneManager;
        this.session = session;
    }

    public void initBattle(SceneManager sceneManager, GameSession session, EnemyEncounter encounter) {
        this.sceneManager = sceneManager;
        this.session = session;
        this.encounter = encounter;
    }
}
