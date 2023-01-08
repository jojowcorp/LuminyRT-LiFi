package fr.luminy_lifi.app;

import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dataManager {

    private List<Point> points;

    public dataManager() {
        this.points = new ArrayList<Point>();
    }


    public Point getPointById(int id) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return this.points.stream().filter(x -> x.ID == id).findFirst().orElse(null);
        } else {
            for(Point p:this.points) {
                if(p.ID == id) {
                    return p;
                }
            }
            return null;
        }
    }

    public List<Point> getPoints() {
        return this.points;
    }

    public static class Point {
        public final int X, Y;
        public final int ID;

        public int[] connectedPoint = null;

        public Point(int ID, int X, int Y) {
            this.ID = ID;
            this.X = X;
            this.Y = Y;
        }

        @Override
        public String toString() {
            return "(Point "+X+" "+Y+" id:"+ID+") "+ Arrays.toString(connectedPoint);
        }

        @Override
        public int hashCode() {
            return (int) (this.ID + this.X + this.Y);
        }
        @Override
        public boolean equals(Object o) {
            if (o == null)
                return false;
            if (getClass() != o.getClass())
                return false;
            Point vec = (Point) o;

            return (vec.ID == this.ID && vec.X == this.X &&  vec.Y == this.Y);
        }

    }

    private class LocationPoint extends Point {
        public final String Name;


        public LocationPoint(int ID, int X, int Y, String Name) {
            super(ID,X,Y);
            this.Name = Name;
        }

        @Override
        public String toString() {
            return "(Point "+Name+" "+X+" "+Y+" id:"+ID+") "+Arrays.toString(connectedPoint);
        }

    }

    private class PathPoint extends Point {

        public PathPoint(int ID, int X, int Y) {
            super(ID,X,Y);
        }
    }

}
