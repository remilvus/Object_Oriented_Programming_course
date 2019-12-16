package agh.cs.lab_archive.lab_7;

import agh.cs.project.MapBasicLogic.Vector2d;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition);

    default void positionChanged(Vector2d oldPosition, Vector2d newPosition, Object o){
        positionChanged(oldPosition, newPosition);
    }
}