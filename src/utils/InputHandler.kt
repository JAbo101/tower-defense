package utils

import entity.Entity
import entity.tower.SpeedTower
import entity.tower.PowerTower
import entity.tower.RangeTower
import entity.tower.SimpleTower
import entity.tower.Tower
import gui.Game
import javafx.scene.input.MouseEvent
import gui.Game.Companion.TILE_SIZE;

object InputHandler {

    fun handleMouseClick(e: MouseEvent, grid: Array<IntArray>, towers: MutableList<Tower>, selectedTower: Tower?, g: Game): Tower? {
        if (selectedTower == null) {
            return null
        }

        val mx = e.x.toInt()
        val my = e.y.toInt()

        if (grid[my / TILE_SIZE][mx / TILE_SIZE] == 0 && !collides(towers, mx, my)) {
            if (selectedTower is SimpleTower) {
                g.buyTower(SimpleTower.PRICE)
                towers.add(SimpleTower(mx, my))
                return null
            } else if (selectedTower is SpeedTower) {
                g.buyTower(SpeedTower.PRICE)
                towers.add(SpeedTower(mx, my))
                return null
            } else if (selectedTower is RangeTower) {
                g.buyTower(RangeTower.PRICE)
                towers.add(RangeTower(mx, my))
                return null
            } else if (selectedTower is PowerTower) {
                g.buyTower(PowerTower.PRICE)
                towers.add(PowerTower(mx, my))
                return null
            }
        }

        return selectedTower
    }

    fun collides(list: Iterable<Tower>, mx: Int, my: Int): Boolean {

        for (entity in list) {
            if (entity.distance(mx, my) < TILE_SIZE.toDouble() * .6) {
                return true
            }
        }

        return false
    }
}